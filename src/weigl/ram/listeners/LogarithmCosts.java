package weigl.ram.listeners;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;

import weigl.ram.RAMachine;
import weigl.ram.commands.Command;

/**
 * Listener calculates the logarithm costs from a machine program.
 * 
 * 
 * @author Alexander Weigl <alexweigl@gmail.com>
 * @date 10.11.2009
 * @version 1
 * 
 */
public class LogarithmCosts implements MachineListener {
	private HashMap<Integer, Double> usedSpace;
	private int timeCosts;

	private PrintWriter out;
	
	public LogarithmCosts() {
		this(System.out);
	}

	public LogarithmCosts(PrintWriter out) {
		this.out = out;
	}

	public LogarithmCosts(PrintStream out) {
		this(new PrintWriter(out));
	}

	public static int log2(int n) {
		if (n == 0)
			return 1;
		return (int) (Math.log(n) / Math.log(2)) + 1;
	}

	@Override
	public void machineStart(RAMachine machine) {
		usedSpace = new HashMap<Integer, Double>();
		timeCosts = 0;
	}

	@Override
	public void instructionEnd(RAMachine machine) {

	}

	@Override
	public void machineStop(RAMachine machine) {
		out.println("Logarithmische Zeitkosten:  " + getTimeCosts());
		out.println("Logarithmische Platzkosten: " + getSpaceCosts());
	}

	public double getSpaceCosts() {
		double sum = 0;
		for (Double d : usedSpace.values())
			sum += d;
		return sum;
	}

	public double getTimeCosts() {
		return timeCosts;
	}

	@Override
	public void nextInstruction(RAMachine machine) {
		Command c = machine.getCommands()[machine.getProgramCounter()];
		switch (c.getType()) {
		case GOTO:
		case JZERO:
		case END:
			timeCosts++;
		}
	}

	@Override
	public void valueRead(RAMachine machine, int position) {
		setSpace(position, machine.getRegisters().get(position));

		timeCosts += log2(position);
		timeCosts += log2(machine.getRegisters().get(position));
	}

	private void setSpace(int position, int value) {
		double old = 0;
		if (usedSpace.containsKey(position))
			old = usedSpace.get(position);

		double dvalue = log2(value);
		if (dvalue > old)
			usedSpace.put(position, dvalue);
	}

	@Override
	public void valueSet(RAMachine machine, int position) {
		setSpace(position, machine.getRegisters().get(position));

		timeCosts += log2(position);
		timeCosts += log2(machine.getRegisters().get(position));
		timeCosts += log2(machine.getRegisters().get(0));
	}
}
