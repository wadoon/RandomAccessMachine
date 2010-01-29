package weigl.ram.compiler.lisprules;

import java.util.List;

import weigl.ram.commands.Command;
import weigl.ram.compiler.lisp.ExecutionContext;
import weigl.ram.compiler.lisp.LispList;

public class FalseRule extends TranslationRule {

	public FalseRule() {
		super("false");

	}

	@Override
	public List<Command> visit(ExecutionContext ec, LispList ll) {
		return CommandFactory.create("Loading false", CommandFactory.load(0));
	}
}
