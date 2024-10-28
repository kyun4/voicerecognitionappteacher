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
import com.example.voicerecognitionappteacheradmin.DataClass.Classes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddClassForm : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private lateinit var firebase_uid: String
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_class_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()

        val yearlevel_list = listOf(" [Select Year Level] ","Grade 1","Grade 2","Grade 3","Grade 4","Grade 5","Grade 6")
        val section_list = listOf(" [Select Section] ","Mahongany","Narra","Sampaloc","ipil-Ipil")




        val imageback = findViewById<ImageView>(R.id.imageView_back_class_menu)
        val et_class_name = findViewById<EditText>(R.id.et_class_name)
        val spinner_yearlevel = findViewById<Spinner>(R.id.spinner_yearlevel)
        val spinner_sections = findViewById<Spinner>(R.id.spinner_sections)
        val button_save = findViewById<Button>(R.id.buttonCreateClass)

        if(auth.currentUser?.uid != null){
            firebase_uid = auth.currentUser?.uid.toString()
        }

        imageback.setOnClickListener {
            val intent = Intent(baseContext, ClassMenu::class.java)
            startActivity(intent)
        }


        val yearlevel_adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, yearlevel_list)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, section_list)

        yearlevel_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner_yearlevel.adapter = yearlevel_adapter
        spinner_sections.adapter = adapter


        button_save.setOnClickListener {

            var className: String = ""
            var error_messages: String = ""

            if(et_class_name.text.isEmpty()){
                error_messages += "Please specify class name\n";
            }else{
                className = et_class_name.text.toString()
            }


            if(error_messages.trim().equals("")){

                addClass(className, "section_id_1", "sy",firebase_uid)
                et_class_name.text.clear()
                val intent = Intent(baseContext, MainMenu::class.java)
                startActivity(intent)

            }else{
                Toast.makeText(baseContext, error_messages.trim(), Toast.LENGTH_LONG).show();
            }

        }
    }

    fun addClass(class_name: String, section_id: String, sy: String, teacher_id: String){
        val database : DatabaseReference
        database = FirebaseDatabase.getInstance().getReference("classes")

        var class_id: String = ""
        class_id = database.push().key.toString()

        database.child(class_id).setValue(Classes(class_id,class_name,section_id,teacher_id,"",sy)).addOnSuccessListener {
            Toast.makeText(baseContext,"One Class Added "+class_name, Toast.LENGTH_LONG).show();
        }.addOnFailureListener {
            Toast.makeText(baseContext,"Failed Adding Class ${it.toString()}", Toast.LENGTH_LONG).show();
        }



    }
}