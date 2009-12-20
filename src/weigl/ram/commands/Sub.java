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
public class Sub extends AbstractCommand {

	public Sub(String p) {
		super(p,COMMANDS_TABLE.SUB);
	}

	public void exec(RAMachine machine) {
		int i = machine.rget(0) - machine.rget(operand);
		machine.rset(0, Math.max(0, i));
		machine.nextInstruction();
	}
}
