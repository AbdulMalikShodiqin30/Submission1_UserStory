package com.malik.userstory.presentation.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.malik.userstory.databinding.ActivityDetailBinding
import com.malik.userstory.utils.Result
import com.malik.userstory.utils.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    private val detailViewModel: DetailViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras

        if (bundle != null) {
            val id = bundle.getString(ID)
            detailViewModel.getDetailStory(id!!).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.apply {
                                nameTv.text = result.data.story?.name
                                descriptionTv.text = result.data.story?.description
                            }
                            Glide.with(this)
                                .load(result.data.story?.photoUrl)
                                .into(binding.storyImage)
                        }
                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this,
                                "Couldn't load data",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ID = "id"
    }
}