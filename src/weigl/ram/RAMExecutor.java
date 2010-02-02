package weigl.ram;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import weigl.ram.commands.Command;
import weigl.ram.compiler.BrainFuckCompiler;
import weigl.ram.compiler.LispCompiler;
import weigl.ram.listeners.LogarithmCosts;
import weigl.ram.listeners.MachineAdapter;
import weigl.ram.listeners.MachineListener;
import weigl.ram.listeners.RegisterTest;
import weigl.ram.listeners.UniformCosts;
import weigl.ram.view.ConsoleMachineListener;
import weigl.ram.view.ListenerEDTAdapter;
import weigl.ram.view.RegistersView;
import weigl.ram.view.WaitListener;

/**
 * 
 * @author Alexander Weigl <alexweigl@gmail.com>
 * @date 2010-02-02
 */
public class RAMExecutor implements Runnable {
	private ExecutionOptions options;
	private RAMachine machine;

	public RAMExecutor(ExecutionOptions eo) {
		this.options = eo;
	}

	public void build() throws IOException {
		Reader r = getReader(options.getInputFile());
		Command[] commands;
		Map<String, Integer> varMap = null;
		if (options.isBfCompiler()) {
			commands = new BrainFuckCompiler(r).parse();
		} else {
			if (options.isLispCompiler()) {
				LispCompiler lispCompiler = new LispCompiler();
				commands = lispCompiler.compile(r);
				varMap = lispCompiler.getLastExecutionContext()
						.getVariableMap();
			} else {
				Parser p = new Parser(r);
				commands = p.getCommands();
			}
		}
		machine = new RAMachine(commands);

		if (options.isGuiView()) {
			final RegistersView rw = new RegistersView();
			rw.pack();
			rw.setVisible(true);
			machine.addListener(new ListenerEDTAdapter(rw));
		}
		if (options.isConsoleLogging())
			machine.addListener(new ConsoleMachineListener(System.out));

		if (options.isWaitAfterEachCommand())
			machine.addListener(new WaitListener(options.getWaitTime()));
		if (options.isLogCosts())
			machine.addListener(new LogarithmCosts());
		if (options.isUniformCosts())
			machine.addListener(new UniformCosts());
		if (options.isCheckRegisters()) {
			MachineListener listener = registerTest(varMap, options
					.getRegisterFile());
			machine.addListener(listener);
		}
		machine.addListener(new MachineAdapter() {
			@Override
			public void machineStop(RAMachine machine) {
				System.err.println("Registers:\n"
						+ Arrays.toString(machine.getRegisters().getArray()));
			}
		});
	}

	private Reader getReader(String inputFile) throws FileNotFoundException {
		if (inputFile.equals("--")) {
			return new InputStreamReader(System.in);
		} else {
			return new FileReader(inputFile);
		}
	}

	@Override
	public void run() {
		machine.start();
	}

	private static RegisterTest registerTest(Map<String, Integer> vars,
			String fileName) throws FileNotFoundException, IOException {
		Properties p = new Properties();
		p.load(new FileReader(fileName));
		Enumeration<Object> e = p.keys();
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();

		while (e.hasMoreElements()) {
			String o = e.nextElement().toString();
			Integer j = new Integer(p.getProperty(o));
			Integer i;
			try {
				i = new Integer(o);
			} catch (NumberFormatException nfe) {
				if (vars != null)
					i = vars.get(o);
				else
					i = -1;
			}
			map.put(i, j);
		}
		return new RegisterTest(map);
	}
}