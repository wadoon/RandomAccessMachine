package weigl.ram.compiler;

import java.io.IOException;

import java.util.LinkedList;
import java.util.List;


import weigl.io.file.FileUtils;
import weigl.ram.commands.Command;
import weigl.ram.compiler.lisp.ExecutionContext;
import weigl.ram.compiler.lisp.LispList;
import weigl.ram.compiler.lisprules.AddRule;
import weigl.ram.compiler.lisprules.DefineRule;
import weigl.ram.compiler.lisprules.DivRule;
import weigl.ram.compiler.lisprules.ExecRule;
import weigl.ram.compiler.lisprules.FreeRule;
import weigl.ram.compiler.lisprules.GtRule;
import weigl.ram.compiler.lisprules.IfRule;
import weigl.ram.compiler.lisprules.LtRule;
import weigl.ram.compiler.lisprules.MultRule;
import weigl.ram.compiler.lisprules.PrintRule;
import weigl.ram.compiler.lisprules.SetExprRule;
import weigl.ram.compiler.lisprules.SubRule;
import weigl.ram.compiler.lisprules.Translator;
import weigl.ram.compiler.lisprules.TrueRule;
import weigl.ram.compiler.lisprules.WhileRule;

public class LispCompiler {
	private Translator translator;

	public LispCompiler() {
		translator = new Translator();
		registerRules();
		
	}

	private void registerRules() {
		translator.register(
				new AddRule(),
				new SubRule(),
				new IfRule(),
				new DivRule(),
				new MultRule(), 
				new WhileRule(),
				new SetExprRule(),
				new DefineRule(),
				new ExecRule(),
				new PrintRule(),
				new FreeRule(),
				new LtRule(),
				new GtRule(),
				new TrueRule()
		);
	}

	public void compile(String source) {
		LispParser lp = new LispParser(source);
		List<LispList> rootLists = lp.getRootLists();
		ExecutionContext ec = new ExecutionContext();
		final List<Command> list = new LinkedList<Command>();
		for (LispList lispList : rootLists) 
		{
			translator.translate(ec,list, lispList);
		}
		int i = 1;
		for (Command command : list) {
			System.out.format("%5d: %s%n", i++,command.repr());
		}
	}
	
	public static void main(String[] args) throws IOException {
//		String s = "(+ (- 1 1) 1)";
		String s = FileUtils.readFile("npowerm.lisp");
//		String s = FileUtils.readFile("while.lisp");
		LispCompiler lc = new LispCompiler();
		lc.compile(s);
	}
}

