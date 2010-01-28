package weigl.ram.compiler.lisprules;

import static weigl.ram.compiler.lisprules.CommandFactory.addr;
import static weigl.ram.compiler.lisprules.CommandFactory.loadr;

import java.util.List;

import weigl.ram.commands.Command;
import weigl.ram.compiler.lisp.Atom;
import weigl.ram.compiler.lisp.ExecutionContext;
import weigl.ram.compiler.lisp.LispList;

public abstract class AbstractBinaryOperation extends TranslationRule {
	public AbstractBinaryOperation(String atom) {
		super(atom);
	}

	public AbstractBinaryOperation(Atom atom) {
		super(atom);
	}

	@Override
	public List<Command> visit(ExecutionContext ec, LispList ll) {
		// (+ (- 1 2) 2)

		List<Command> cl = createList();

		int posX = ec.reserve();
		int posY = ec.reserve();

		cl.addAll(valueTo(ec, ll, 1, posX));
		cl.addAll(valueTo(ec, ll, 2, posY));

		cl.add(loadr(posX));
		addOperation(cl, posX, posY);
		
		ec.free(posX,posY);
		
		return cl;
	}

	protected abstract void addOperation(List<Command> cl, int v1, int v2);
}
