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
public class Goto extends AbstractCommand {

	private String param;
	private Map<String, Integer> jumpTable;

	public Goto(Map<String, Integer> jumpTable, String p) {
		super(p, COMMANDS_TABLE.GOTO);
		param = p;
		this.jumpTable = jumpTable;
	}

	public void exec(RAMachine machine) {

		try {
			machine.setInstruction(machine.rget(param) - 1);
		} catch (NumberFormatException e) {
			try {
				machine.setInstruction(jumpTable.get(param) - 1);
			} catch (Exception e1) {
				System.err.println(param + " label unknown");
				machine.end();
			}
		}
	}

	@Override
	public COMMANDS_TABLE getType() {
		return COMMANDS_TABLE.GOTO;
	}

}
