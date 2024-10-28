package com.example.voicerecognitionappteacheradmin.DataClass;

public class Classes {
    String class_id;
    String class_name;
    String section_id;
    String teacher_id;
    String yearlevel_id;
    String sy;

    public Classes() {
    }

    public Classes(String class_id, String class_name, String section_id, String teacher_id, String yearlevel_id, String sy) {
        this.class_id = class_id;
        this.class_name = class_name;
        this.section_id = section_id;
        this.teacher_id = teacher_id;
        this.yearlevel_id = yearlevel_id;
        this.sy = sy;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getSection_id() {
        return section_id;
    }

    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getYearlevel_id() {
        return yearlevel_id;
    }

    public void setYearlevel_id(String yearlevel_id) {
        this.yearlevel_id = yearlevel_id;
    }

    public String getSy() {
        return sy;
    }

    public void setSy(String sy) {
        this.sy = sy;
    }
}
