package engine.util;

public class StringUtil {

    public static String capStringNumber(String number, int length){
        while (number.length() - 2 < length){
            number += "0";
        }
        return number;
    }
}