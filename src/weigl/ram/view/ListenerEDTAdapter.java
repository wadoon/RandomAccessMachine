package weigl.ram.view;

import javax.swing.SwingUtilities;

import weigl.ram.RAMachine;
import weigl.ram.listeners.MachineListener;

/**
 * Wraps an {@link MachineListener} and routs each event to the EDT thread.
 * 
 * 
 * @author Alexander Weigl <alexweigl@gmail.com>
 * @date 10.11.2009
 * @version 1
 * 
 */
public class ListenerEDTAdapter implements MachineListener {

	private MachineListener mlistener;

	public ListenerEDTAdapter(MachineListener mlistener) {
		this.mlistener = mlistener;
	}

	@Override
	public void instructionEnd(final RAMachine machine) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					mlistener.instructionEnd(machine);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void machineStart(final RAMachine machine) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					mlistener.machineStart(machine);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void machineStop(final RAMachine machine) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					mlistener.machineStop(machine);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void nextInstruction(final RAMachine machine) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					mlistener.nextInstruction(machine);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void valueRead(final RAMachine machine, final int position) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					mlistener.valueRead(machine, position);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void valueSet(final RAMachine machine, final int position) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					mlistener.valueSet(machine, position);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
