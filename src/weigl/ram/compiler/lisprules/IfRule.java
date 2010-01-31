package weigl.ram.compiler.lisprules;

import static weigl.ram.compiler.lisprules.CommandFactory.*;

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
		List<Command> sub1 = dispatchExecution(ec, (LispList) ll.get(2));

		int temp = ec.reserve();
		l.addAll(valueTo(ec, ll, 1, temp));
		l.add(loadr(temp));
		l.add(jzeror(sub1.size() + 3));
		l.addAll(sub1);

		try {
			List<Command> sub2 = dispatchExecution(ec, (LispList) ll.get(3));
			l.add(gotor(sub2.size() + 1));
			l.addAll(sub2);
		} catch (IndexOutOfBoundsException e) {
		}
		ec.free(temp);
		return l;
	}

}
