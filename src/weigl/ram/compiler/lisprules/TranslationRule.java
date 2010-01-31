package weigl.ram.compiler.lisprules;

import static weigl.ram.compiler.lisprules.CommandFactory.load;
import static weigl.ram.compiler.lisprules.CommandFactory.loadr;

import java.util.LinkedList;
import java.util.List;

import weigl.ram.commands.Command;
import weigl.ram.compiler.lisp.Atom;
import weigl.ram.compiler.lisp.CompilerException;
import weigl.ram.compiler.lisp.Constant;
import weigl.ram.compiler.lisp.ExecutionContext;
import weigl.ram.compiler.lisp.LispList;
import weigl.ram.compiler.lisp.LispType;

/**
 * defines a tranlation rule from an {@link Atom} to the {@link Command}s
 * 
 * @author Alexander Weigl <alexweigl@gmail.com>
 */
public abstract class TranslationRule {
	protected Atom atom;

	public TranslationRule(String atom) {
		this(new Atom(atom));
	}

	public TranslationRule(Atom atom) {
		this.atom = atom;
	}

	public Atom getAtom() {
		return atom;
	}

	protected static List<Command> createList() {
		return new LinkedList<Command>();
	}

	public abstract List<Command> visit(ExecutionContext ec, LispList ll);

	protected Atom asAtom(LispList ll, int pos) {
		try {
			return (Atom) ll.get(pos);
		} catch (ClassCastException cce) {
			throw new CompilerException("Function " + atom + " expected value "
					+ pos + " as an Name", cce);
		}
	}

	protected Constant asConstant(LispList ll, int pos) {
		try {
			return (Constant) ll.get(pos);
		} catch (ClassCastException cce) {
			throw new CompilerException("Function " + atom + " expected value "
					+ pos + " as an Constant", cce);
		}
	}

	protected LispList asList(LispList ll, int i) {
		return (LispList) ll.get(i);
	}

	static List<Command> valueTo(ExecutionContext ec, LispList ll, int pos,
			int register) {
		List<Command> cl = createList();
		LispType lispType = ll.get(pos);

		if (lispType instanceof Atom) {
			Atom atom = (Atom) lispType;
			cl.add(loadr(ec.getFieldPosition(atom.TEXT)));
		}
		if (lispType instanceof Constant) {
			Constant constant = (Constant) lispType;
			cl.add(load(constant.VALUE));
		}

		if (lispType instanceof LispList) {
			LispList list = (LispList) lispType;
			dispatchExecution(ec, cl, list);
		}
		cl.add(CommandFactory.storer(register));
		return cl;
	}

	static void dispatchExecution(ExecutionContext ec, List<Command> cl,
			LispList list) {
		ec.getTranslator().translate(ec, cl, list);
	}

	static List<Command> dispatchExecution(ExecutionContext ec,
			LispList lispList) {
		List<Command> l = createList();
		dispatchExecution(ec, l, lispList);
		return l;
	}
}
