package weigl.ram.compiler.lisprules;

import java.util.List;

import weigl.ram.commands.Command;
import static weigl.ram.compiler.lisprules.CommandFactory.divr;

public class DivRule extends AbstractBinaryOperation {
	public DivRule() {
		super("/");
	}

	@Override
	protected void addOperation(List<Command> cl, int v1, int v2) {
		cl.add(divr(v2));
	}
}
