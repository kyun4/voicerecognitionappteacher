package com.example.voicerecognitionappteacheradmin.CustomAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.voicerecognitionappteacheradmin.DataClass.SchoolYearClass
import com.example.voicerecognitionappteacheradmin.R

class SchoolYearCustomAdapter(context: Context, private val resource:Int, private val listItem: List<SchoolYearClass>): ArrayAdapter<SchoolYearClass>(context, resource, listItem) {
    override fun getView(position: Int, convertView: View?,  parent:ViewGroup):View{

        val item = listItem[position]

        val view:View = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)

        val imageViewIcon = view.findViewById<ImageView>(R.id.imageView_icon)
        val textViewTitle = view.findViewById<TextView>(R.id.textView_title)
        val textViewSubtitle = view.findViewById<TextView>(R.id.textView_subtitle)

        imageViewIcon.setImageResource(R.drawable.schoolyearicon1)
        textViewTitle.setText(item.schoolyear_name)

        if(item.status.trim().equals("1")){
            textViewSubtitle.setText("Active")
        }else{
            textViewSubtitle.setText("Inactive")
        }


        return view
    }
}