package com.malik.userstory.presentation.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.malik.userstory.R
import com.malik.userstory.databinding.ActivityHomeBinding
import com.malik.userstory.presentation.adapter.StoryAdapter
import com.malik.userstory.presentation.detail.DetailActivity
import com.malik.userstory.presentation.newstory.NewStoryActivity
import com.malik.userstory.presentation.welcome.WelcomeActivity
import com.malik.userstory.utils.Result
import com.malik.userstory.data.preference.UserPreferences
import com.malik.userstory.utils.ViewModelFactory

class HomeActivity : AppCompatActivity(), MenuItem.OnMenuItemClickListener,
    StoryAdapter.OnItemClickListener {
    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeViewModel.getStories().observe(this) { result ->
            if (result != null) {
                when(result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val storyAdapter = StoryAdapter(result.data.listStory, this)
                        binding.storiesRv.adapter = storyAdapter
                        val layoutManager = LinearLayoutManager(this)
                        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
                        binding.storiesRv.layoutManager = layoutManager
                        binding.storiesRv.addItemDecoration(dividerItemDecoration)
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

        binding.floatingButton.setOnClickListener {
            val intent = Intent(this, NewStoryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        val logout = menu?.findItem(R.id.logout_option)
        logout?.setOnMenuItemClickListener(this)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout_option -> {
                val userPreferences = UserPreferences(this)
                userPreferences.removeToken()
                Toast.makeText(this@HomeActivity, "Logout Success", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, WelcomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        return false
    }

    override fun onItemClick(id: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.ID, id)
        startActivity(intent)
    }
}