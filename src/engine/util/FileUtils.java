package engine.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FileUtils {

    public static String loadFileToString(String file){
        StringBuilder sb = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(Class.class.getResourceAsStream("/" + file)));
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

    public static BufferedReader loadBufferedReader(String path) {
        return new BufferedReader(new InputStreamReader(Class.class.getResourceAsStream("/" + path)));
    }
}