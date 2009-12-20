package weigl.ram.view;

import weigl.ram.RAMachine;
import weigl.ram.listeners.MachineListener;

/**
 * delay the exection of an program.
 *
 *
 * @author Alexander Weigl <alexweigl@gmail.com>
 * @date   10.11.2009
 * @version 1
 *
 */
public class WaitListener implements MachineListener {
	
	private volatile long delay;
	
	/**
	 * @param delay after each instruction
	 */
	public WaitListener(long delay) {
		this.delay = delay;
	}

	@Override
	public void instructionEnd(RAMachine machine) {

	}

	@Override
	public void machineStart(RAMachine machine) {
	}

	@Override
	public void machineStop(RAMachine machine) {

	}

	@Override
	public void nextInstruction(RAMachine machine) {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void valueRead(RAMachine machine, int position) {

	}

	@Override
	public void valueSet(RAMachine machine, int position) {

	}

}
