package tools.coolbyte;

public class CoolBytes {
    private byte[] hb;
    private int cap;
    private int offset;
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

    public CoolBytes() {
        this.cap = DEFAULT_INITIAL_CAPACITY;
        hb = new byte[DEFAULT_INITIAL_CAPACITY];
        offset = 0;
    }

    public CoolBytes(int cap) {
        hb = new byte[cap];
        this.cap = cap;
        offset = 0;
    }

    public void add(byte b) {
        if (offset == cap) {
            hb = resize();
        }
        hb[offset] = b;
        offset++;
    }

    public String toString() {
        return new String(toArray());
    }

    public byte[] toArray() {
        byte[] newHb = new byte[offset];
        for (int i = 0; i < offset; i++) {
            newHb[i] = hb[i];
        }
        return newHb;
    }

    public int length() {
        return offset;
    }

    final byte[] resize() {
        byte[] oldHb = hb;
        byte[] newHb = new byte[cap << 1];
        for (int i = 0; i < oldHb.length; i++) {
            newHb[i] = oldHb[i];
        }
        cap = cap << 1;
        return newHb;
    }

}
