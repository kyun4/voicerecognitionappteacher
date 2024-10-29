package com.example.voicerecognitionappteacheradmin.DataClass;

public class SchoolYearClass {
    String schoolyear_id;
    String schoolyear_name;
    String created_by;
    String date_time_created;
    String date_time_updated;
    String last_updated_by;
    String status;

    public SchoolYearClass() {
    }

    public String getSchoolyear_id() {
        return schoolyear_id;
    }

    public void setSchoolyear_id(String schoolyear_id) {
        this.schoolyear_id = schoolyear_id;
    }

    public String getSchoolyear_name() {
        return schoolyear_name;
    }

    public void setSchoolyear_name(String schoolyear_name) {
        this.schoolyear_name = schoolyear_name;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SchoolYearClass(String schoolyear_id, String schoolyear_name, String created_by, String date_time_created, String date_time_updated, String last_updated_by, String status) {
        this.schoolyear_id = schoolyear_id;
        this.schoolyear_name = schoolyear_name;
        this.created_by = created_by;
        this.date_time_created = date_time_created;
        this.date_time_updated = date_time_updated;
        this.last_updated_by = last_updated_by;
        this.status = status;
    }
}
