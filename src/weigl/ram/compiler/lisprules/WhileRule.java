package weigl.ram.compiler.lisprules;

import java.util.List;

import weigl.ram.commands.Command;
import weigl.ram.commands.EmptyCommand;
import weigl.ram.compiler.lisp.Atom;
import weigl.ram.compiler.lisp.ExecutionContext;
import weigl.ram.compiler.lisp.LispList;

public class WhileRule extends TranslationRule{

	public WhileRule() {
		super("while");
		
	}

	@Override
	public List<Command> visit(ExecutionContext ec, LispList ll) {
		List<Command> cl = createList();
		cl.add(new EmptyCommand("//TODO WHILE"));
		dispatchExecution(ec, cl, (LispList) ll.get(2));
		cl.add(new EmptyCommand("//ENDHWILE"));
		return cl;
	}

}
