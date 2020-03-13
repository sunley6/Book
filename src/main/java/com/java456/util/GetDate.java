package com.java456.util;

import java.util.Calendar;
import java.util.Date;

public class GetDate {
    public Date getlastdate(int n){
        Date d = new Date();
        Calendar ca = Calendar.getInstance();
        ca.setTime(d);
        ca.add(Calendar.DATE, n);// num为增加的天数，可以改变的
        d = ca.getTime();
        return d;
    }

}
