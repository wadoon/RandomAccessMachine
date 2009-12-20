/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package weigl.ram.commands;

import java.util.Map;

import weigl.ram.COMMANDS_TABLE;
import weigl.ram.RAMachine;

/**
 * 
 * @author alex
 */
public class JZero extends AbstractCommand {
	private Map<String, Integer> jumpTable;

	public JZero(Map<String, Integer> jumpTable, String p) {
		super(p,COMMANDS_TABLE.JZERO);
		this.jumpTable = jumpTable;
	}

	public void exec(RAMachine machine) {
		if (machine.rget(0) == 0)
			new Goto(jumpTable, operand).exec(machine);
		else
			machine.nextInstruction();
	}

}
