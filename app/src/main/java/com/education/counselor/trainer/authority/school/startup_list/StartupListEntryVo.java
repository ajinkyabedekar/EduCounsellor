package com.education.counselor.trainer.authority.school.startup_list;
/*
  
******************************************************************
* this is model class for setting and retriving datas from firebase *
******************************************************************
*************************************
*      Biren Sharma         *
*************************************
*/
public class StartupListEntryVo {
    private String name;
    StartupListEntryVo() {
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
