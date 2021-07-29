package com.bat.velo.util;

import org.apache.commons.lang3.RandomStringUtils;


import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class Util {
    
    public static boolean isValidEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    
    public static String generateReferal() {
        
        return RandomStringUtils.randomAlphanumeric(6);
    }
    
    public static String generateNumber(){
        
        return RandomStringUtils.randomNumeric(6);
    }

    public static Boolean calculateAge(Date birthdate) {
        
        Calendar birth = Calendar.getInstance();
        birth.setTime(birthdate);
        Calendar today = Calendar.getInstance();
        int yearDifference = today.get(Calendar.YEAR)- birth.get(Calendar.YEAR);
        if (today.get(Calendar.MONTH) < birth.get(Calendar.MONTH)) {
             yearDifference--;
            } else {
                if (today.get(Calendar.MONTH) == birth.get(Calendar.MONTH)&& today.get(Calendar.DAY_OF_MONTH) < birth.get(Calendar.DAY_OF_MONTH)) {
                    yearDifference--;
                }
            }

        return yearDifference<=18?true:false;
     }
}