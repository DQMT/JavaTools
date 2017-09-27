package tools.nio;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Test {

	public static void main(String[] args)  {
		//method2();
		File file = new File("src/nio.txt");
		BufferedReader fis;
		try {
			fis = new BufferedReader(new FileReader(file));
			System.out.println(fis.readLine());
			System.out.println(fis.readLine());
			System.out.println(fis.readLine());
			System.out.println(fis.readLine());
			System.out.println(fis.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void method2() {
		RandomAccessFile aFile = null;
		try {
			aFile = new RandomAccessFile("src/nio.txt", "rw");
			FileChannel fileChannel = aFile.getChannel();
			ByteBuffer buf = ByteBuffer.allocate(1);

			int bytesRead = fileChannel.read(buf);
			System.out.println("bytesRead = " + bytesRead);

			while (bytesRead != -1) {
				buf.flip();
				while (buf.hasRemaining()) {
					System.out.print((char) buf.get());
				}

				buf.compact();
				System.out.println("\nbuffer read!");
				bytesRead = fileChannel.read(buf);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (aFile != null) {
					aFile.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void method1() {
		RandomAccessFile aFile = null;
		try {
			aFile = new RandomAccessFile("src/nio.txt", "rw");
			FileChannel fileChannel = aFile.getChannel();
			ByteBuffer buf = ByteBuffer.allocate(8);

			int bytesRead = fileChannel.read(buf);
			System.out.println("bytesRead = " + bytesRead);

			while (bytesRead != -1) {
				buf.flip();
				while (buf.hasRemaining()) {
					System.out.print((char) buf.get());
				}

				buf.compact();
				System.out.println("\nbuffer read!");
				bytesRead = fileChannel.read(buf);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (aFile != null) {
					aFile.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
