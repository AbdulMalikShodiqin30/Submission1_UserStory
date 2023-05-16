package com.malik.userstory.presentation.register

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import com.malik.userstory.utils.Result
import com.malik.userstory.databinding.ActivityRegisterBinding
import com.malik.userstory.utils.ViewModelFactory
import com.malik.userstory.presentation.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    private val registerViewModel: RegisterViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableButton()

        binding.passwordEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if ((s?.length ?: 0) < 8) {
                    enableButton()
                } else {
                    enableButton()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.submitBtn.setOnClickListener {
            val name = binding.nameEt.text.toString()
            val email = binding.emailEt.text.toString()
            val password = binding.passwordEt.text.toString()

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

            registerViewModel.register(name, email, password).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding.apply {
                                progressBar.visibility = View.VISIBLE
                                passwordEt.visibility = View.GONE
                                emailEt.visibility = View.GONE
                                nameEt.visibility = View.GONE
                                register.visibility = View.GONE
                                submitBtn.visibility = View.GONE
                            }
                        }
                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this, "Account created! Please login", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                        is Result.Error -> {
                            binding.apply {
                                progressBar.visibility = View.GONE
                                passwordEt.visibility = View.VISIBLE
                                emailEt.visibility = View.VISIBLE
                                register.visibility = View.VISIBLE
                                nameEt.visibility = View.VISIBLE
                                submitBtn.visibility = View.VISIBLE
                            }
                            Toast.makeText(this, "Error Creating Account", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun enableButton() {
        val result = binding.passwordEt.text
        binding.submitBtn.isEnabled = result != null && result.toString().isNotEmpty() && result.length >= 8
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}