package tools.file.textfile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TextFileBuilder {
	
	
	public static void create(String path){
		File dir = new File(path);
		if(dir.exists()){
			dir.delete();
		}
		
	}
	
	public static class Builder{
		private final String path;
		public Builder(String path){
			this.path=path;
		}
		
		
	}
	
	public static void writerLine(String path, String contents) {  
        try {  
            FileWriter fileWriter = new FileWriter(path, true);  
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);  
            bufferedWriter.write(contents);  
            bufferedWriter.newLine();  
            bufferedWriter.flush();  
            bufferedWriter.close();  
            fileWriter.close();  
        } catch (IOException ioe) {  
        }  
    }  

}
