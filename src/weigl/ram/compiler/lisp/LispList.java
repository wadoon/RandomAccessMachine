package weigl.ram.compiler.lisp;

import java.util.LinkedList;
import java.util.List;

import weigl.ram.commands.Command;

/**
 * 
 * @author Alexander Weigl <alexweigl@gmail.com>
 */
public class LispList implements LispType {
	List<LispType> elements = new LinkedList<LispType>();

	public boolean add(LispType e) {
		return elements.add(e);
	}

	public LispType remove(int index) {
		return elements.remove(index);
	}

	public void visit(ExecutionContext ec, List<Command> commands) {}

	public LispType get(int i)
	{
		return elements.get(i);	
	}
	
	@Override
	public String toString() {
		return "(" + elements + ")";
	}

	public List<LispType> getElements() {
		return elements;
	}
}
