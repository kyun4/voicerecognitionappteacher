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
import com.example.voicerecognitionappteacheradmin.DataClass.SectionClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ClassMenu : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_class_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()

        var listClassItem = mutableListOf<Classes>()
        var listSectionItem = mutableListOf<SectionClass>()

        var listview: ListView = findViewById<ListView>(R.id.listview_classes)

        val imageback: ImageView = findViewById<ImageView>(R.id.imageView_back_home)

        val button_add_class: Button = findViewById<Button>(R.id.buttonAddClass)


        if(auth.currentUser?.uid != null){
            populateClasses(listview, listClassItem, listSectionItem , auth.currentUser?.uid.toString())
        }



        button_add_class.setOnClickListener {
            val intent = Intent(this, AddClassForm::class.java)
            startActivity(intent)
        }

        imageback.setOnClickListener {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
        }
    }


    fun populateClasses(listview: ListView, listClassItem: MutableList<Classes>, listSectionItem: MutableList<SectionClass>,  firebase_uid: String){

        val databaseClassRef: DatabaseReference
        databaseClassRef = FirebaseDatabase.getInstance().getReference("/section/")
        databaseClassRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                var objects = snapshot.children.mapNotNull { child ->
                    child.getValue(SectionClass::class.java)
                }

                for(item_records in objects){
                    listSectionItem.add(item_records)
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        val database_ref: DatabaseReference
        database_ref = FirebaseDatabase.getInstance().getReference("/classes/")
        database_ref.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                var objects = snapshot.children.mapNotNull { child ->
                    child.getValue(Classes::class.java)
                }

                for(object_item in objects){

                    if(object_item.teacher_id.equals(firebase_uid)){
                        listClassItem.add(object_item)
                    }

                }

                val adapter = ClassesCustomAdapter(baseContext, R.layout.class_item_details, listClassItem,listSectionItem)

                listview.adapter = adapter

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext,"Loading Class List Failed ${error}", Toast.LENGTH_LONG).show()
            }
        })


    }
}