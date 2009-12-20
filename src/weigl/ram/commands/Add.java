/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package weigl.ram.commands;

import weigl.ram.COMMANDS_TABLE;
import weigl.ram.RAMachine;

/**
 * 
 * @author alex
 */
public class Add extends AbstractCommand {

	public Add(String p) {
		super(p, COMMANDS_TABLE.ADD);
	}

	public void exec(RAMachine machine) {
		machine.rset(0, machine.rget(0) + machine.rget(operand));
		machine.nextInstruction();
	}
}
