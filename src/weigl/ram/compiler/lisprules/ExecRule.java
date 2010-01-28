package weigl.ram.compiler.lisprules;

import java.util.List;
import weigl.ram.commands.Command;
import weigl.ram.compiler.lisp.ExecutionContext;
import weigl.ram.compiler.lisp.LispList;

public class ExecRule extends TranslationRule {

	public ExecRule() {
		super("exec");

	}

	@Override
	public List<Command> visit(ExecutionContext ec, LispList ll) {
		List<Command> cl = createList();
		for (int i = 1; i < ll.getElements().size(); i++)
			dispatchExecution(ec, cl, (LispList) ll.get(i));
		return cl;
	}

}
