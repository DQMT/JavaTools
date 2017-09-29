package tools.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class NioDemo {
	public static void main(String[] args) {
		try {
			Charset charset = Charset.forName("GBK");  
	        CharsetDecoder decoder = charset.newDecoder();
			
			RandomAccessFile raf = new RandomAccessFile("src/nio.txt", "rw");
			FileChannel fc = raf.getChannel();
			
			ByteBuffer buffer = ByteBuffer.allocate(5); 
			CharBuffer cb = CharBuffer.allocate(5
					);
			
			int count = fc.read(buffer);
			while (count != -1) { 
				System.out.println("count = "+count); 
				 buffer.flip();
				 decoder.decode(buffer, cb, false);
				 cb.flip();
				 while (cb.hasRemaining()) {
						System.out.print(cb.get());
					} 
				 System.out.println();
				buffer.clear();
				cb.clear();
				count = fc.read(buffer);
			}
			raf.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}