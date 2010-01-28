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
public class Div extends AbstractCommand implements Command {
	public Div(String p) {
		super(p, COMMANDS_TABLE.DIV);
	}

	public void exec(RAMachine machine) {
		try {
			int i = machine.rget(0) / machine.rget(operand);
			machine.rset(0, i);
			machine.nextInstruction();
		} catch (ArithmeticException e) {
			machine.end();
		}

	}
}
