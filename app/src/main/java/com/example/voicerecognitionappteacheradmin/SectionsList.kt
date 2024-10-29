package com.example.voicerecognitionappteacheradmin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.voicerecognitionappteacheradmin.CustomAdapters.SectionCustomAdapter
import com.example.voicerecognitionappteacheradmin.DataClass.SchoolYearClass
import com.example.voicerecognitionappteacheradmin.DataClass.SectionClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SectionsList : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var current_sy_id: String

    private var listview: ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sections_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()

        val buttonAddSection: Button = findViewById(R.id.buttonAddSection)

        val imageBack: ImageView = findViewById<ImageView>(R.id.imageView_back_home)

        var listview: ListView = findViewById(R.id.listview_sections)

        var listItemSection = mutableListOf<SectionClass>()

        current_sy_id = ""

        if(auth.currentUser?.uid != null){
            var firebaseUID = auth.currentUser?.uid.toString()
            getCurrentActiveSY(firebaseUID)
            populateSections(listview, listItemSection, firebaseUID)
        }

        imageBack.setOnClickListener {
            val intent = Intent(baseContext, MainMenu::class.java)
            startActivity(intent)
        }

        buttonAddSection.setOnClickListener {
            val intent = Intent(baseContext, AddSection::class.java)
            startActivity(intent)
        }


    }

    fun getCurrentActiveSY(firebase_uid: String){
        val dbref: DatabaseReference
        dbref = FirebaseDatabase.getInstance().getReference("/schoolyear/")

        dbref.addValueEventListener(object: ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {

                var record_items = snapshot.children.mapNotNull { child ->
                    child.getValue(SchoolYearClass::class.java)
                }

                for(item_records in record_items){
                    if(firebase_uid.equals(item_records.created_by) && item_records.status.equals("1")){
                        current_sy_id = item_records.schoolyear_id
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


    fun populateSections(listview: ListView, listItemSection: MutableList<SectionClass>, firebase_uid: String){

        val database_ref: DatabaseReference

        database_ref = FirebaseDatabase.getInstance().getReference("/section/")

        database_ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                var objects = snapshot.children.mapNotNull { child ->
                    child.getValue(SectionClass::class.java)
                }

                for(object_item in objects){
                    if(object_item.teacher_id.equals(firebase_uid) && object_item.sy.equals(current_sy_id)){
                        listItemSection.add(object_item)
                    }
                }

                val adapter = SectionCustomAdapter(baseContext, R.layout.section_detail_item,listItemSection)

                listview.adapter = adapter

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext, "Sections cannot be loaded: ${error}", Toast.LENGTH_LONG).show()
            }
        })

    }
}