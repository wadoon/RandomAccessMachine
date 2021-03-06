package weigl.ram.compiler.lisprules;

import java.util.List;

import weigl.ram.commands.Command;
import weigl.ram.compiler.lisp.ExecutionContext;
import weigl.ram.compiler.lisp.LispList;

public class ReturnRule extends TranslationRule {

	public ReturnRule() {
		super("return");
		
	}

	@Override
	public List<Command> visit(ExecutionContext ec, LispList ll) {
		return valueTo(ec, ll, 1, 0);
	}

}
