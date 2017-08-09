package effectivejava.effective;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Test {
	private static Comparator<?> comparator = new Comparator<String>(){
		public int compare(String s1,String s2){
			return s1.length()-s2.length();
		}
	};
	private static String pi = "3.14";
	private String e = "2.5";
	
	public class InnerClass{
		public String e(){
			return e;
		}
	};
	
	public static void main(String[] args) {
		int[] a = {0, 1, 2, 3, 4, 5 };
		int i = 0;
		System.out.println(a[i]);
		System.out.println(a[i++]);
		System.out.println(a[i]);
		Random random = new Random();
		random.equals(a);
		Date date =  new Date(System.currentTimeMillis());
		Timestamp timestamp ;
		System.out.println(date);
		Base.VALUES[1] = 2;
		Base base = new Base();
		System.out.println(base);
		String sa  = "abc";
		String sb = new String(sa);
		System.out.println(sa == sb);
		Map<String, Object> map = new HashMap<String, Object>();
		map.toString();
		Map.class.cast(map);
		Class<Object> clazz = null;
		Map.class.asSubclass(clazz);
	}

	public enum Singleton {
		INSTANCE;
		public void whateverMethod() {
		}
	}
	
}
