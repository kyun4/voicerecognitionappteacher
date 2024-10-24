package com.example.voicerecognitionappteacheradmin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.voicerecognitionappteacheradmin.DataClass.UsersClass
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoadingLogin : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_loading_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        FirebaseApp.initializeApp(this);

        auth = FirebaseAuth.getInstance();

        var firebase_uid = intent.getStringExtra("firebase_uid")
        var hasRecordExist: Boolean = false

        val handler = Handler(Looper.getMainLooper())

        hasRecord(firebase_uid.toString()) {exist ->
            if(exist){
                hasRecordExist = true
                runAfterDelay(handler, hasRecordExist);

            }else{
                hasRecordExist = false
                runAfterDelay(handler, hasRecordExist);

            }
        }






    }


    fun runAfterDelay(handler: Handler, hasRecordExistHere: Boolean) {
        handler.postDelayed({
            // Call your function here

            if(hasRecordExistHere === true){
                val ii = Intent(this, MainMenu::class.java)
                startActivity(ii)
            }else{
                auth.signOut();
                Toast.makeText(baseContext, "You cannot login other role here", Toast.LENGTH_LONG).show();
                val ii = Intent(this, LoginActivity::class.java)
                startActivity(ii)
            }

        }, 1000)
    }


    fun hasRecord(fuid: String, callback: (Boolean) -> Unit) {
        val database_ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("/users/")

        database_ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val objects = snapshot.children.mapNotNull { child ->
                    child.getValue(UsersClass::class.java)
                }

                val isExist = objects.any {
                    it.role == "teacher" && it.firebase_uid == fuid
                }

                // Notify the callback with the result
                callback(isExist)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext, "Error on reading Firebase Realtime DB on users, details: ${error.message}", Toast.LENGTH_LONG).show()
                // Notify the callback with false in case of an error
                callback(false)
            }
        })
    }

}