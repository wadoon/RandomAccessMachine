package weigl.ram;

import java.util.Arrays;

public class Registers {

	private int registers[];

	public Registers(int size) {
		registers = new int[size];
	}

	public int get(int index) {
		return registers[index];
	}

	public void set(int index, int value) {
		registers[index] = value;
	}

	public int size() {
		return registers.length;
	}

	public int[] getArray() {
		return Arrays.copyOf(registers, registers.length);
//		return registers;
	}
}
