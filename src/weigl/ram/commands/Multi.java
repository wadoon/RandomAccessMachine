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
public class Multi extends AbstractCommand {

	public Multi(String p) {
		super(p,COMMANDS_TABLE.MULT);
	}

	public void exec(RAMachine machine) {
		machine.rset(0, machine.rget(0) * machine.rget(operand));
		machine.nextInstruction();
	}
}
