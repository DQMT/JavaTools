package tools.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOReader {

	private int bufferSize = 10;
	private FileChannel fileChannel;
	private ByteBuffer buffer;
	private FileInputStream fis;

	NIOReader(File file) throws IOException {
		fis = new FileInputStream(file);
		fileChannel = fis.getChannel();
		buffer = ByteBuffer.allocate(bufferSize);
	}

	private void ensure() {
		if (fis == null || fileChannel == null || buffer == null) {
			throw new IllegalArgumentException(
					"attempt to use an uninitialized Reader");
		}
	}

	private boolean crlf(char c) {
		if (c == '\n')
			return true;
		else
			return false;
	}

	public String readLine() throws IOException {
		ensure();
		StringBuilder sb = new StringBuilder();
//		while (buffer.position()!=0) {
//			System.out.println("buffer not empty! position="+buffer.position()+"limit="+buffer.limit());
//			char c = (char) buffer.get();
//			sb.append(c);
//			if (crlf(c)) {
//				buffer.compact();
//				return sb.toString();
//			}
//		}
		int bytesRead = fileChannel.read(buffer);
		System.out.println("bytesRead="+bytesRead);
		while (bytesRead != -1) {
			buffer.flip();
			while (buffer.hasRemaining()) {
				char c = (char) buffer.get();
				sb.append(c);
				if (crlf(c)) {
					buffer.compact();
					return sb.toString();
				}
			}
			buffer.compact();
			bytesRead = fileChannel.read(buffer);
			System.out.println("bytesRead="+bytesRead);
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		File file = new File("src/nio.txt");
		try {
			NIOReader nioReader = new NIOReader(file);
			System.out.print(nioReader.readLine());
			System.out.print(nioReader.readLine());
			System.out.print(nioReader.readLine());
			System.out.print(nioReader.readLine());
			System.out.print(nioReader.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
