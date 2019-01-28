package engine.util;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileUtils {

    public static String loadFileToString(String file){
        StringBuilder sb = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String buff;
            while ((buff = br.readLine()) != null){
                sb.append(buff).append("\n");
            }
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }
}