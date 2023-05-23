package com.project.mindmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.project.mindmate.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        binding.signUpCreateAccBtn.setOnClickListener {
            createUserWithEmail()
        }

        binding.signintv.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        binding.gSignupBTN.setOnClickListener {
            startActivityForResult(googleSignInClient.signInIntent,13)
        }
    }

    private fun createUserWithEmail() {
        val email = binding.signUpEmail.text.toString()
        val password = binding.signUpPassword.text.toString()
        val rePassword = binding.signUpRePassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty() && rePassword.isNotEmpty()) {
            if (password == rePassword) {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(
                                    this,
                                    "Account created successfully!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(Intent(this, LoggedInActivity::class.java))
                            } else {
                                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT)
                                    .show()
                            }

                        }
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Passwords don't match!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Empty fields not allowed!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 13 && resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)!!
            firebaseAuthWithGoogle(account.idToken!!)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        this.auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Account Created successfully",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoggedInActivity::class.java))
                    finish()
                }
            }.addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
    }
}