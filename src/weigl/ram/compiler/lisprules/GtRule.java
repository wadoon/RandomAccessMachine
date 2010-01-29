package weigl.ram.compiler.lisprules;

import java.util.List;

import weigl.ram.commands.Command;

public class GtRule extends AbstractBinaryOperation {

	public GtRule() {
		super(">");
	}

	@Override
	protected void addOperation(List<Command> cl, int r1, int r2) {
		cl.add( CommandFactory.gtr(r2)  );	
	}
}
