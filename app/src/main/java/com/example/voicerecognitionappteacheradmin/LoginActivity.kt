package com.example.voicerecognitionappteacheradmin

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)


        FirebaseApp.initializeApp(this);
        //addDataToFirebase()

        auth = FirebaseAuth.getInstance();

        if(auth.currentUser?.uid != null){
           val intent = Intent(this, LoadingLogin::class.java)
            intent.putExtra("firebase_uid", auth.currentUser?.uid)
           startActivity(intent);
        }

        val btn = findViewById<Button>(R.id.buttonSignIn);
        val text_forgot_password = findViewById<TextView>(R.id.textView_forgot_password);
        val text_signup = findViewById<TextView>(R.id.textView_create_account);

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics);

        val textview_signup_params = text_signup.layoutParams
        textview_signup_params.width = displayMetrics.widthPixels/4;

        val edittext_email = findViewById<EditText>(R.id.email_login);
        val edittext_password = findViewById<EditText>(R.id.password_login);


        btn.setOnClickListener {

            var error_message = "";

            var email_input: String = "";
            var password_input: String = "";

            if(edittext_email.text.toString().isEmpty()) {
                error_message += "Please Specify Email Address\n";
            }

            if(edittext_password.text.toString().isEmpty()) {
                error_message += "Please Specify your Password\n";
            }else{
                email_input = edittext_email.text.toString();
            }

            if(email_input.equals("")){

            }else{
                if(Patterns.EMAIL_ADDRESS.matcher(email_input).matches()){

                }else{
                    error_message += "Invalid Email Address Format\nCheck your Entered Email";
                }
            }



            if(error_message.equals("")){

                password_input = edittext_password.text.toString();
                loginUser(email_input, password_input);

            }else{

                Toast.makeText(this, error_message.trim(), Toast.LENGTH_SHORT).show();

            }



        }

        text_forgot_password.setOnClickListener {
            val intent = Intent(this, ForgotPassword::class.java);
            startActivity(intent);
        }

        text_signup.setOnClickListener {
            val intent = Intent(this, SignUp::class.java);
            startActivity(intent);
        }

    }

    fun loginUser(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {task ->

            if(task.isSuccessful){
                val user = task.result?.user;
                val intent = Intent(this, LoadingLogin::class.java)
                intent.putExtra("firebase_uid", auth.currentUser?.uid.toString())
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(this, "Login Failed: "+task.exception?.message.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }



}