package tools.file.textfile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextFile {
	private final String path;
	private final List<String> content;

	public TextFile(Builder builder) {
		path = builder.path;
		content = builder.content;
	}

	public static class Builder {
		private final String path;
		private List<String> content = new ArrayList<String>();

		public Builder(String path) {
			this.path = path;
		}

		public Builder addLine(String s) {
			content.add(s);
			return this;
		}

		public TextFile create() {
			return new TextFile(this);
		}
	}

	public String newFile() throws Exception {
		if (path == null || content == null || content.isEmpty()) {
			return null;
		} else {
			File file = new File(path);
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
			for (String s : content) {
				writerLine(path, s);
			}
			return path;
		}

	}

	private void writerLine(String path, String contents) {
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
