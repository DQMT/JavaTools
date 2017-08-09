package effectivejava.effective;

import java.util.Arrays;

public class Base {
	public static final int [] VALUES = {1,2,3};

	public static int[] getValues() {
		return VALUES;
	}

	@Override
	public String toString() {
		return Arrays.toString(getValues());
	}

	
}
