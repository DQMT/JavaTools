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

    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

    private int bufferSize = DEFAULT_INITIAL_CAPACITY;
    private FileChannel fileChannel;
    private ByteBuffer buffer;
    private FileInputStream fis;
    private boolean empty = false;
    private boolean lastLine = false;

    public NIOReader(File file) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("file is null!");
        }
        fis = new FileInputStream(file);
        fileChannel = fis.getChannel();
        buffer = ByteBuffer.allocate(bufferSize);
    }

    public NIOReader(File file, int bufferSize) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("file is null!");
        }
        if (bufferSize <= 0) {
            throw new IllegalArgumentException("bufferSize must be positive integer!");
        }
        fis = new FileInputStream(file);
        fileChannel = fis.getChannel();
        this.bufferSize = bufferSize;
        buffer = ByteBuffer.allocate(bufferSize);
    }

    private void ensure() {
        if (fis == null || fileChannel == null || buffer == null) {
            throw new IllegalArgumentException(
                    "Attempt to use an uninitialized Reader!");
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
                    if (!buffer.hasRemaining()) {
                        empty = true;
                    }
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
            empty = true;
            return bytes.toString();
        }
        return readLastBufferLine();
    }

    public boolean isEmpty() {
        return empty;
    }

    public void test(int n) {
        File file = new File("src/nio.txt");
        try {
            System.out.println("test " + n);
            NIOReader nioReader = new NIOReader(file, n);
            for (int i = 0; i < 5; i++) {
                System.out.println(nioReader.readLine());
            }
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
