package weigl.ram.compiler;

import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import weigl.io.file.FileUtils;
import weigl.ram.RAMachine;
import weigl.ram.commands.Command;
import weigl.ram.compiler.lisp.ExecutionContext;
import weigl.ram.compiler.lisp.LispFunction;
import weigl.ram.compiler.lisp.LispList;
import weigl.ram.compiler.lisprules.AddRule;
import weigl.ram.compiler.lisprules.BindRule;
import weigl.ram.compiler.lisprules.CommentRule;
import weigl.ram.compiler.lisprules.DefineRule;
import weigl.ram.compiler.lisprules.DivRule;
import weigl.ram.compiler.lisprules.ExecRule;
import weigl.ram.compiler.lisprules.FreeRule;
import weigl.ram.compiler.lisprules.GtRule;
import weigl.ram.compiler.lisprules.IfRule;
import weigl.ram.compiler.lisprules.LtRule;
import weigl.ram.compiler.lisprules.MultRule;
import weigl.ram.compiler.lisprules.PrintRule;
import weigl.ram.compiler.lisprules.ReturnRule;
import weigl.ram.compiler.lisprules.SetOptRule;
import weigl.ram.compiler.lisprules.SetRule;
import weigl.ram.compiler.lisprules.SubRule;
import weigl.ram.compiler.lisprules.Translator;
import weigl.ram.compiler.lisprules.TrueRule;
import weigl.ram.compiler.lisprules.WhileRule;
import weigl.ram.listeners.MachineListener;
import weigl.ram.listeners.UniformCosts;

public class LispCompiler {
	private Translator translator;
	private ExecutionContext lastExecutionContext;

	public LispCompiler() {
		translator = new Translator();
		registerRules();
	}

	private void registerRules() {
		translator.register(new AddRule(), new SubRule(), new IfRule(),
				new DivRule(), new MultRule(), new WhileRule(), new SetRule(),
				new DefineRule(), new ExecRule(), new PrintRule(),
				new FreeRule(), new LtRule(), new GtRule(), new TrueRule(),
				new BindRule(), new SetOptRule(), new ReturnRule(),
				new DefineRule(), new CommentRule());
	}

	public List<Command> compile(String source) {
		LispParser lp = new LispParser(source);
		List<LispList> rootLists = lp.getRootLists();
		ExecutionContext ec = new ExecutionContext(this);
		final List<Command> list = new LinkedList<Command>();
		for (LispList lispList : rootLists) {
			translator.translate(ec, list, lispList);
		}

		int i = 1;
		for (Command command : list) {
			System.out.format("%5d: %s%n", i++, command.repr());
		}
		lastExecutionContext = ec;
		return list;
	}

	public static void runTestCompiler(String filename) throws IOException {
		String commands = FileUtils.readFile(filename + ".lisp");

		LispCompiler lc = new LispCompiler();
		List<Command> l = lc.compile(commands);

		MachineListener tester = registerTest(lc.getLastExecutionContext(),
				filename + ".registers");

		RAMachine machine = new RAMachine(l.toArray(new Command[] {}), 200);
//		machine.addListener(new WaitListener(1000));
		machine.addListener(new UniformCosts());

//		RegistersView rw = new RegistersView();
//		rw.pack();
//		rw.setVisible(true);
//		machine.addListener(rw);

//		machine.addListener(new MachineAdapter() {
//			@Override
//			public void machineStop(RAMachine machine) {
//				for (int i = 10; i <= 30; i++) 
//				{
//					System.out.print( machine.rget(i) + " ");
//				}
//			}
//			
//		});

		if  (tester != null)
			machine.addListener(tester);
		
		machine.start();
	}

	private ExecutionContext getLastExecutionContext() {
		return lastExecutionContext;
	}

	private static RegisterTest registerTest(ExecutionContext ec,
			String fileName) {
		try {
			Properties p = new Properties();
			p.load(new FileReader(fileName));
			Enumeration<Object> e = p.keys();
			Map<Integer, Integer> map = new HashMap<Integer, Integer>();

			// transfer to map
			while (e.hasMoreElements()) {
				String o = e.nextElement().toString();
				Integer j = new Integer(p.getProperty(o));
				Integer i;
				try {
					i = new Integer(o);
				} catch (NumberFormatException nfe) {
					i = ec.getFieldPosition(o);
				}
				map.put(i, j);
			}

			return new RegisterTest(map);

		} catch (IOException e) {
			// e.printStackTrace();
			return null;
		}
	}

	public void defineFunction(String name, LispList body, String... params) {
		translator.getFunctions().put(name,
				LispFunction.create(name, body, params));
	}

	public Translator getTranslator() {
		return translator;
	}

	public static void main(String[] args) throws IOException {
		runTestCompiler("examples/func");
	}
}
