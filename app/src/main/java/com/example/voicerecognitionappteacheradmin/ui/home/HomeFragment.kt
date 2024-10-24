package com.example.voicerecognitionappteacheradmin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.voicerecognitionappteacheradmin.DataClass.UsersClass
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


        if(auth.currentUser?.uid != null){
            val firebase_uid = auth.currentUser?.uid.toString()
            displayTeacherName(textview_teacher_display_name,firebase_uid)
        }


        return root
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