package com.example.caballero.cse455_fall17;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

public class TextManager extends AppCompatActivity {

    //constructor
    public TextManager() {
    }

    //returns true if string has a given word
    public static boolean hasString(String str, String substr){
        int pos = str.toLowerCase().indexOf(substr.toLowerCase());
        if (pos == -1)
            return false;
        return true;
    }

    //returns a line with all text found
    @NonNull
    public static String stringList(String str, String substr){
        final StringBuilder stringBuilder = new StringBuilder();
        String tolowerString = str.toLowerCase();
        int fPos = tolowerString.indexOf(substr.toLowerCase());
        int lPos = tolowerString.indexOf(".", fPos);
        int n = str.length();

        while (--n > 0 && fPos != -1) {
            stringBuilder.append(str.substring(fPos, lPos));
            stringBuilder.append("\n");
            fPos = tolowerString.indexOf(substr.toLowerCase(), fPos + 1);
            lPos = tolowerString.indexOf(".", fPos + 1);

            /*//Log.i("tttttt",Integer.toString(fPos) + " " + Integer.toString(lPos));
            while(!tolowerString.substring(lPos + 1, lPos + 2).equals(" ") &&
                    !tolowerString.substring(lPos + 1, lPos + 2).equals("\n")){
                Log.i("tttttt",tolowerString.substring(lPos + 1, lPos + 2));
                lPos = tolowerString.indexOf(".", lPos + 1) + 2;
            }*/
        }
        return stringBuilder.toString();
    }

}
