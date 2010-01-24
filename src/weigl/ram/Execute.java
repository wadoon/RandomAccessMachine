package weigl.ram;

import java.io.FileReader;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;

import weigl.ram.commands.Command;
import weigl.ram.compiler.BrainFuckCompiler;
import weigl.ram.listeners.LogarithmCosts;
import weigl.ram.listeners.MachineAdapter;
import weigl.ram.listeners.UniformCosts;
import weigl.ram.view.ConsoleMachineListener;
import weigl.ram.view.ListenerEDTAdapter;
import weigl.ram.view.RegistersView;
import weigl.ram.view.WaitListener;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOpt;

public class Execute {

	static boolean LOG_COST = false;
	static boolean UNI_COST = false;
	static boolean GUI = false;
	static boolean CONSOLE = false;
	static boolean WAIT = false;
	static boolean BRAINFUCK = false;

	public static void main(String[] args) {
		try {
			GetOpt go = new GetOpt(args, "blugvw");
			int i;
			while ((i = go.getNextOption()) > 0) {
				switch (i) {
				case 'l': // logarithm costs
					LOG_COST = true;
					break;
				case 'u': // uniform costs
					UNI_COST = true;
					break;
				case 'g': // gui
					GUI = true;
					break;
				case 'v':
					CONSOLE = true;
					break;
				case 'w':
					WAIT = true;
					break;
				case 'b':
					BRAINFUCK = true;
					break;
				}
			}

			args = go.getCmdArgs();
			RAMachine machine;
			Reader r = args.length > 0 ? new FileReader(args[0])
					: new InputStreamReader(System.in);
			if (BRAINFUCK) {
				BrainFuckCompiler bfc = new BrainFuckCompiler(r);
				Command[] c = bfc.parse();
				System.out.println(Arrays.toString(c));
				machine = new RAMachine(c);
			} else {
				Parser p = new Parser(r);
				machine = new RAMachine(p.getCommands());
			}

			if (GUI) {
				final RegistersView rw = new RegistersView();
				rw.pack();
				rw.setVisible(true);
				machine.addListener(new ListenerEDTAdapter(rw));
			}
			if (CONSOLE)
				machine.addListener(new ConsoleMachineListener(System.out));

			if (WAIT)
				machine.addListener(new WaitListener(1000));
			if (LOG_COST)
				machine.addListener(new LogarithmCosts());
			if (UNI_COST)
				machine.addListener(new UniformCosts());

			machine.addListener(new MachineAdapter() {
				@Override
				public void machineStop(RAMachine machine) {
					System.err
							.println("Registers:\n"
									+ Arrays.toString(machine.getRegisters()
											.getArray()));
				}
			});
			machine.start();
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}
	}
}
