package com.example.voicerecognitionappteacheradmin.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.voicerecognitionappteacheradmin.DataClass.UsersClass
import com.example.voicerecognitionappteacheradmin.LoginActivity
import com.example.voicerecognitionappteacheradmin.databinding.FragmentAccountBinding
import com.example.voicerecognitionappteacheradmin.databinding.FragmentGalleryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.w3c.dom.Text

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    val list_users = mutableListOf<UsersClass>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        auth = FirebaseAuth.getInstance()

        val accountViewModel =
            ViewModelProvider(this).get(AccountViewModel::class.java)

        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textAccount
//        accountViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        val buttonLogout: Button = binding.buttonSignOut
        val textTeacherFullname: TextView = binding.textViewTeacherFullname
        var textTeacherEmail: TextView = binding.textViewTeacherEmail

        buttonLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }

        if(auth.currentUser != null){
            textTeacherEmail.setText(auth.currentUser?.email)
            getUsersAndDisplayLoggedInDetails(textTeacherFullname,auth.currentUser?.uid.toString())
        }

        return root
    }

    fun getUsersAndDisplayLoggedInDetails(textview_teacher_fullname: TextView, fuid: String){

        val database_ref: DatabaseReference
        database_ref = FirebaseDatabase.getInstance().getReference("/users/")

        database_ref.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                var object_records = snapshot.children.mapNotNull { child ->
                    child.getValue(UsersClass::class.java)
                }

                for(data_records in object_records){
                    list_users.add(data_records)
                }


                for(list_item_users in list_users){
                    if(fuid.equals(list_item_users.firebase_uid.toString())){
                        val teacher_fullname = list_item_users.firstname +" "+list_item_users.lastname


                        textview_teacher_fullname.setText(teacher_fullname)

                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })




    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}