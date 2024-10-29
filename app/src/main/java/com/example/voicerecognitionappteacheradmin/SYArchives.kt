package com.example.voicerecognitionappteacheradmin

import android.content.Intent
import android.os.Bundle
import android.renderscript.Sampler.Value
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.voicerecognitionappteacheradmin.CustomAdapters.SchoolYearCustomAdapter
import com.example.voicerecognitionappteacheradmin.DataClass.SchoolYearClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SYArchives : AppCompatActivity() {

    private lateinit var  auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_syarchives)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imageviewBack: ImageView = findViewById(R.id.imageView_back_home)
        val listViewSchoolYear: ListView = findViewById(R.id.listview_sy_archives)
        val buttonSchoolYear: Button = findViewById(R.id.buttonAddSY)
        var list_schoolyear = mutableListOf<SchoolYearClass>()

        auth = FirebaseAuth.getInstance()

        if(auth.currentUser?.uid != null){
            populateSchoolYear(auth.currentUser?.uid.toString(), listViewSchoolYear, list_schoolyear)
        }else{
            Toast.makeText(baseContext,"Please login to continue", Toast.LENGTH_LONG).show()
            auth.signOut();
            val intent = Intent(baseContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        imageviewBack.setOnClickListener {
            val intent = Intent(baseContext, MainMenu::class.java)
            startActivity(intent)
        }

        listViewSchoolYear.setOnItemClickListener{
            parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            val selectedIndex = position

            if(auth.currentUser?.uid != null){

                var firebase_uid = auth.currentUser?.uid.toString()
                showDialog(firebase_uid,list_schoolyear.get(position).schoolyear_name,list_schoolyear.get(position).schoolyear_id,list_schoolyear.get(position).status)

            }

        }




        buttonSchoolYear.setOnClickListener {
            val intent = Intent(baseContext, AddSYArchive::class.java)
            startActivity(intent)
        }

    }

    fun showDialog(firebase_uid: String, schoolyear_name: String, schoolyear_id: String, schoolyear_status: String){

        var schoolyearStatus = ""
        var makeStatus = ""
        var toUpdateStatus = ""

        schoolyearStatus = when { schoolyear_status.equals("1") -> "Active" else -> "Inactive" }
        makeStatus = when { schoolyear_status.equals("1") -> "Make Inactive" else -> "Make Active" }
        toUpdateStatus = when { schoolyear_status.equals("1") -> "0" else -> "1" }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("School Year")
        builder.setMessage(schoolyear_name+" ("+schoolyearStatus+")")

        builder.setPositiveButton(makeStatus){
            dialog, which ->

            val intent = Intent(baseContext, MainMenu::class.java)
            startActivity(intent)

            updateInactiveStatusAll(firebase_uid, schoolyear_name, schoolyear_id,toUpdateStatus)



        }

        builder.setNeutralButton("Cancel"){
            dialog, which ->

            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()

    }


    fun updateStatus(schoolyear_name: String, schoolyear_id: String, status_string: String){

        val dbref: DatabaseReference
        dbref = FirebaseDatabase.getInstance().getReference("/schoolyear/").child(schoolyear_id)

        var statusPrompt = when { status_string.equals("1") -> "School Year is Activated" else -> "School Year is Deactivated" }

        val updates = mapOf<String, Any>(
            "status" to status_string
        )

        dbref.updateChildren(updates).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(baseContext,schoolyear_name+" "+statusPrompt, Toast.LENGTH_LONG).show()
            }else{

            }
        }

    }

    fun updateInactiveStatusAll(firebase_uid: String, schoolyear_name: String, schoolyear_id: String, status_string: String){

        val dbref_deactivate: DatabaseReference
        dbref_deactivate = FirebaseDatabase.getInstance().getReference("/schoolyear/")


        dbref_deactivate.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                var object_records = snapshot.children.mapNotNull { child ->
                    child.getValue(SchoolYearClass::class.java)
                }

                for(item_records in object_records){

                    if(item_records.created_by.equals(firebase_uid)){

                        val dbref_update: DatabaseReference
                        dbref_update = FirebaseDatabase.getInstance().getReference("/schoolyear/").child(item_records.schoolyear_id)
                        val updates = mapOf<String, Any>(
                            "status" to "0"
                        )

                        dbref_update.updateChildren(updates).addOnCompleteListener { task ->
                            if(task.isSuccessful){



                            }else{

                            }
                        }

                    }


                }


                updateStatus(schoolyear_name, schoolyear_id, status_string)


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })



    }

    fun populateSchoolYear(firebase_uid: String, listview: ListView, listitem: MutableList<SchoolYearClass>){

        listitem.clear()

        val dbref: DatabaseReference
        dbref = FirebaseDatabase.getInstance().getReference("/schoolyear/")

        dbref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               var object_record = snapshot.children.mapNotNull { child->
                   child.getValue(SchoolYearClass::class.java)
               }

                for(item_record in object_record){
                    if(item_record.created_by.equals(firebase_uid)){
                        listitem.add(item_record)
                    }
                }


                val adapter = SchoolYearCustomAdapter(baseContext, R.layout.list_item_custom, listitem)
                listview.adapter = adapter

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }
}