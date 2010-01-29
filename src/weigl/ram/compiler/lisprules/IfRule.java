package weigl.ram.compiler.lisprules;

import static weigl.ram.compiler.lisprules.CommandFactory.jzeror;
import static weigl.ram.compiler.lisprules.CommandFactory.loadr;

import java.util.List;

import weigl.ram.commands.Command;
import weigl.ram.compiler.lisp.ExecutionContext;
import weigl.ram.compiler.lisp.LispList;

public class IfRule extends TranslationRule {

	public IfRule() {
		super("if");

	}

	/**
	 * - if = jzero a b c ->to here
	 */
	@Override
	public List<Command> visit(ExecutionContext ec, LispList ll) {
		List<Command> l = createList();
		List<Command> sub = dispatchExecution(ec, (LispList) ll.get(2));
		int temp = ec.reserve();
		l.addAll(valueTo(ec, ll, 1, temp));
		l.add(loadr(temp));
		l.add(jzeror(sub.size() + 1));
		l.addAll(sub);
		return l;
	}

}
