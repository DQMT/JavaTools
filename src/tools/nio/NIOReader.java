package tools.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class NIOReader {

	private static Charset cn = Charset.forName("GBK"); 
	private static CharsetDecoder decoder = cn.newDecoder();
	private int bufferSize = 16;
	private FileChannel fileChannel;
	private ByteBuffer buffer;
	private CharBuffer cb;
	private FileInputStream fis;
	private boolean empty = false;
	private boolean lastLine = false;

	NIOReader(File file) throws IOException {
		fis = new FileInputStream(file);
		fileChannel = fis.getChannel();
		buffer = ByteBuffer.allocate(bufferSize);
		cb = CharBuffer.allocate(bufferSize);
	}

	NIOReader(File file, int bufferSize) throws IOException {
		fis = new FileInputStream(file);
		fileChannel = fis.getChannel();
		this.bufferSize = bufferSize;
		buffer = ByteBuffer.allocate(bufferSize);
		cb = CharBuffer.allocate(bufferSize);
	}

	private void ensure() {
		if (fis == null || fileChannel == null || buffer == null) {
			throw new IllegalArgumentException(
					"Attempt to use an uninitialized Reader");
		}
	}

	private boolean isCRLF(char c) {
		if (c == '\n') {
			return true;
		} else {
			return false;
		}
	}

	private String ignoreCRLF(String s) throws UnsupportedEncodingException {
		return s.replace("\r\n", "");
	}

	private String readLastBufferLine() throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		if (buffer.hasRemaining()) {
			while (buffer.hasRemaining()) {
				char c = (char) buffer.get();
				sb.append(c);
				if (isCRLF(c)) {
					buffer.compact();
					buffer.flip();
					return ignoreCRLF(sb.toString());
				}
			}
			empty = true;
			return sb.toString();
		} else {
			empty = true;
			return null;
		}
	}

	public String readLine() throws IOException {
		ensure();
		if (empty) {
			return null;
		}
		if (lastLine) {
			return readLastBufferLine();
		}
		StringBuilder sb = new StringBuilder();
		int bytesRead = fileChannel.read(buffer);
		while (bytesRead != -1) {
			//cn.decode(buffer);
			buffer.flip();
			//decoder.decode(buffer,cb,false);
			//cb.flip();
			while (buffer.hasRemaining()) {
				char c = (char) buffer.get();
				sb.append(c);
				if (isCRLF(c)) {
					buffer.compact();
					return ignoreCRLF(sb.toString());
				}
			}
			buffer.compact();
			bytesRead = fileChannel.read(buffer);
		}
		buffer.flip();
		lastLine = true;
		if (sb.length() > 0) {
			return sb.toString();
		}
		return readLastBufferLine();
	}

	public void test(int i) {
		File file = new File("src/nio.txt");
		try {
			System.out.println("test " + i);
			NIOReader nioReader = new NIOReader(file, i);
			System.out.println(nioReader.readLine());
			System.out.println(nioReader.readLine());
			System.out.println(nioReader.readLine());
			System.out.println(nioReader.readLine());
			System.out.println(nioReader.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void print() throws IOException {
		String s;
		while ((s = readLine()) != null || (!empty)) {
			System.out.println(s);
		}
	}

	public static void main(String[] args) throws IOException {
		File file = new File("src/nio.txt");
		NIOReader r = new NIOReader(file);
		r.print();
	}
}
