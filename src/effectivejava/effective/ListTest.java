package effectivejava.effective;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListTest {
	
	public static void main(String[] args) {
		List a = new ArrayList();
		for(int i=0;i<10;i++){
			a.add(i);
		}
		System.out.println(a);
		change(a);
		System.out.println(a);
		
		
	}
	
	public static void change(List list){
		
		for(Iterator iterator = list.iterator();iterator.hasNext();){
			int t = (Integer) iterator.next();
			if(t%3==0){
				iterator.remove();
			}
		}
		System.out.println(list);
	}

}
