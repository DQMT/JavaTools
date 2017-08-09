package tools.word;

import java.io.IOException;

import tools.file.textfile.TextFile;

public class WordToPdf {
	
	/**
	 * word转pdf:tmp.doc->tmp.pdf
	 * 需要在office 2000/2013环境下
	 */
	public static void doWithVBS(){
		TextFile vbs = new TextFile.Builder("D:\\\\word2p1df\\\\123.vbs")
		.addLine("path = createobject(\"Scripting.FileSystemObject\").GetFile(Wscript.ScriptFullName).ParentFolder.Path")
		.addLine("set fs = CreateObject(\"Scripting.FileSystemObject\")")
		.addLine("Set word = CreateObject(\"Word.application\")")
		.addLine("Set docx = word.Documents.Open(path & \"\\\" & \"tmp.doc\")")
		.addLine("docx.SaveAs path & \"\\\"  & \"tmp.pdf\",17")
		.addLine("docx.close(doNotSaveChanges)")
		.addLine("word.Quit")
		.addLine("set docx = nothing")
		.addLine("set word = nothing")
		.addLine("set fs = nothing")
		.create();
		try {
			String vbsFileName = vbs.newFile();
			final String cpCmd = "cmd /c start " + vbsFileName;
			Thread t = new Thread() {
				@Override
				public void run() {
					try {
						Process process = Runtime.getRuntime().exec(cpCmd);
						try {
							int val = process.waitFor();
							System.out.println("val = "+val);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
			t.start();
			t.join();
			try {
				// 等待pdf文件生成
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		doWithVBS();
	}
}
