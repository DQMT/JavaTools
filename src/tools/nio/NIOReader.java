package tools.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.List;

import tools.coolbyte.CoolBytes;

public class NIOReader {

	private static Charset cn = Charset.forName("GBK");
	private static CharsetDecoder decoder = cn.newDecoder();
	private int bufferSize = 16;
	private FileChannel fileChannel;
	private ByteBuffer buffer;
	private List<Byte> byteList;
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
					"Attempt to use an uninitialized Reader!");
		}
		if (bufferSize % 2 != 0) {
			throw new IllegalArgumentException(
					"BufferSize must be divided by 2!");
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
		CoolBytes bytes = new CoolBytes();
		if (buffer.hasRemaining()) {
			while (buffer.hasRemaining()) {
				byte b = buffer.get();
				char c = (char) b;
				bytes.add(b);
				if (isCRLF(c)) {
					buffer.compact();
					buffer.flip();
					return ignoreCRLF(bytes.toString());
				}
			}
			empty = true;
			return bytes.toString();
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
		CoolBytes bytes = new CoolBytes();
		int bytesRead = fileChannel.read(buffer);
		while (bytesRead != -1) {
			buffer.flip();
			while (buffer.hasRemaining()) {
				byte b = buffer.get();
				char c = (char) b;
				bytes.add(b);
				if (isCRLF(c)) {
					buffer.compact();
					return ignoreCRLF(bytes.toString());
				}
			}
			buffer.compact();
			bytesRead = fileChannel.read(buffer);
		}
		buffer.flip();
		lastLine = true;
		if (bytes.length() > 0) {
			return bytes.toString();
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
