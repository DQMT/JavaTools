package tools.develop;

import tools.nio.NIOReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class CodeHelper {

	private String rootPath;
	private String regex;
	public CodeHelper(String rootPath,String regex){
		this.rootPath=rootPath;
		this.regex=regex;
	}
	public void countCode(){
		File file = new File(rootPath);
		doCount(file);
	}
	
	private void doCount(File file){
		System.out.println("doCount..."+file.getAbsolutePath());
		if(file.isFile() && file.getName().matches(regex)){
			System.out.println("doCount... java source file..."+file.getAbsolutePath());
			//java source code
			try {
				NIOReader nioReader = new NIOReader(file);
				while (!nioReader.isEmpty()){
					System.out.println(nioReader.readLine());
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
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
