package com.sfs.crfosp.transport.avizout

import java.text.SimpleDateFormat

import javax.xml.bind.annotation.adapters.XmlAdapter

class DateAdapter extends XmlAdapter<String, Date> {

    // the desired format
    private String pattern = "yyyy-MM-dd";

    public String marshal(Date date) throws Exception {
        return new SimpleDateFormat(pattern).format(date);
    }

    public Date unmarshal(String dateString) throws Exception {
        return new SimpleDateFormat(pattern).parse(dateString);
    }   
}