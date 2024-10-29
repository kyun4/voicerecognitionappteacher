package com.example.voicerecognitionappteacheradmin.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.voicerecognitionappteacheradmin.ClassMenu
import com.example.voicerecognitionappteacheradmin.DataClass.SchoolYearClass
import com.example.voicerecognitionappteacheradmin.DataClass.UsersClass
import com.example.voicerecognitionappteacheradmin.LessonPlan
import com.example.voicerecognitionappteacheradmin.SYArchives
import com.example.voicerecognitionappteacheradmin.SectionsList
import com.example.voicerecognitionappteacheradmin.StudentsList
import com.example.voicerecognitionappteacheradmin.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    private lateinit var current_sy_id: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        auth = FirebaseAuth.getInstance()

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        val textview_teacher_display_name = binding.textViewTeacherDisplayName
        val linearlayout_class_menu = binding.linearlayoutClassmenu
        val linearlayout_section_menu = binding.linearlayoutSectionMenu
        var linearlayout_student_menu = binding.linearlayoutStudent
        var linearlayout_lesson_plan_menu = binding.linearlayoutLessonPlan
        val linearlayout_schoolyear_menu = binding.linearlayoutSchoolyear

        current_sy_id = ""


        if(auth.currentUser?.uid != null){
            val firebase_uid = auth.currentUser?.uid.toString()
            displayTeacherName(textview_teacher_display_name,firebase_uid)
            getCurrentActiveSY(firebase_uid, linearlayout_student_menu, linearlayout_lesson_plan_menu, linearlayout_section_menu, linearlayout_class_menu)
        }



        linearlayout_schoolyear_menu.setOnClickListener {
            val intent = Intent(context, SYArchives::class.java)
            startActivity(intent)
        }

        return root
    }

    fun getCurrentActiveSY(firebase_uid: String,linearlayout_student_menu: LinearLayout, linearlayout_lesson_plan: LinearLayout, linearlayout_section_menu:LinearLayout, linearlayout_class_menu:LinearLayout){
        val db_ref: DatabaseReference
        db_ref = FirebaseDatabase.getInstance().getReference("/schoolyear/")

        db_ref.addValueEventListener(object: ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {

                var record_items = snapshot.children.mapNotNull { child ->
                    child.getValue(SchoolYearClass::class.java)
                }

                for(item_records in record_items){
                    if(firebase_uid.equals(item_records.created_by) && item_records.status.equals("1")){
                        current_sy_id = item_records.schoolyear_id
                    }
                }

                if(current_sy_id.trim() != ""){
                    linearlayout_class_menu.setOnClickListener {
                        val intent = Intent(context, ClassMenu::class.java);
                        startActivity(intent)
                    }

                    linearlayout_section_menu.setOnClickListener {
                        val intent = Intent(context, SectionsList::class.java)
                        startActivity(intent)
                    }

                    linearlayout_student_menu.setOnClickListener {
                        val intent = Intent(context, StudentsList::class.java)
                        startActivity(intent)
                    }

                    linearlayout_lesson_plan.setOnClickListener {
                        val intent = Intent(context, LessonPlan::class.java)
                        startActivity(intent)
                    }

                }else{
                    linearlayout_class_menu.alpha= 0.5F
                    linearlayout_section_menu.alpha= 0.5F
                }

                if(current_sy_id.trim().equals("")){
                    Toast.makeText(context,"Welcome to Voice Recognition App Teacher!\nYou may add or activate School Year\non your SY Archives", Toast.LENGTH_LONG).show()
                }

            }



            override fun onCancelled(error: DatabaseError) {

            }



        })
    }

    fun displayTeacherName(textview_teacher_displayname: TextView, fuid: String){

        val dbref: DatabaseReference
        dbref = FirebaseDatabase.getInstance().getReference("/users/")
        dbref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                val objects = snapshot.children.mapNotNull { child ->
                    child.getValue(UsersClass::class.java)
                }

                for(dataRecords in objects){
                    if(dataRecords.firebase_uid.equals(fuid) && dataRecords.role.equals("teacher")){
                        var teacher_fullname = dataRecords.firstname+" "+dataRecords.lastname
                        textview_teacher_displayname.setText(teacher_fullname)
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}