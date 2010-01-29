package weigl.ram.compiler;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import weigl.ram.RAMachine;
import weigl.ram.commands.AbstractCommand;
import weigl.ram.commands.Add;
import weigl.ram.commands.Command;
import weigl.ram.commands.Goto;
import weigl.ram.commands.Input;
import weigl.ram.commands.JZero;
import weigl.ram.commands.Load;
import weigl.ram.commands.Store;
import weigl.ram.commands.Sub;

/**
 * <pre>
 * IDEA:
 * we reserve the:
 *         1st register for the pointer
 *         2st for value
 * </pre>
 * 
 * @author Alexander Weigl <alexweigl@gmail.com>
 * 
 */
public class BrainFuckCompiler {
	private char[] chars;

	private List<Command> list = new LinkedList<Command>();

	private Map<String, Integer> map = new HashMap<String, Integer>();
	private Stack<Integer> loopStack = new Stack<Integer>();

	private Stack<Goto> loopStackGoto = new Stack<Goto>();

	public BrainFuckCompiler(String brainFuck) {
		chars = brainFuck.toCharArray();
	}

	public BrainFuckCompiler(Reader r) throws IOException {
		StringBuilder sb = new StringBuilder();
		char[] buf=new char[1024];
		while(r.read(buf)>0)
			sb.append(buf);
		chars=sb.toString().toCharArray();
	}

	public Command[] parse() {
		list.add(new Load("#10"));
		list.add(new Store("#1"));

		for (int i = 0; i < chars.length; i++) {
			char cur = chars[i];
			interpret(cur);
		}
		return list.toArray(new Command[] {});
	}

	private void interpret(char cur) {
		switch (cur) {
		case '+':
			load_1();
			plus();
			store();
			break;
		case '-':
			load_1();
			minus();
			store();
			break;
		case '<':
			load_1();
			pminus();
			pstore();
			break;
		case '>':
			load_1();
			pplus();
			pstore();
			break;
		case '.':
			list.add(new Load("*1"));
			list.add(new BFOutput());
			break;
		case ',':
			list.add(new Input());
			list.add(new Store("*1"));
			break;
		case '[':
			Goto g = new Goto(map, "");
			list.add(g);
			loopStack.push(list.size());
			loopStackGoto.push(g);
			break;
		case ']':
			Goto go = loopStackGoto.pop();
			int p = list.indexOf(go);
			JZero jz = new JZero(map, "#" + (1+p));
			list.add(jz);
			go.setOperand("#" + list.indexOf(jz));
			break;
		}
	}

	private void store() {
		list.add(new Store("1"));
	}

	private void pstore()
	{
		list.add(new Store("#1"));
	}
	
	private void pplus() {
		list.add(new Add("1"));
		
	}

	private void pminus() {
		list.add(new Sub("1"));
	}

	private void minus() {
		list.add(new Sub("*1"));
	}

	private void plus() {
		list.add(new Add("*1"));
	}

	private void load_1() {
		list.add(new Load("#1"));
	}
}

class BFOutput extends AbstractCommand{
	public BFOutput() {
		super("0", null);
	}

	@Override
	public void exec(RAMachine machine) {
		System.out.print((char)machine.rget(getOperand()));
	}
}