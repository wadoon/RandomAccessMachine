package weigl.ram.compiler.lisprules;

import java.util.List;

import weigl.ram.commands.Command;
import weigl.ram.compiler.lisp.ExecutionContext;
import weigl.ram.compiler.lisp.LispList;

public class DefineRule extends TranslationRule {

	public DefineRule() {
		super("define");
	}

	@Override
	public List<Command> visit(ExecutionContext ec, LispList ll) {
		List<Command> l = createList();
		
		for (int i = 1; i < ll.getElements().size(); i++) 
		{
			String var = asAtom(ll, i).TEXT;
			l.add(CommandFactory.comment("Define variable " + var));
			ec.defineVariable(var);
		}
		return l;
	}
}
