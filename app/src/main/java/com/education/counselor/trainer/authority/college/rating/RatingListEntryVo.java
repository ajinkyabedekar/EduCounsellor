package com.education.counselor.trainer.authority.college.rating;
/*-------------------------------------------------------------------------------------------
 |     Its is a model class for writing data in this module                                   |
 |---------------------------------------------------------------------------------------------
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
