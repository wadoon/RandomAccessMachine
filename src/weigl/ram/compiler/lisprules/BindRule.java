package weigl.ram.compiler.lisprules;

import java.util.List;

import weigl.ram.commands.Command;
import weigl.ram.compiler.lisp.ExecutionContext;
import weigl.ram.compiler.lisp.LispList;

public class BindRule extends TranslationRule {

	public BindRule() {
		super("bind");
	}

	@Override
	public List<Command> visit(ExecutionContext ec, LispList ll) {
		String varname = asAtom(ll, 1).TEXT;
		int register = asConstant(ll, 2).VALUE;
		ec.bindField(varname, register);
		return CommandFactory.create("Assign "+varname+" to position "+ register);
	}

}
