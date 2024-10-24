package com.example.voicerecognitionappteacheradmin

import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.voicerecognitionappteacheradmin.DataClass.UsersClass
import com.example.voicerecognitionappteacheradmin.databinding.ActivityMainMenuBinding
import com.google.android.material.navigation.NavigationBarMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainMenu : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainMenuBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMainMenu.toolbar)

        auth = FirebaseAuth.getInstance()

        binding.appBarMainMenu.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val headerView = navView.getHeaderView(0)

        val headerTextViewUsername = headerView.findViewById<TextView>(R.id.textview_username)
        val headerTextViewEmail = headerView.findViewById<TextView>(R.id.textview_email)

        if(auth.currentUser?.uid != null){
            headerTextViewEmail.setText(auth.currentUser?.email)
            getUsersAndDisplayLoggedInDetails(headerTextViewUsername, auth.currentUser?.uid.toString())
        }

        val navController = findNavController(R.id.nav_host_fragment_content_main_menu)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


    fun getUsersAndDisplayLoggedInDetails(textview_teacher_fullname: TextView, fuid: String){

        val database_ref: DatabaseReference
        database_ref = FirebaseDatabase.getInstance().getReference("/users/")

        database_ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                var object_records = snapshot.children.mapNotNull { child ->
                    child.getValue(UsersClass::class.java)
                }

                for(data_records in object_records){
                    if(fuid.equals(data_records.firebase_uid.toString())){
                        val teacher_fullname = data_records.firstname +" "+data_records.lastname


                        textview_teacher_fullname.setText(teacher_fullname)

                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })




    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main_menu)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}