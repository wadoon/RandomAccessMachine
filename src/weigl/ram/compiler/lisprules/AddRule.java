package weigl.ram.compiler.lisprules;


import java.util.List;

import weigl.ram.commands.Add;
import weigl.ram.commands.Command;
import weigl.ram.commands.Load;
import weigl.ram.compiler.lisp.Atom;
import static weigl.ram.compiler.lisprules.CommandFactory.*;

public class AddRule extends AbstractBinaryOperation {
	public AddRule() {
		super("+");
	}

	@Override
	protected void addOperation(List<Command> cl, int v1, int v2) {
		cl.add(addr(v2));
	}
}
