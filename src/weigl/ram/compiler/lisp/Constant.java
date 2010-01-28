package weigl.ram.compiler.lisp;

import java.util.List;

import weigl.ram.commands.Command;

public class Constant implements LispType {
	public final int VALUE;

	public Constant(int value) {
		VALUE = value;
	}

	public Constant(String integer) {
		this(Integer.parseInt(integer));
	}

	@Override
	public String toString() {
		return "[C:" + VALUE + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + VALUE;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Constant other = (Constant) obj;
		if (VALUE != other.VALUE)
			return false;
		return true;
	}

	@Override
	public void visit(ExecutionContext ec, List<Command> commands) {
	}

}
