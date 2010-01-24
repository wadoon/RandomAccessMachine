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

	private Map<String, Integer> jumpTable;

	public Goto(Map<String, Integer> jumpTable, String p) {
		super(p, COMMANDS_TABLE.GOTO);
		this.jumpTable = jumpTable;
	}

	public void exec(RAMachine machine) {

		try {
			machine.setInstruction(machine.rget(operand) - 1);
		} catch (NumberFormatException e) {
			try {
				machine.setInstruction(jumpTable.get(operand) - 1);
			} catch (Exception e1) {
				System.err.println(operand + " label unknown");
				machine.end();
			}
		}
	}

	@Override
	public COMMANDS_TABLE getType() {
		return COMMANDS_TABLE.GOTO;
	}

	public void setOperand(String jumpAddress) {
		operand=jumpAddress;
	}

}
