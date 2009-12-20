package weigl.ram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import weigl.ram.commands.Add;
import weigl.ram.commands.Command;
import weigl.ram.commands.Div;
import weigl.ram.commands.EmptyCommand;
import weigl.ram.commands.End;
import weigl.ram.commands.Goto;
import weigl.ram.commands.Input;
import weigl.ram.commands.JZero;
import weigl.ram.commands.Load;
import weigl.ram.commands.Multi;
import weigl.ram.commands.Print;
import weigl.ram.commands.Store;
import weigl.ram.commands.Sub;

public class Parser {

	private Map<String, Integer> labelmap = new HashMap<String, Integer>();
	private Command[] commands;

	// private ParserException parseException;

	public Parser(Reader r) throws IOException {
		List<String> lines = lines(r);
		parse(lines);
	}

	public Parser(String text) throws IOException {
		this(new StringReader(text));
	}

	private List<String> lines(Reader r) throws IOException {
		String s = null;
		List<String> l = new LinkedList<String>();
		BufferedReader br = new BufferedReader(r);
		while ((s = br.readLine()) != null) {
			l.add(s);
		}
		return l;
	}

	private void parse(List<String> lines) {
		Pattern reLabel = Pattern.compile("\\s*.*:");
		Pattern reComment = Pattern.compile("//.*");

		commands = new Command[lines.size()];
		int linesno = 0;
		for (String l : lines) {
			l = l.trim();
			if (l.isEmpty() || reComment.matcher(l).matches()) {
				commands[linesno] = new EmptyCommand(l);
			} else if (reLabel.matcher(l).matches()) {
				String c = l.trim().substring(0, l.length() - 1);
				labelmap.put(c, linesno + 1);
				commands[linesno] = new EmptyCommand("SPRUNG: " + l);
			} else {
				String cm[] = l.trim().split("\\s+", 0);
				COMMANDS_TABLE ct = COMMANDS_TABLE.valueOf(cm[0]);
				Command c;

				switch (ct) {
				case STORE:
					c = new Store(cm[1]);
					break;
				case LOAD:
					c = new Load(cm[1]);
					break;
				case ADD:
					c = new Add(cm[1]);
					break;
				case MULT:
					c = new Multi(cm[1]);
					break;
				case DIV:
					c = new Div(cm[1]);
					break;
				case SUB:
					c = new Sub(cm[1]);
					break;
				case END:
					c = new End();
					break;
				case JZERO:
					c = new JZero(labelmap, cm[1]);
					break;
				case GOTO:
					c = new Goto(labelmap, cm[1]);
					break;
				case PRINT:
					c = Print.SINGLETON;
					break;
				case INPUT:
					c = Input.SINGLETON;
					break;
				default:
					c = new EmptyCommand(l);
				}
				commands[linesno] = c;
			}
			linesno++;
		}
	}

	public Command[] getCommands() {
		for (Command c : commands) {
			System.err.println(c);
		}

		return commands;
	}

	static class ParserException extends Exception {
		private static final long serialVersionUID = -3103053045477715104L;
		private int line;

		public ParserException(Throwable e) {
			super(e);
		}

		public ParserException(String message, int line) {
			super(message);
			this.line = line;
		}

		public int getLine() {
			return line;
		}
	}
}
