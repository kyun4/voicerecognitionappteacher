package com.example.voicerecognitionappteacheradmin.DataClass;

public class SectionClass {
    String section_id;
    String section_name;
    String sy;
    String teacher_id;
    String date_time_created;
    String date_time_updated;
    String last_updated_by;

    String yearlevel_id;

    public SectionClass() {
    }

    public String getSection_id() {
        return section_id;
    }

    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }

    public String getSection_name() {
        return section_name;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }

    public String getSy() {
        return sy;
    }

    public void setSy(String sy) {
        this.sy = sy;
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

    public String getYearlevel_id() {
        return yearlevel_id;
    }

    public void setYearlevel_id(String yearlevel_id) {
        this.yearlevel_id = yearlevel_id;
    }

    public SectionClass(String section_id, String section_name, String sy, String teacher_id, String date_time_created, String date_time_updated, String last_updated_by, String yearlevel_id) {
        this.section_id = section_id;
        this.section_name = section_name;
        this.sy = sy;
        this.teacher_id = teacher_id;
        this.date_time_created = date_time_created;
        this.date_time_updated = date_time_updated;
        this.last_updated_by = last_updated_by;
        this.yearlevel_id = yearlevel_id;
    }
}
