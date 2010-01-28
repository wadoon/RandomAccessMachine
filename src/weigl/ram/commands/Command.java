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
public interface Command {
    public void exec(RAMachine machine);
    public COMMANDS_TABLE getType();
    public String repr();
}
