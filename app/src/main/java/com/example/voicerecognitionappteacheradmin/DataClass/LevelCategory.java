package com.example.voicerecognitionappteacheradmin.DataClass;

public class LevelCategory {
    String category_name;
    String category_id;
    String teacher_id;

    public LevelCategory(String category_name, String category_id, String teacher_id) {
        this.category_name = category_name;
        this.category_id = category_id;
        this.teacher_id = teacher_id;
    }

    public LevelCategory() {
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }
}
