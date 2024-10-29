package com.example.voicerecognitionappteacheradmin

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.voicerecognitionappteacheradmin.DataClass.SchoolYearClass
import com.example.voicerecognitionappteacheradmin.DataClass.SectionClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AddSection : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var current_sy_id: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_section)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()
        current_sy_id = ""

        if(auth.currentUser?.uid != null){
            getCurrentActiveSY(auth.currentUser?.uid.toString())
        }

        val imageBack = findViewById<ImageView>(R.id.imageView_back_section_list)
        val buttonAddSection = findViewById<Button>(R.id.buttonCreateSection)
        val et_section_name = findViewById<EditText>(R.id.et_section_name)
        val spinner_yearlevel = findViewById<Spinner>(R.id.spinner_yearlevel)

        var listyear_level = listOf("[Select Level]","Grade 1","Grade 2","Grade 3","Grade 4","Grade 5","Grade 6")
        val adapter = ArrayAdapter(baseContext, android.R.layout.simple_spinner_item, listyear_level)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_yearlevel.adapter = adapter

        imageBack.setOnClickListener {
            val intent = Intent(baseContext, SectionsList::class.java)
            startActivity(intent)
        }

        buttonAddSection.setOnClickListener {

            var section_name = ""
            var yearlevel_id = "";
            var error_message = ""

            if(et_section_name.text.isEmpty()){
                error_message += "Specify Section Name\n";
            }else{
                section_name = et_section_name.text.toString()
            }

            if(spinner_yearlevel.selectedItemPosition < 1){
                error_message += "Specify Section Year Level\n";
            }else{
                yearlevel_id = spinner_yearlevel.selectedItemId.toString()
            }

            if(error_message.trim().equals("")){

                if(auth.currentUser?.uid != null){
                    addSection(section_name,Integer.parseInt(yearlevel_id)-1, auth.currentUser?.uid.toString(),current_sy_id)
                    et_section_name.text.clear()
                    spinner_yearlevel.setSelection(0)

                    Toast.makeText(baseContext,"One Section Added: "+section_name+"", Toast.LENGTH_LONG).show()

                    val intent = Intent(this, SectionsList::class.java)
                    startActivity(intent)
                    finish()

                }else{
                    Toast.makeText(baseContext,"Please login to continue the action", Toast.LENGTH_LONG).show()
                }


            }else{
                Toast.makeText(baseContext, error_message, Toast.LENGTH_LONG).show()
            }

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

    fun addSection(section_name: String, yearlevel_id: Int, teacher_id: String, sy: String){

        val databaseRef: DatabaseReference
        databaseRef = FirebaseDatabase.getInstance().getReference("/section/")

        var section_id = databaseRef.push().key
        var yearlevel_id_string = yearlevel_id.toString()

        databaseRef.child(section_id.toString()).setValue(SectionClass(section_id,section_name,sy,teacher_id,"","","",yearlevel_id_string))

    }
}