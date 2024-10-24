package com.example.voicerecognitionappteacheradmin

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.voicerecognitionappteacheradmin.DataClass.UsersClass
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SignUp : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up);

        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();



        val img_back = findViewById<ImageView>(R.id.imageView_back);
        val textview_login = findViewById<TextView>(R.id.textView_login);

        val edittext_username = findViewById<EditText>(R.id.textview_desired_username);
        val edittext_email = findViewById<EditText>(R.id.textview_email);
        val edittext_firstname = findViewById<EditText>(R.id.et_firstname);
        val edittext_lastname = findViewById<EditText>(R.id.et_lastname);
        val edittext_phone = findViewById<EditText>(R.id.textview_phone);
        val edittext_password = findViewById<EditText>(R.id.desired_password);
        val edittext_retry_password = findViewById<EditText>(R.id.retry_password);

        val button_create_account = findViewById<Button>(R.id.buttonCreateAccount);

        val displayMetics = DisplayMetrics();
        windowManager.defaultDisplay.getMetrics(displayMetics);

        val textlogin_param = textview_login.layoutParams;
        textlogin_param.width = displayMetics.widthPixels/7;

        img_back.setOnClickListener {
            val intent= Intent(this, LoginActivity::class.java);
            startActivity(intent);
        }

        button_create_account.setOnClickListener {

            var username: String = "";
            var phone: String = "";
            var firstname: String = "";
            var lastname: String = "";
            var email_input: String = "";
            var password_input: String = "";
            var retry_password_input: String = "";
            var error_messages = "";

            if(edittext_username.text.toString().isEmpty()){
                error_messages += "Please specify your Username\n";
            }else{
                username = edittext_username.text.toString();
            }

            if(edittext_firstname.text.toString().isEmpty()){
                error_messages += "Please specify your First Name\n";
            }else{
                firstname = edittext_firstname.text.toString();
            }

            if(edittext_lastname.text.toString().isEmpty()){
                error_messages += "Please specify your Last Name\n";
            }else{
                lastname = edittext_lastname.text.toString();
            }

            if(edittext_phone.text.toString().isEmpty()){
                error_messages += "Please specify your Mobile Phone Number\n";
            }else{
                phone = edittext_phone.text.toString();
            }

            if(edittext_email.text.toString().isEmpty()){
                error_messages += "Please specify your E-mail Address\n";
            }else{
                if(!edittext_email.text.toString().equals("")){

                    email_input = edittext_email.text.toString();

                    if(!Patterns.EMAIL_ADDRESS.matcher(email_input).matches()){
                        error_messages += "Invalid Email Address Format\nCheck your entered E-mail Address";
                    }
                }
            }

            if(edittext_password.text.toString().isEmpty()){
                error_messages += "Please specify your Password\n";
            }else{
                password_input = edittext_password.text.toString();

                if(password_input.length < 8){
                    error_messages += "Password must be atleast 8 characters long";
                }
            }


            if(!edittext_retry_password.text.toString().isEmpty()){
                retry_password_input = edittext_retry_password.text.toString();
            }

            if(retry_password_input != password_input){
                error_messages += "Password mismatch";
            }

            error_messages += getAllUsers(email_input, phone, username);

            if(error_messages.equals("")){
                register(username, "", firstname,lastname, phone,email_input, password_input);

                edittext_username.setText("");
                edittext_email.setText("");
                edittext_phone.setText("");
                edittext_password.setText("");
                edittext_retry_password.setText("");
                edittext_firstname.setText("");
                edittext_lastname.setText("");

            }else{
                Toast.makeText(this,error_messages.trim(), Toast.LENGTH_LONG).show();
            }




        }

        textview_login.setOnClickListener {
            val intent= Intent(this, LoginActivity::class.java);
            startActivity(intent);
        }



    }



    private fun register(username: String, address: String, firstname: String, lastname: String, phone: String,email: String, password: String){

        if(email.isEmpty() || password.isEmpty())
        {
            Toast.makeText(this, "Make sure you fill all the details especially Email and Password", Toast.LENGTH_SHORT).show();

        }else{
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task->
                if(task.isSuccessful){

                    val user = task.result?.user;


                    val user_id: String = user?.uid ?: "you-id";

                    addUserDetailsToFirebase(username,email,address,user_id,firstname,lastname,phone);

//                    Toast.makeText(this, "Sign Up Complete! You may now login your account!", Toast.LENGTH_LONG).show();

                    auth.signOut();

                    val intent = Intent(this, SignUpComplete::class.java);
                    startActivity(intent);


                }else{
                    Toast.makeText(this, "Login Failed "+task.exception?.message.toString()+"", Toast.LENGTH_LONG).show();
                }
            }
        }


    } // register

    fun getAllUsers(email: String, phone: String, username: String): String{


        var error_messages = "";

        val users_list =  mutableListOf<UsersClass>();

        val database: DatabaseReference;

        database = FirebaseDatabase.getInstance().getReference("/users/");

        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {


                var objects =snapshot.children.mapNotNull { child->
                    child.getValue(UsersClass::class.java);
                }

                for (dataRecord in objects){

                    users_list.add(dataRecord);

                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext,"Checking Existing Users Cancelled\nDetails: "+error.message.toString(),Toast.LENGTH_LONG).show();
            }
        })

        for(users_item in users_list){
            if(users_item.email.equals(email)){
                error_messages += "Email Already Exists\n";
            }
            if(users_item.phone.equals(phone)){
                error_messages += "Phone Number Already Exists\n";
            }
            if(users_item.username.equals(username)){
                error_messages += "Username Already Exists\n";
            }
        }

        return error_messages;

        //users_list.add(UsersClass("","","","","","","","","",""));

    }

    fun addUserDetailsToFirebase(username: String, email: String, address: String, fuid: String, firstname: String, lastname: String, phone: String){

        var date_time_registered = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getDateTimeDefaultFormat()
        } else {
            TODO("VERSION.SDK_INT < O")
            "";
        };


        val database: DatabaseReference;

        database = FirebaseDatabase.getInstance().getReference("/users/");


        database.push().setValue(UsersClass(""+username+"",""+email+"",""+address+"",""+fuid+"",""+firstname+"",""+lastname+"",""+phone+"",""+date_time_registered+"","teacher"));


    } // addUserDetailsToFirebase

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDateTimeDefaultFormat(): String {

        val now = LocalDateTime.now();
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        val formattedDate = now.format(formatter);

        return formattedDate.toString();
    }
}