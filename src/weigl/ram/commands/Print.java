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
public class Print extends AbstractCommand {

	public Print() {
		super("0", COMMANDS_TABLE.PRINT);
	}

	public static Print SINGLETON = new Print();

	public void exec(RAMachine machine) {
		System.out.println(machine.rget(0));
		machine.nextInstruction();
	}
}
