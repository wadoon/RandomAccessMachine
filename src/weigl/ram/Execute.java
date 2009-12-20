package weigl.ram;

import java.io.FileReader;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;

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

	public static void main(String[] args) {
		try {
			GetOpt go = new GetOpt(args, "lugvw");
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
				}
			}

			args = go.getCmdArgs();
			Reader r = args.length > 0 ? new FileReader(args[0])
					: new InputStreamReader(System.in);

			Parser p = new Parser(r);
			RAMachine machine = new RAMachine(p.getCommands());

			if (GUI) {
				final RegistersView rw = new RegistersView();
				rw.pack();
				rw.setVisible(true);
				machine.addListner(new ListenerEDTAdapter(rw));
			}
			if (CONSOLE)
				machine.addListner(new ConsoleMachineListener(System.out));

			if (WAIT)
				machine.addListner(new WaitListener(100));
			if (LOG_COST)
				machine.addListner(new LogarithmCosts());
			if (UNI_COST)
				machine.addListner(new UniformCosts());

			machine.addListner(new MachineAdapter() {
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
