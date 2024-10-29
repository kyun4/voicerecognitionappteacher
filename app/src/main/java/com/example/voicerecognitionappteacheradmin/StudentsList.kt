package com.example.voicerecognitionappteacheradmin

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.voicerecognitionappteacheradmin.CustomAdapters.StudentAdapter
import com.example.voicerecognitionappteacheradmin.DataClass.UsersClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class StudentsList : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_students_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()

        val listItemStudents = mutableListOf<UsersClass>()

        val imageBackHome: ImageView = findViewById(R.id.imageView_back_home)
        val listview_students: ListView = findViewById(R.id.listview_students)

        populateStudentList(listview_students, listItemStudents)

        imageBackHome.setOnClickListener {
            val intent = Intent(baseContext, MainMenu::class.java)
            startActivity(intent)
        }
    }

    fun populateStudentList(listview_students: ListView, ListItems: MutableList<UsersClass>){
        var dbref_students: DatabaseReference
        dbref_students = FirebaseDatabase.getInstance().getReference("/users/")

        dbref_students.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               var object_records = snapshot.children.mapNotNull { child ->
                   child.getValue(UsersClass::class.java)
               }

                for(item_records in object_records){
                    if(item_records.role.equals("student")){
                        ListItems.add(item_records)
                    }

                }

                var adapter = StudentAdapter(baseContext, R.layout.list_item_custom, ListItems)
                listview_students.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}