package weigl.ram.compiler.lisprules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weigl.ram.commands.Command;
import weigl.ram.compiler.lisp.Atom;
import weigl.ram.compiler.lisp.ExecutionContext;
import weigl.ram.compiler.lisp.LispList;

public class Translator {
	private Map<Atom, TranslationRule> assoc = new HashMap<Atom, TranslationRule>();

	public Translator() {
	}

	public void register(TranslationRule... tr) {
		for (TranslationRule rule : tr)
			register(rule);
	}

	public void register(TranslationRule tr) {
		tr.setTranslator(this);
		assoc.put(tr.getAtom(), tr);
	}

	public void deregister(TranslationRule tr) {
		assoc.remove(tr.getAtom());
	}

	public void translate(ExecutionContext ec, List<Command> cl, LispList list) {
		Atom a = (Atom) list.get(0);
		TranslationRule tr = assoc.get(a);
		if (tr != null) {
			// System.out.println(tr.getClass());
			cl.addAll(tr.visit(ec, list));
		} else {
			System.err.println("Function " + a + " could not be found!");
		}
	}
}
