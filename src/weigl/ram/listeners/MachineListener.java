package weigl.ram.listeners;

import weigl.ram.RAMachine;

/**
 * Synchrone hook class for the Random Access Machine (RAMachine) class.
 * 
 * @author Alexander Weigl <alexweigl@gmail.com>
 * @date 10.11.2009
 * @version 1
 * 
 */
public interface MachineListener {
	/**
	 * Executes on machine startup
	 * 
	 * @param machine
	 */
	public void machineStart(RAMachine machine);

	/**
	 * Execute after machine has terminated
	 * 
	 * @param machine
	 */
	public void machineStop(RAMachine machine);

	/**
	 * Call before running the next instruction
	 * 
	 * @param machine
	 */
	public void nextInstruction(RAMachine machine);

	/**
	 * Call after an instruction has finished
	 * 
	 * @param machine
	 */
	public void instructionEnd(RAMachine machine);

	/**
	 * called after an register was overwritten by an new value
	 * 
	 * @param machine
	 * @param position
	 */
	public void valueSet(RAMachine machine, int position);

	/**
	 * called after an value from a register was read
	 * 
	 * @param machine
	 * @param position
	 */
	public void valueRead(RAMachine machine, int position);
}
