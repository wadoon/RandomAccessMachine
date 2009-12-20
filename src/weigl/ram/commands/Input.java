/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package weigl.ram.commands;

import java.io.IOException;

import weigl.ram.COMMANDS_TABLE;
import weigl.ram.RAMachine;

/**
 *
 * @author alex
 */
public class Input extends AbstractCommand implements Command {

    public Input() {
		super("0",COMMANDS_TABLE.INPUT);
	}


	public static Input SINGLETON = new Input();

    public void exec(RAMachine machine) {
        try {
            machine.rset(0, System.in.read());
            machine.nextInstruction();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
