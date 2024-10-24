package com.example.voicerecognitionappteacheradmin

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.example.voicerecognitionappteacheradmin.DataClass.UsersClass
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ForgotPassword : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    var users_list =  mutableListOf<UsersClass>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_password)

        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();



        var email_input = "";


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        val imageview_back = findViewById<ImageView>(R.id.imageView_back_forgot_password);
        val button_send_email = findViewById<Button>(R.id.buttonSendEmailForPasswordReset);
        val edittext_email = findViewById<EditText>(R.id.et_email_reset_password);

        imageview_back.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java);
            startActivity(intent);
        }

        getAllUsers(button_send_email);

            button_send_email.setOnClickListener {

                var error_messages = "";
                var email_found = 0;

                if(edittext_email.text.toString().isEmpty()){
                    error_messages += "Please specify your last registered e-mail\n";
                }else{
                    email_input = edittext_email.text.toString();

                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email_input).matches()){
                    error_messages += "Invalid Email Address Format\n";
                }

                for(user_item_list in users_list){
                    if(email_input.equals(user_item_list.email)){
                        email_found += 1;
                    }
                }

                if(!email_input.equals(""))
                {
                    if(email_found > 0){



                    }else{
                        error_messages += "Email not found!\nNot yet registered? please sign up to continue\n";
                        Toast.makeText(this, error_messages.trim(),Toast.LENGTH_LONG).show();
                    }
                }


                if(error_messages.equals("")){


                        auth.sendPasswordResetEmail(email_input).addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                Toast.makeText(this, "Reset Password Link sent to your E-mail\nYou may check now to reset your password!", Toast.LENGTH_LONG).show();
                                val intent = Intent(this, LoginActivity::class.java);
                                startActivity(intent);



                                edittext_email.setText("");
                            }else{
                                val exception = task.exception;
                                Toast.makeText(this, "Sending Password Reset Email Failed: "+exception.toString(), Toast.LENGTH_LONG).show();
                            }
                        }





                }else{
                    Toast.makeText(this, error_messages.trim(),Toast.LENGTH_LONG).show();
                }

            }



    }

    fun getAllUsers(button_reset_email: Button){


        var error_messages = "";


        var found_count: Int = 0;

        val database: DatabaseReference;

        database = FirebaseDatabase.getInstance().getReference("/users/");

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){


                    var objects =snapshot.children.mapNotNull { child->
                        child.getValue(UsersClass::class.java);
                    }

                    for (dataRecord in objects){

                        users_list.add(dataRecord);

                    }

                    button_reset_email.visibility = View.VISIBLE;

                }else{
                    button_reset_email.visibility = View.INVISIBLE;
                }


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext,"Checking Existing Users Cancelled\nDetails: "+error.message.toString(),Toast.LENGTH_LONG).show();
            }
        })



        //users_list.add(UsersClass("","","","","","","","","",""));

    }
}