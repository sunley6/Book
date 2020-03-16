package com.java456.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public Date getcontinuelastdate(Date date,int n){

        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.DATE, n);// num为增加的天数，可以改变的
        date = ca.getTime();
        return date;
    }

    public boolean compDate(String str) throws ParseException {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        sd.format(date);
        return sd.parse(sd.format(date)).before(sd.parse(str));
    }



}
