package tools.develop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class CodeHelper {
	static private String rootPath = "C:\\Users\\hangbo.song\\git";
	
	public static void countCode(String path){
		File file = new File(path);
		doCount(file);
	}
	
	private static void doCount(File file){
		if(file.isFile() && file.getName().matches(".*\\.java$")){
			//java source code
			try {
				RandomAccessFile aFile = new RandomAccessFile(file, "r");
				FileChannel inChannel = aFile.getChannel();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if(file.isDirectory()){
			for(File f : file.listFiles()){
				doCount(f);
			}
		}
	}

}
