package com.example.voicerecognitionappteacheradmin.CustomAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.voicerecognitionappteacheradmin.DataClass.UsersClass
import com.example.voicerecognitionappteacheradmin.R

class StudentAdapter(context: Context, private val resource: Int, private val listItem: List<UsersClass>) :
    ArrayAdapter<UsersClass>(context, resource, listItem) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup) : View {

            val item = listItem[position]

            var view:View = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)

            val imageView = view.findViewById<ImageView>(R.id.imageView_icon)
            val textViewTitle = view.findViewById<TextView>(R.id.textView_title)
            val textViewSubtitle = view.findViewById<TextView>(R.id.textView_subtitle)

            imageView.setImageResource(R.drawable.user04)

            var fullname = item.firstname+" "+item.lastname
            var email = item.email
            var phone = item.phone

            textViewTitle.setText(fullname)
            textViewSubtitle.setText(email+"\n+63"+phone)

            return view
        }
}