package weigl.ram.compiler.lisprules;

import java.util.List;

import weigl.ram.commands.Command;
import weigl.ram.compiler.lisp.ExecutionContext;
import weigl.ram.compiler.lisp.LispList;

public class GtRule extends TranslationRule {

	public GtRule() {
		super(">");
	}

	@Override
	public List<Command> visit(ExecutionContext ec, LispList ll) {
		// TODO
		return createList();
	}

}
