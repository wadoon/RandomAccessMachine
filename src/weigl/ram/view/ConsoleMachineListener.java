package weigl.ram.view;

import java.io.PrintStream;
import java.io.PrintWriter;

import weigl.ram.RAMachine;
import weigl.ram.listeners.MachineListener;

/**
 * {@link ConsoleMachineListener} listen to events of a {@link RAMachine} and
 * prints information to an {@link PrintWriter} like {@link System#out}.
 * 
 * 
 * @author Alexander Weigl <alexweigl@gmail.com>
 * @date 10.11.2009
 * @version 1
 * 
 */
public class ConsoleMachineListener implements MachineListener {
	private PrintWriter out;

	public ConsoleMachineListener(PrintWriter out) {
		this.out = out;
	}

	public ConsoleMachineListener(PrintStream out) {
		this(new PrintWriter(out));
	}

	public PrintWriter getOut() {
		return out;
	}

	public void setOut(PrintWriter out) {
		this.out = out;
	}

	@Override
	public void machineStart(RAMachine machine) {
		out
				.println("Machine start ============================================ ");
		out.flush();
	}

	@Override
	public void machineStop(RAMachine machine) {
		out
				.println("========================================= Machine stop");

		out.flush();
	}

	@Override
	public void nextInstruction(RAMachine machine) {
		out.print(machine.getCommands()[machine.getProgramCounter()] + " > ");
		out.flush();
	}

	@Override
	public void valueRead(RAMachine machine, int position) {
		out.print(" readed(" + position + "),");
		out.flush();

	}

	@Override
	public void valueSet(RAMachine machine, int position) {
		out.print(" set(" + position + "),");
		out.flush();

	}

	@Override
	public void instructionEnd(RAMachine machine) {
		out.println();
		out.flush();
	}

}
