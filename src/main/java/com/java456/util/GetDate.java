package com.java456.util;

import com.java456.entity.BookBorrow;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    public int compDate(String str) throws ParseException {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        sd.format(date);
        Date date1=new Date();
        date1=sd.parse(str);
        return (int) ((date.getTime() - date1.getTime()) / (1000*3600*24));
    }
    
    public int overflag(List<BookBorrow> list) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String str=new String();
        Date date0=new Date();
        sdf.format(date0);
        for (BookBorrow b : list) {
           str= sdf.format(b.getBorrowLastDateTime());
           Date date=sdf.parse(str);
           if (b.getState()!=5) {
               if ((int) ((date0.getTime() - date.getTime()) / (1000 * 3600 * 24)) > 0) {
                   return 0;
               }
           }
        }
        
        return 1;
        
    }
    
    public  int differentDaysByMillisecond(Date date1,Date date2)
    {

        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));

        return days;
    }



}
