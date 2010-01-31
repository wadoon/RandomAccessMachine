package weigl.ram.compiler.lisprules;

import java.util.List;

import weigl.ram.commands.Command;
import weigl.ram.compiler.lisp.ExecutionContext;
import weigl.ram.compiler.lisp.LispList;

public class SetOptRule extends TranslationRule {

	public SetOptRule() {
		super("setopt");		
	}

	@Override
	public List<Command> visit(ExecutionContext ec, LispList ll) {
		String name = asAtom(ll,1).TEXT;
		int value   = asConstant(ll, 2).VALUE;
		ec.setOption(name, value);
		return createList();
	}
}
