package tools.develop;

public class Main {
	private static int i =1;
	public Main(){
		System.out.println("main constructor"+i++);
	}
	public static Main main = new Main();
	
	public static void main(String[] args) {
		Main t = new Main();
		System.out.println("main method"+i++);
		//Main.main(null);
	}
}
