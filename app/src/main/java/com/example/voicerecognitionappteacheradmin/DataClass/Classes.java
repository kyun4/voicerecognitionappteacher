package com.example.voicerecognitionappteacheradmin.DataClass;

public class Classes {
    String class_id;
    String class_name;
    String section_id;
    String teacher_id;

    String date_time_created;
    String date_time_updated;
    String last_updated_by;

    public Classes() {
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

    public String getDate_time_created() {
        return date_time_created;
    }

    public void setDate_time_created(String date_time_created) {
        this.date_time_created = date_time_created;
    }

    public String getDate_time_updated() {
        return date_time_updated;
    }

    public void setDate_time_updated(String date_time_updated) {
        this.date_time_updated = date_time_updated;
    }

    public String getLast_updated_by() {
        return last_updated_by;
    }

    public void setLast_updated_by(String last_updated_by) {
        this.last_updated_by = last_updated_by;
    }

    public String getSy() {
        return sy;
    }

    public void setSy(String sy) {
        this.sy = sy;
    }

    public Classes(String class_id, String class_name, String section_id, String teacher_id, String date_time_created, String date_time_updated, String last_updated_by, String sy) {
        this.class_id = class_id;
        this.class_name = class_name;
        this.section_id = section_id;
        this.teacher_id = teacher_id;
        this.date_time_created = date_time_created;
        this.date_time_updated = date_time_updated;
        this.last_updated_by = last_updated_by;
        this.sy = sy;
    }

    String sy;




}
