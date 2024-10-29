package com.example.voicerecognitionappteacheradmin.CustomAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.voicerecognitionappteacheradmin.DataClass.TeacherLessonPlanClass

class TeacherLessonPlanAdapter(context: Context, private val resource: Int, listLessonPlan: List<TeacherLessonPlanClass>) :
    ArrayAdapter<TeacherLessonPlanClass>(context, resource, listLessonPlan) {

        override fun getView(position:Int, convertView: View?, parent: ViewGroup) :View {

            var view: View = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)



            return view

        }
}