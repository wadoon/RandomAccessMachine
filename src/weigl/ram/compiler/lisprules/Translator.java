package weigl.ram.compiler.lisprules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import weigl.ram.commands.Command;
import weigl.ram.compiler.lisp.Atom;
import weigl.ram.compiler.lisp.CompilerException;
import weigl.ram.compiler.lisp.ExecutionContext;
import weigl.ram.compiler.lisp.LispFunction;
import weigl.ram.compiler.lisp.LispList;

public class Translator {
	private Map<Atom, TranslationRule> ruleMap = new HashMap<Atom, TranslationRule>();
	private Map<String, LispFunction> functionMap = new HashMap<String, LispFunction>();
	private CallUserFunction caller = new CallUserFunction();

	public Translator() {
	}

	public void register(TranslationRule... tr) {
		for (TranslationRule rule : tr)
			register(rule);
	}

	public void register(TranslationRule tr) {
		ruleMap.put(tr.getAtom(), tr);
	}

	public void deregister(TranslationRule tr) {
		ruleMap.remove(tr.getAtom());
	}

	public void translate(ExecutionContext ec, List<Command> cl, LispList list) {
		Atom a = (Atom) list.get(0);
		TranslationRule tr = ruleMap.get(a);
		if (tr != null) {
			cl.addAll(tr.visit(ec, list));
		} else {
			translateFunction(ec, a.TEXT, cl, list);
		}
	}

	private void translateFunction(ExecutionContext ec, String name,
			List<Command> cl, LispList list) {
		LispFunction function = functionMap.get(name);
		if (function != null) {
			cl.addAll(caller.visit(ec, list, function));
		} else {
			throw new CompilerException("A function with name '" + name
					+ "' was not found!");
		}
	}

	public Map<String, LispFunction> getFunctions() {
		return functionMap;
	}

	public Set<Atom> getRuleNames() {
		return ruleMap.keySet();
	}
}
