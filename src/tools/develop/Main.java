package tools.develop;

public class Main {
	private static int i =1;
	public Main(){
		System.out.println("main constructor"+i++);
	}
	public static Main main = new Main();

	public static void main(String[] args) {
		String p1 = "G:\\dev\\GitProjects\\longan\\src\\test\\java\\MybatisTest.java";
		String p2 = "D:\\financeleasing\\tac-service";
		String p3 = "C:\\Test.java";
		CodeHelper codeHelper = new CodeHelper(p2,".*\\.java$");
		codeHelper.countCode();


	}
}
