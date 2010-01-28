package weigl.ram.compiler.lisp;

import java.util.List;

import weigl.ram.commands.Command;

public class Atom implements LispType {
	public final String TEXT;

	public Atom(String text) {
		TEXT = text;
	}

	@Override
	public String toString() {
		return "[A:" + TEXT + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((TEXT == null) ? 0 : TEXT.hashCode());
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
		Atom other = (Atom) obj;
		if (TEXT == null) {
			if (other.TEXT != null)
				return false;
		} else if (!TEXT.equals(other.TEXT))
			return false;
		return true;
	}

	@Override
	public void visit(ExecutionContext ec, List<Command> commands) {
	}
}
