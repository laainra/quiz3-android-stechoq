package com.lailaar.quiz3

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.lailaar.quiz3.LoginActivity
import com.lailaar.quiz3.databinding.ActivityRegisterBinding
class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.tvToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegister.setOnClickListener {
            val email = binding.edtEmailRegister.text.toString()
            val password = binding.edtPasswordRegister.text.toString()
            val fullName = binding.edtFullnameRegister.text.toString()
            val username = binding.edtUsernameRegister.text.toString()
            val confirmPassword = binding.edtConfirmPasswordRegister.text.toString()

            // Validasi fullname
            if (fullName.isEmpty()) {
                binding.edtFullnameRegister.error = "Full Name must not be empty"
                binding.edtFullnameRegister.requestFocus()
                return@setOnClickListener
            }

            // Validasi username
            if (username.isEmpty()) {
                binding.edtUsernameRegister.error = "Username must not be empty"
                binding.edtUsernameRegister.requestFocus()
                return@setOnClickListener
            }

            // Validasi confirm password
            if (confirmPassword.isEmpty()) {
                binding.edtConfirmPasswordRegister.error = "Confirm Password must not be empty"
                binding.edtConfirmPasswordRegister.requestFocus()
                return@setOnClickListener
            }

            // Validasi password dan confirm password
            if (password != confirmPassword) {
                binding.edtConfirmPasswordRegister.error = "Password and Confirm Password must match"
                binding.edtConfirmPasswordRegister.requestFocus()
                return@setOnClickListener
            }

            // Validasi email
            if (email.isEmpty()) {
                binding.edtEmailRegister.error = "Email must not be empty"
                binding.edtEmailRegister.requestFocus()
                return@setOnClickListener
            }

            //Validasi email tidak sesuai
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.edtEmailRegister.error = "Invalid Email"
                binding.edtEmailRegister.requestFocus()
                return@setOnClickListener
            }

            // Validasi password
            if (password.isEmpty()) {
                binding.edtPasswordRegister.error = "Password must not be empty"
                binding.edtPasswordRegister.requestFocus()
                return@setOnClickListener
            }

            // Validasi panjang password
            if (password.length < 6) {
                binding.edtPasswordRegister.error = "Password must be at least 6 characters"
                binding.edtPasswordRegister.requestFocus()
                return@setOnClickListener
            }

            // Panggil metode untuk registrasi di Firebase
            registerFirebase(email, password, fullName, username)
        }
    }

    private fun registerFirebase(email: String, password: String, fullName: String, username: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registrasi berhasil
                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    // Registrasi gagal, tampilkan pesan error
                    Toast.makeText(this, "${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
