/*
*************************************
*      Author:Yogesh Sharma         *
*************************************
******************************************************************
* this is model class for storing datas of Queries  in firebase  *
******************************************************************

*/
package com.education.counselor.trainer.student.query;
public class QueryListEntryVo {
    private String name, phone;
    QueryListEntryVo() {
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
}
