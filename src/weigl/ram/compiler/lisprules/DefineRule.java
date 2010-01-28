package weigl.ram.compiler.lisprules;

import java.util.List;

import weigl.ram.commands.Command;
import weigl.ram.commands.EmptyCommand;
import weigl.ram.compiler.lisp.Atom;
import weigl.ram.compiler.lisp.ExecutionContext;
import weigl.ram.compiler.lisp.LispList;
import weigl.ram.compiler.lisp.LispType;

public class DefineRule extends TranslationRule {

	public DefineRule() {
		super("define");
	}

	@Override
	public List<Command> visit(ExecutionContext ec, LispList ll) {
		List<Command> l = createList();
		for (int i = 1; i < ll.getElements().size(); i++) {
			LispType lt = ll.get(i);
			String varname = ((Atom) lt).TEXT;
			int pos = ec.defineVariable(varname);
			l
					.add(new EmptyCommand("define " + varname + " to register "
							+ pos));
		}
		return l;
	}
}
