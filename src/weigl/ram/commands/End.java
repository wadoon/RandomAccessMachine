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
public class End implements Command {
    public End() {
    }

    public void exec(RAMachine machine) {
       machine.end();
    }


    public String toString()
    {
        return getType().name();
    }

	@Override
	public COMMANDS_TABLE getType() {
		return COMMANDS_TABLE.END;
	}
}
