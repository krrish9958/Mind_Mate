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
import com.project.mindmate.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(this, gso)
      binding.gBtnLogin.setOnClickListener {
//          googleSignInClient.signOut()
          startActivityForResult(googleSignInClient.signInIntent,13)
      }

        binding.loginBtn.setOnClickListener {
            signInUserWithEmail()
        }

        binding.signUpLogin.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.loginForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))

        }
    }

    private fun signInUserWithEmail() {
        val email = binding.loginEmail.text.toString()
        val password = binding.loginPassword.text.toString()
        val user = auth.currentUser

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    val intent = Intent(this, LoggedInActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }

        } else {
            Toast.makeText(this, "Empty Fields Not allowed!", Toast.LENGTH_SHORT).show()

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
                    Toast.makeText(this, "Logged in successfully",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoggedInActivity::class.java))
                    finish()
                }
            }.addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
    }

}