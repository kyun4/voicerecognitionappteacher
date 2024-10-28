package com.example.voicerecognitionappteacheradmin.CustomAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.voicerecognitionappteacheradmin.DataClass.SectionClass
import com.example.voicerecognitionappteacheradmin.R

class SectionCustomAdapter(context: Context, private val resource:Int, private val listItem: List<SectionClass>): ArrayAdapter<SectionClass>(context, resource, listItem) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val item = listItem[position]

        val view: View = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)

        val imageViewIcon: ImageView = view.findViewById(R.id.imageView_section_icon)
        val textviewTitle: TextView = view.findViewById(R.id.textView_section_name)
        val textviewSubtitle: TextView = view.findViewById(R.id.textView_section_detail)

        val listGradeLevel = listOf("Grade 1","Grade 2","Grade 3","Grade 4","Grade 5","Grade 6")
        var gradeLevel = listGradeLevel[Integer.parseInt(item.yearlevel_id)]

        textviewTitle.setText(item.section_name)
        textviewSubtitle.setText(gradeLevel)

        imageViewIcon.setImageResource(R.drawable.sectionitem1)

        return view
    }

}
