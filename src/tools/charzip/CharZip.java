package tools.charzip;

import java.io.*;

public class CharZip {
    public static void main(String[] args) {
        String source = "C:\\Users\\Administrator\\git\\springBootDemo.7z";
        String target = "C:\\Users\\Administrator\\git\\charzip.txt";
        try {
//            charZip(source,target);
            saveB(source, target);
            //readB(target, source);
            //deCharZip(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void charZip(String source, String target) throws IOException {
        File sourceFile = new File(source);
        File targetFile = new File(target);
        FileOutputStream out = new FileOutputStream(targetFile);
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(sourceFile);
        oos.flush();
        oos.close();
        out.close();
    }

    public static void deCharZip(String source) throws IOException, ClassNotFoundException {
        File sourceFile = new File(source);
        FileInputStream fis = new FileInputStream(sourceFile);
        ObjectInputStream ois = new ObjectInputStream(fis);
        File file = (File) ois.readObject();
        file.createNewFile();
        ois.close();
        fis.close();
    }


    public static void saveB(String source, String target) {
        DataInputStream dataInputStream = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(target);
            dataInputStream = new DataInputStream(new FileInputStream(source));
            int temp;
            while ((temp = dataInputStream.read()) != -1) {
                String  s = Integer.toBinaryString(temp);
                fileOutputStream.write(s.getBytes());
                System.out.println(s);
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

    public static void readB(String source, String target) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(target));
            FileInputStream fileInputStream = new FileInputStream(source);
            int temp;
            StringBuilder sb = new StringBuilder();
            while ((temp = fileInputStream.read()) != -1) {

                sb.append(temp);
                dataOutputStream.write(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ByteArrayOutputStream readInputStream(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int length = 0;
            byte[] buffer = new byte[1024];
            while ((length = is.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            is.close();
            baos.close();
            return baos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
