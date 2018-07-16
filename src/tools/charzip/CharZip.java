package tools.charzip;

import org.apache.commons.lang3.ArrayUtils;
import tools.coolbyte.CoolBytes;

import java.io.*;

/**
 * charZip :  file            ---> hex string text
 * deCharZip: hex string text ---> file
 */
public class CharZip {
    public static void main(String[] args) {
        /*if (args.length < 3) {
            System.out.println("require params: charZip/deCharZip source target");
            return;
        }*/
        args = ArrayUtils.toArray("deCharZip","C:\\Users\\Administrator\\Desktop\\1.txt","C:\\Users\\Administrator\\Desktop\\2.7z");
        String source = args[1];
        String target = args[2];
        try {
            if ("charZip".equals(args[0])) {
                charZip(source, target);
            } else if ("deCharZip".equals(args[0])) {
                deCharZip(source, target);
            } else {
                System.out.println("require params: charZip/deCharZip source target");
            }
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
                if (hex.length() == 1) {
                    hex = "0" + hex;
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
                sb.append((char) temp);
                System.out.println((char) temp);
            }
            byte[] bytes = CoolBytes.hexStringToBytes(sb.toString());
            dataOutputStream.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
