package com.example.voicerecognitionappteacheradmin.DataClass;

public class TeacherLessonPlanClass {
    String teacher_lesson_plan_id;
    String date_time_created;
    String date_time_updated;
    String last_updated_by;
    String status;
    String teacher_id;
    String story_mode_order_index;
    String story_mode_order_index_hard;
    String word_mode_order_index;
    String word_mode_order_index_hard;

    public TeacherLessonPlanClass() {
    }

    public String getTeacher_lesson_plan_id() {
        return teacher_lesson_plan_id;
    }

    public void setTeacher_lesson_plan_id(String teacher_lesson_plan_id) {
        this.teacher_lesson_plan_id = teacher_lesson_plan_id;
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

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getStory_mode_order_index() {
        return story_mode_order_index;
    }

    public void setStory_mode_order_index(String story_mode_order_index) {
        this.story_mode_order_index = story_mode_order_index;
    }

    public String getStory_mode_order_index_hard() {
        return story_mode_order_index_hard;
    }

    public void setStory_mode_order_index_hard(String story_mode_order_index_hard) {
        this.story_mode_order_index_hard = story_mode_order_index_hard;
    }

    public String getWord_mode_order_index() {
        return word_mode_order_index;
    }

    public void setWord_mode_order_index(String word_mode_order_index) {
        this.word_mode_order_index = word_mode_order_index;
    }

    public String getWord_mode_order_index_hard() {
        return word_mode_order_index_hard;
    }

    public void setWord_mode_order_index_hard(String word_mode_order_index_hard) {
        this.word_mode_order_index_hard = word_mode_order_index_hard;
    }

    public TeacherLessonPlanClass(String teacher_lesson_plan_id, String date_time_created, String date_time_updated, String last_updated_by, String status, String teacher_id, String story_mode_order_index, String story_mode_order_index_hard, String word_mode_order_index, String word_mode_order_index_hard) {
        this.teacher_lesson_plan_id = teacher_lesson_plan_id;
        this.date_time_created = date_time_created;
        this.date_time_updated = date_time_updated;
        this.last_updated_by = last_updated_by;
        this.status = status;
        this.teacher_id = teacher_id;
        this.story_mode_order_index = story_mode_order_index;
        this.story_mode_order_index_hard = story_mode_order_index_hard;
        this.word_mode_order_index = word_mode_order_index;
        this.word_mode_order_index_hard = word_mode_order_index_hard;
    }
}
