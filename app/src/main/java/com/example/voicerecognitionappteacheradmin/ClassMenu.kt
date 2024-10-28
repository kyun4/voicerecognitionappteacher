package com.example.voicerecognitionappteacheradmin

import android.content.Intent
import android.os.Bundle
import android.renderscript.Sampler.Value
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.voicerecognitionappteacheradmin.CustomAdapters.ClassesCustomAdapter
import com.example.voicerecognitionappteacheradmin.DataClass.Classes
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ClassMenu : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_class_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var listClassItem = mutableListOf<Classes>()
        var listview: ListView = findViewById<ListView>(R.id.listview_classes)

        val imageback: ImageView = findViewById<ImageView>(R.id.imageView_back_home)

        val button_add_class: Button = findViewById<Button>(R.id.buttonAddClass)



        val database_ref: DatabaseReference
        database_ref = FirebaseDatabase.getInstance().getReference("/classes/")
        database_ref.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                var objects = snapshot.children.mapNotNull { child ->
                    child.getValue(Classes::class.java)
                }

                for(object_item in objects){
                    listClassItem.add(object_item)
                }

                val adapter = ClassesCustomAdapter(baseContext, R.layout.class_item_details, listClassItem)

                listview.adapter = adapter

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext,"Loading Class List Failed ${error}", Toast.LENGTH_LONG).show()
            }
        })



        button_add_class.setOnClickListener {
            val intent = Intent(this, AddClassForm::class.java)
            startActivity(intent)
        }

        imageback.setOnClickListener {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
        }
    }

    fun populateClasses(){



    }
}