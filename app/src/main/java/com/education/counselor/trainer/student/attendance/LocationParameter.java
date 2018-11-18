/*
*************************************
*      Author:Yogesh Sharma         *
*************************************
*/
//ADDING LOCACTION PARAMETERS
package com.education.counselor.trainer.student.attendance;
//LocationParameter class
public class LocationParameter {
    private double latitude, longitude;
    LocationParameter(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    //getter method of latitude for retriving values
    public double getLatitude() {
        return latitude;
    }
    //setter method of latitude for seting values
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
        //getter method of longitude for retriving values
    public double getLongitude() {
        return longitude;
    }
        //getter method of longitude for retriving values
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
