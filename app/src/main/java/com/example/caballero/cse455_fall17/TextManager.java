package com.example.caballero.cse455_fall17;


import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.*;
import android.util.Log;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TextManager extends AppCompatActivity {

    private static String name = "";
    private static String email = "";
    private static String phone = "";

    //constructor
    public TextManager() {
    }

    //create list of text
    public static List<String> populateList(List<String> list, String type){
        if(!list.isEmpty())
            list.clear();
        switch (type){
            case "CONTACT": list.add("Email");
                list.add("dr");
                list.add("phone");
                break;
            default:        list.add("Exam");
                list.add("dr");
                list.add("phone");
                list.add("Email");
                list.add("midterm");
                list.add("due");
                list.add("/");
                break;
        }
        return list;
    }

    //returns list of important stuff
    public static List<String> importantList(String str, List<String> list) {
        List<String> returnList = new ArrayList<>();

        List<String> contactList = new ArrayList<>();
        populateList(contactList, "CONTACT");
        String contactString = "";
        String doctor = "";

        boolean next = false;
        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
        iterator.setText(str);
        int start = iterator.first();
        for (int end = iterator.next();
             end != BreakIterator.DONE;
             start = end, end = iterator.next()) {

            if (!hasString(str.substring(start, end), "dr.") && next) {
                doctor = doctor + str.substring(start, end);
                next = false;
            }
            if (hasString(str.substring(start, end), "dr.")) {
                doctor = doctor + str.substring(start, end);
                next = true;
            }
            for (int j = 0; j < list.size(); j++) {
                if (hasString(str.substring(start, end), list.get(j))) {
                    returnList.add(str.substring(start, end));
                }
            }
        }

        for (int i = returnList.size() - 1; i >= 0; i--) {
            for (int j = contactList.size() - 1; j >= 0; j--) {
                if (hasString(returnList.get(i), contactList.get(j))) {
                    if (!hasString(returnList.get(i), "dr"))
                        contactString = contactString + returnList.get(i);
                    returnList.remove(i);
                }
            }
        }

        if (!contactString.isEmpty()){
            contactString = doctor + contactString;
            returnList.add(contactString);
        }

        return returnList;
    }

    //returns true if string has a given word
    public static boolean hasString(String str, String substr){
        int pos = str.toLowerCase().indexOf(substr.toLowerCase());
        if (pos == -1)
            return false;
        return true;
    }

    //takes in a date in mm/dd/yy format and returns a formatted date in millis
    @NonNull
    public static String dateDetect(String s){
        String date = "";
        int start = s.indexOf("/") - 2;
        int end = s.substring(start).indexOf(" ");
        android.util.Log.v("TXT",s.substring(start, start + end));
        String string = s.substring(start, start + end);
        while (hasString(string, "/")){
            android.util.Log.v("TXT", string.substring(0, string.indexOf("/")));
            date = date + string.substring(0, string.indexOf("/"));
            string = string.substring(string.indexOf("/") + 1);
            android.util.Log.v("TXT", string);
        }
        date = date + "20" + string + "07";
        android.util.Log.v("TXT", date);
        //return s.substring(start, start + end);
        return date;
    }

    public static boolean isDate(String s){
        if(hasString(s, "/")){
            return true;
            /*if(ordinalIndexOf(s, "/", 2) - ordinalIndexOf(s, "/", 1) == 3){
                return true;
            }*/
        }
        return false;
    }

    public static boolean isContact(String s){
        if(hasString(s,"@"))
            return true;
        return false;
    }

    //takes in a formatted contact and returns an intent to the contacts
    public static Intent contactDetect(String s){
        String[] array = s.split("\n");
        /*String name = array[0];
        String phone = "";
        String email = "";*/
        name = array[0];

        for(int i = 0; i < array.length; i++){
            android.util.Log.v("TEST", array[i]);
            if(hasString(array[i], "email")){
                email = array[i].substring(array[i].indexOf(" "));
            }
            if(hasString(array[i], "phone")){
                phone = array[i].substring(array[i].indexOf(" "));
            }
        }
        Log.v("TEST", name+"---"+phone+"---"+email);

        Intent contactIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
        contactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

        contactIntent
                .putExtra(ContactsContract.Intents.Insert.NAME, name)
                .putExtra(ContactsContract.Intents.Insert.EMAIL, email)
                .putExtra(ContactsContract.Intents.Insert.PHONE, phone);

        return contactIntent;
        //startActivityForResult(contactIntent, 1);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
