package com.example.voicerecognitionappteacheradmin.CustomAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.voicerecognitionappteacheradmin.DataClass.Classes
import com.example.voicerecognitionappteacheradmin.R

class ClassesCustomAdapter(context: Context, private val resource: Int, private val Items: List<Classes>) : ArrayAdapter<Classes>(context, resource, Items){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val item = Items[position]

        val view: View = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)


        val imageView: ImageView = view.findViewById(R.id.imageView_item)
        val textViewClassName: TextView = view.findViewById(R.id.textView_class_name)
        val textViewClassNameDetail: TextView = view.findViewById(R.id.textView_class_name_detail)

        imageView.setImageResource(R.drawable.classitemlist1)
        textViewClassName.text = item.class_name
        textViewClassNameDetail.text = item.section_id

        return view
    }

}