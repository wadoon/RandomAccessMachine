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
public class EmptyCommand implements Command {
    //public static final EmptyCommand SINGLETON = new EmptyCommand("");
	private String line;

    public EmptyCommand(String l) {
		line = l;
	}

	public void exec(RAMachine curline) {
        curline.nextInstruction();
    }

    public String toString()
    {
        return line ;
    }

	@Override
	public COMMANDS_TABLE getType() {
		return COMMANDS_TABLE.EMPTY;
	}

	@Override
	public String repr() {
		return "// "+line;
	}
}
