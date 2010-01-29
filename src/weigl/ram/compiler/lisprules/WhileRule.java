package weigl.ram.compiler.lisprules;

import static weigl.ram.compiler.lisprules.CommandFactory.comment;
import static weigl.ram.compiler.lisprules.CommandFactory.goto_;
import static weigl.ram.compiler.lisprules.CommandFactory.gotor;
import static weigl.ram.compiler.lisprules.CommandFactory.jzeror;

import java.util.List;

import weigl.ram.commands.Command;
import weigl.ram.compiler.lisp.ExecutionContext;
import weigl.ram.compiler.lisp.LispList;

public class WhileRule extends TranslationRule {

	public WhileRule() {
		super("while");

	}

	/**
	 * <pre>
	 * while true
	 *   a 
	 *   b
	 *   c
	 * end
	 * 
	 * 1: goto 5
	 * 2:  a 
	 * 3:  b
	 * 4:  c
	 * 5: do check
	 * 6: jzero 8
	 * 7: goto 2
	 * 8: ...
	 * </pre>
	 */
	@Override
	public List<Command> visit(ExecutionContext ec, LispList ll) {
		List<Command> cl = createList();

		List<Command> check = dispatchExecution(ec, (LispList) ll.get(1));
		List<Command> sub = dispatchExecution(ec, (LispList) ll.get(2));

		cl.add(comment("WHILE"));
		// goto 5
		cl.add(gotor(sub.size() + 2));
		// a,b,c
		cl.addAll(sub);
		// do check
		cl.add(comment("do check"));
		cl.addAll(check);
		// jzero
		cl.add(jzeror(3));
		// goto 2
		cl.add(goto_("-" + (cl.size() - 3)));
		cl.add(comment("ENDHWILE"));
		return cl;
	}
}
