package com.education.counselor.trainer.student;

public class courses {
    private String course_name, course_id;

    public courses(String course_name, String course_id) {
        this.course_name = course_name;
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }
}
