package weigl.ram.compiler.lisprules;

import static weigl.ram.compiler.lisprules.CommandFactory.addr;

import java.util.List;

import weigl.ram.commands.Command;

public class AddRule extends AbstractBinaryOperation {
	public AddRule() {
		super("+");
	}

	@Override
	protected void addOperation(List<Command> cl, int v1, int v2) {
		cl.add(addr(v2));
	}
}
