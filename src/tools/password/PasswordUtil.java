package tools.password;

import java.util.Random;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.shiro.crypto.hash.SimpleHash;

public class PasswordUtil {
	private static char[] pswdStr = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
			.toCharArray();

	private static final int pswdLen = 12;

	public static String newPassword() {
		Random rand = new Random();
		StringBuilder pswd = new StringBuilder();
		for (int i = 0; i < pswdLen; i++) {
			int index = rand.nextInt(pswdStr.length);
			pswd.append(pswdStr[index]);
		}

		return pswd.toString();
	}

	public static String encryptPassword(String str,String salt) {
		return new SimpleHash("SHA-1", str, salt).toString();  
	}

	private final static String MD5(String input) {
		try {
			// 鎷垮埌涓�涓狹D5杞崲鍣紙濡傛灉鎯宠SHA1鍙傛暟鎹㈡垚鈥漇HA1鈥濓級
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] inputByteArray = input.getBytes();
			messageDigest.update(inputByteArray);
			byte[] resultByteArray = messageDigest.digest();
			return byteArrayToHex(resultByteArray);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
	
	private final static String byteArrayToHex(byte[] byteArray) {
		  char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };
		  char[] resultCharArray =new char[byteArray.length * 2];
		  int index = 0;
		  for (byte b : byteArray) {
		     resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];
		     resultCharArray[index++] = hexDigits[b& 0xf];
		  }
		  return new String(resultCharArray);
		}

	public static void main(String[] args) {
		String pw = newPassword();
		System.out.println(pw);
		System.out.println(encryptPassword(pw,"sfsdfs"));
	}
}
