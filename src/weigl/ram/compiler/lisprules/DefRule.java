package weigl.ram.compiler.lisprules;

import java.util.List;

import weigl.ram.commands.Command;
import weigl.ram.compiler.lisp.ExecutionContext;
import weigl.ram.compiler.lisp.LispList;

public class DefRule extends TranslationRule {

	public DefRule() {
		super("def");
	}

	@Override
	public List<Command> visit(ExecutionContext ec, LispList ll) {
		String funcname = asAtom(ll, 1).TEXT;
		LispList parameters = asList(ll, 2);
		String params[] = new String[parameters.getElements().size()];
		LispList body = asList(ll, 3);

		for (int i = 0; i < parameters.getElements().size(); i++)
			params[i] = asAtom(parameters, i).TEXT;

		ec.getCompiler().defineFunction(funcname, body, params);

		return CommandFactory.create(CommandFactory
				.comment("define user function '" + funcname + "'"));
	}
}
