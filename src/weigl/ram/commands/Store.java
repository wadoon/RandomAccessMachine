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
public class Store extends AbstractCommand {

	public Store(String p) {
		super(p,COMMANDS_TABLE.STORE);
	}

	public void exec(RAMachine machine) {
		int where = machine.rget(operand);
		int value = machine.rget(0);
		machine.rset(where , value);
		machine.nextInstruction();
	}

}
