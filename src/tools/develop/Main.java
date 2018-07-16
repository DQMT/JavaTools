package tools.develop;

public class Main {
	private static int i =1;
	public Main(){
		System.out.println("main constructor"+i++);
	}
	public static Main main = new Main();

	public static void main(String[] args) {
		String p2 = "D:\\git\\iotteleproxymgr";
		CodeHelper codeHelper = new CodeHelper(p2,".*\\.java$");
		codeHelper.countCode();


	}
}
