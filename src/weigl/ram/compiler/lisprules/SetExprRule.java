package weigl.ram.compiler.lisprules;

import static weigl.ram.compiler.lisprules.CommandFactory.create;

import java.util.List;

import weigl.ram.commands.Command;
import weigl.ram.compiler.lisp.ExecutionContext;
import weigl.ram.compiler.lisp.LispList;

public class SetExprRule extends TranslationRule {
	public SetExprRule() {
		super("set");
	}

	@Override
	public List<Command> visit(ExecutionContext ec, LispList ll) {
		String vname = asAtom(ll, 1).TEXT;
		List<Command> cl = create("Assign " + vname);
		int pos = ec.getFieldPosition(vname);
		cl.addAll(valueTo(ec, ll, 2, pos));
		return cl;
	}
}
