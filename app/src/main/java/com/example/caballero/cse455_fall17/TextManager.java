package com.example.caballero.cse455_fall17;


import android.support.v7.app.AppCompatActivity;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TextManager extends AppCompatActivity {

    //constructor
    public TextManager() {
    }

    //create list of text
    public static List<String> populateList(List<String> list){
        if(!list.isEmpty())
            list.clear();
        list.add("Exam");
        list.add("Email");
        list.add("midterm");
        list.add("due");
        return list;
    }

    public static List<String> importantList(String str, List<String> list){
        List<String> returnList = new ArrayList<>();
        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
        iterator.setText(str);
        int start = iterator.first();
        for(int end = iterator.next();
            end != BreakIterator.DONE;
            start = end, end = iterator.next()){
            for(int j = 0; j < list.size(); j++ ){
                String find = list.get(j);
                if(hasString(str.substring(start,end),find)){
                    returnList.add(str.substring(start,end));
                }
            }
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


}
