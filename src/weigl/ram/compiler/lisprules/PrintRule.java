package weigl.ram.compiler.lisprules;

import java.util.List;

import weigl.ram.commands.Command;
import weigl.ram.compiler.lisp.Atom;
import weigl.ram.compiler.lisp.ExecutionContext;
import weigl.ram.compiler.lisp.LispList;

public class PrintRule extends TranslationRule {

	public PrintRule() {
		super("print");
	}

	@Override
	public List<Command> visit(ExecutionContext ec, LispList ll) {
		int f = ec.reserve();
		valueTo(ec, ll, 1, f);
		ec.free(f);
		return CommandFactory.create(CommandFactory.loadr(f), CommandFactory.print());
	}

}
