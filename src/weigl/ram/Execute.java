package weigl.ram;

import java.io.IOException;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOpt;

/**
 * Main-Class for RandomAccessMachine
 * 
 * @author Alexander Weigl <alexweigl@gmail.com>
 * @version 1
 */
public class Execute {
	public static void main(String[] args) throws IOException, IllegalArgumentException, Exception {
		ExecutionOptions eo = new ExecutionOptions();

		GetOpt go = new GetOpt(args, "blugvwr:");
		int i;
		while ((i = go.getNextOption()) > 0) {
			switch (i) {
			case 'l': // logarithm costs
				eo.setLogCosts(true);
				break;
			case 'u': // uniform costs
				eo.setUniformCosts(true);
				break;
			case 'g': // gui
				eo.setGuiView(true);
				break;
			case 'v':
				eo.setConsoleLogging(true);
				break;
			case 'w':
				eo.setWaitAfterEachCommand(true);
				break;
			case 'b':
				eo.setBfCompiler(true);
				break;
			case 'r':
				eo.setRegisterFile(go.getOptionArg());
				eo.setCheckRegisters(true);
				break;
			case 's':
				eo.setLispCompiler(true);
				break;
			}
		}

		args = go.getCmdArgs();
		eo.setInputFile(args[0]);
		RAMExecutor exec = new RAMExecutor(eo);
		exec.build();
		exec.run();
	}
}
