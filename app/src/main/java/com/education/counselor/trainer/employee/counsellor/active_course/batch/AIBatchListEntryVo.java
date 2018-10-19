package com.education.counselor.trainer.employee.counsellor.active_course.batch;

public class AIBatchListEntryVo {
    private String name, course;


    private String phone;

    AIBatchListEntryVo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}