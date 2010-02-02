package weigl.ram;

import java.util.LinkedList;
import java.util.List;

import weigl.ram.commands.Command;
import weigl.ram.listeners.MachineListener;

public class RAMachine {

	private List<MachineListener> listener = new LinkedList<MachineListener>();
	private Registers reg;
	private Command[] commands;
	private int curline = 0;

	public RAMachine(Command[] c) {
		this(c, 25);
	}

	public RAMachine(Command[] c, int memsz) {
		commands = c;
		reg = new Registers(memsz);
	}

	public void addListener(MachineListener ml) {
		listener.add(ml);
	}

	public void start() {
		fireStart();
		while (curline < commands.length) {
			fireNextInstruction();
			commands[curline].exec(this);
			fireInstructionEnd();
		}
		fireStop();
	}

	public Registers getRegisters() {
		return reg;
	}

	public void fireNextInstruction() {
		for (MachineListener machineListener : listener) {
			machineListener.nextInstruction(this);
		}
	}

	protected void fireInstructionEnd() {
		for (MachineListener machineListener : listener) {
			machineListener.instructionEnd(this);
		}
	}

	public void fireStart() {
		for (MachineListener machineListener : listener) {
			machineListener.machineStart(this);
		}
	}

	public void fireStop() {
		for (MachineListener machineListener : listener) {
			machineListener.machineStop(this);
		}
	}

	private void fireValueSet(int p) {
		for (MachineListener machineListener : listener) {
			machineListener.valueSet(this, p);
		}
	}

	private void fireValueGet(int p) {
		for (MachineListener machineListener : listener) {
			machineListener.valueRead(this, p);
		}
	}

	public int rget(int index) {
		fireValueGet(index);
		return reg.get(index);
	}

	public int rget(String index) {
		char c = index.charAt(0);
		int pos = 0;
		switch (c) {
		case '#':
			return Integer.parseInt(index.substring(1));
		case '*':
			pos = rget(Integer.parseInt(index.substring(1)));
			break;
		case '+':// relative jump
			return Integer.parseInt(index.substring(1)) + getProgramCounter();
		case '-':// relative jump
			return getProgramCounter() - Integer.parseInt(index.substring(1));
		default:
			pos = Integer.parseInt(index);
		}

		if (pos > 0) {
			return rget(pos);
		} else {
			throw new RuntimeException("not allow to access that register");
		}
	}

	public void rset(int index, int value) {
		// System.out.format("c(%d);=%d\n", index, value);
		reg.set(index, value);
		fireValueSet(index);
	}

	public void rset(String index, int value) {
		char c = index.charAt(0);
		int pos = 0;
		switch (c) {
		case '*':
			pos = rget(Integer.parseInt(index.substring(1)));
		default:
			pos = Integer.parseInt(index);
		}
		if (pos > 0) {
			rset(pos, value);
		} else {
			throw new RuntimeException("not allow to access that register");
		}
	}

	public void nextInstruction() {
		curline++;
		// System.out.format("b:=%d\n", curline);
	}

	public void setInstruction(int i) {
		curline = i;
		// System.out.format("b:=%d\n", curline);
	}

	public void end() {
		curline = Integer.MAX_VALUE;
		// System.out.format("END\n", curline);
	}

	public Command[] getCommands() {
		return commands;
	}

	public int getProgramCounter() {
		return curline;
	}
}
