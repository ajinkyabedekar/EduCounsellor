package com.education.counselor.trainer.authority.school.rating;
/*
  
******************************************************************
* this is model class for setting and retriving datas from firebase *
******************************************************************
*************************************
*      Biren Sharma         *
*************************************
*/
public class RatingListEntryVo {
    private String name;
    private float rating;
    RatingListEntryVo() {
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
