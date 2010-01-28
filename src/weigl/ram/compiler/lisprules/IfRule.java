package weigl.ram.compiler.lisprules;

import java.util.List;


import weigl.ram.commands.Command;
import weigl.ram.compiler.lisp.ExecutionContext;
import weigl.ram.compiler.lisp.LispList;

public class IfRule extends TranslationRule{

	public IfRule() {
		super("if");
		
	}

	@Override
	public List<Command> visit(ExecutionContext ec, LispList ll) {
		return null;
	}

}
