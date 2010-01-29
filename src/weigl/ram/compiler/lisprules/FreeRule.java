package weigl.ram.compiler.lisprules;

import java.util.List;

import weigl.ram.commands.Command;
import weigl.ram.compiler.lisp.ExecutionContext;
import weigl.ram.compiler.lisp.LispList;

public class FreeRule extends TranslationRule {

	public FreeRule() {
		super("free");

	}

	@Override
	public List<Command> visit(ExecutionContext ec, LispList ll) {
		String v = asAtom(ll, 1).TEXT;
		ec.releaseVariable(v);
		return CommandFactory.create("Free " + v);
	}
}
