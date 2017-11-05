package tools.charzip;

import tools.coolbyte.CoolBytes;

import java.io.*;

/**
 * charZip :  file            ---> hex string text
 * deCharZip: hex string text ---> file
 */
public class CharZip {
    public static void main(String[] args) {
        String source = "C:\\Users\\Administrator\\git\\springBootDemo.7z";
        String target = "C:\\Users\\Administrator\\git\\charzip.txt";
        try {
//            charZip(source, target);
            deCharZip(target, source);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void charZip(String source, String target) {
        DataInputStream dataInputStream = null;
        try {

            FileOutputStream fileOutputStream = new FileOutputStream(target);
            dataInputStream = new DataInputStream(new FileInputStream(source));
            int temp;
            while ((temp = dataInputStream.read()) != -1) {
                String hex = Integer.toHexString(temp).toUpperCase();
                if(hex.length()==1){
                    hex = "0"+hex;
                }
                fileOutputStream.write(hex.getBytes());
                System.out.println(hex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dataInputStream != null) {
                try {
                    dataInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void deCharZip(String source, String target) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(target));
            FileReader fileReader = new FileReader(source);
            int temp;
            StringBuilder sb = new StringBuilder();
            while ((temp = fileReader.read()) != -1) {
                sb.append((char)temp);
                System.out.println((char)temp);
            }
            byte[] bytes = CoolBytes.hexStringToBytes(sb.toString());
            dataOutputStream.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
