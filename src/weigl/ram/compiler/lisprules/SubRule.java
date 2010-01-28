package weigl.ram.compiler.lisprules;

import java.util.List;

import weigl.ram.commands.Command;

public class SubRule extends AbstractBinaryOperation {

	public SubRule() {
		super("-");
		
	}

	@Override
	protected void addOperation(List<Command> cl, int v1, int v2) {
		cl.add(CommandFactory.sub(""+v2));
	}

}
