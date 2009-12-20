package weigl.ram.listeners;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;

import weigl.ram.RAMachine;

/**
 * Calculates the uniform costs from a program during it's execution in an
 * {@link RAMachine}.
 * 
 * 
 * @author Alexander Weigl <alexweigl@gmail.com>
 * @date 10.11.2009
 * @version 1
 * 
 */
public class UniformCosts implements MachineListener {
	private Set<Integer> usedSpace;
	private int timeCosts;

	private PrintWriter out;

	public UniformCosts() {
		this(System.out);
	}
	
	public UniformCosts(PrintWriter out) {
		this.out = out;
	}

	public UniformCosts(PrintStream out) {
		this(new PrintWriter(out));
	}

	@Override
	public void machineStart(RAMachine machine) {
		usedSpace = new TreeSet<Integer>();
		timeCosts = 0;
	}

	@Override
	public void instructionEnd(RAMachine machine) {
		timeCosts++;
	}

	@Override
	public void machineStop(RAMachine machine) {
		out.println("uniforme Zeitkosten: " + getTimeCosts());
		out.println("uniforme Platzkosten: " + getSpaceCosts());
	}

	public double getSpaceCosts() {
		return usedSpace.size();
	}

	public double getTimeCosts() {
		return timeCosts;
	}

	@Override
	public void nextInstruction(RAMachine machine) {
		//
	}

	@Override
	public void valueRead(RAMachine machine, int position) {
		usedSpace.add(position);
	}

	@Override
	public void valueSet(RAMachine machine, int position) {
		usedSpace.add(position);
	}
}
