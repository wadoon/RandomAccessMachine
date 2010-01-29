package weigl.ram.compiler.lisprules;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import weigl.ram.commands.Command;
import weigl.ram.commands.EmptyCommand;
import weigl.ram.commands.ext.ExtendedCommands.Add;
import weigl.ram.commands.ext.ExtendedCommands.Div;
import weigl.ram.commands.ext.ExtendedCommands.End;
import weigl.ram.commands.ext.ExtendedCommands.GT;
import weigl.ram.commands.ext.ExtendedCommands.Goto;
import weigl.ram.commands.ext.ExtendedCommands.JZero;
import weigl.ram.commands.ext.ExtendedCommands.LT;
import weigl.ram.commands.ext.ExtendedCommands.Load;
import weigl.ram.commands.ext.ExtendedCommands.Multi;
import weigl.ram.commands.ext.ExtendedCommands.Print;
import weigl.ram.commands.ext.ExtendedCommands.Store;
import weigl.ram.commands.ext.ExtendedCommands.Sub;

public class CommandFactory {
	public static Add add(String addr) {
		return new Add(addr);
	}

	public static Add addc(int value) {
		return add("#" + value);
	}

	public static Add addr(int register) {
		return add("" + register);
	}

	public static EmptyCommand comment(String line) {
		return new EmptyCommand(line);
	}

	public static List<Command> create(Command... c) {
		return Arrays.asList(c);
	}

	public static List<Command> create(String comment, Command... c) {
		List<Command> l = new LinkedList<Command>();
		l.add(new EmptyCommand(comment));
		l.addAll(create(c));
		return l;
	}

	public static Div div(int value) {
		return div("#" + value);
	}

	public static Div div(String addr) {
		return new Div(addr);
	}

	public static Div divc(int c) {
		return div("#" + c);
	}

	public static Div divr(int register) {
		return div("" + register);
	}

	public static End end() {
		return new End();
	}

	public static Goto goto_(int line) {
		return goto_("#" + line);
	}

	public static Goto goto_(String addr) {
		return new Goto(null, addr);
	}

	public static Goto gotor(int line) {
		return goto_("+" + line);
	}

	public static GT gt(int value) {
		return gt("#" + value);
	}

	public static GT gt(String param) {
		return new GT(param);
	}

	public static GT gtr(int register) {
		return gt("" + register);
	}

	public static JZero jzero(int line) {
		return jzero("#" + line);
	}

	public static JZero jzero(String addr) {
		return new JZero(null, addr);
	}

	public static JZero jzeror(int line) {
		return jzero("+" + line);
	}

	public static Load load(int value) {
		return load("#" + value);
	}

	public static Load load(String addr) {
		return new Load(addr);
	}

	public static Load loadr(int value) {
		return load("" + value);
	}

	public static LT lt(int value) {
		return lt("#" + value);
	}

	public static LT lt(String param) {
		return new LT(param);
	}

	public static LT ltr(int v2) {
		return lt("" + v2);
	}

	public static Multi mult(int value) {
		return mult("#" + value);
	}

	public static Multi mult(String addr) {
		return new Multi(addr);
	}

	public static Print print() {
		return new Print();
	}

	public static Store store(String addr) {
		return new Store(addr);
	}

	public static Store storer(int register) {
		return store("#" + register);
	}

	public static Sub sub(int value) {
		return sub("#" + value);
	}

	public static Sub sub(String addr) {
		return new Sub(addr);
	}
}
