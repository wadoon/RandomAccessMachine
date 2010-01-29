package weigl.ram.compiler.lisprules;

import static weigl.ram.compiler.lisprules.CommandFactory.mult;

import java.util.List;

import weigl.ram.commands.Command;

;

public class MultRule extends AbstractBinaryOperation {
	public MultRule() {
		super("*");

	}

	@Override
	protected void addOperation(List<Command> cl, int v1, int v2) {
		cl.add(mult("" + v2));
	}
}
