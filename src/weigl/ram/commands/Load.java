/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package weigl.ram.commands;

import weigl.ram.*;
import weigl.ram.commands.Command;

/**
 * 
 * @author alex
 */
public class Load extends AbstractCommand implements Command {
	public Load(String string) {
		super(string, COMMANDS_TABLE.LOAD);
	}

	public void exec(RAMachine machine) {
		machine.rset(0, machine.rget(operand));
		machine.nextInstruction();
	}

}
