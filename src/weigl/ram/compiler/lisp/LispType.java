package weigl.ram.compiler.lisp;

import java.util.List;

import weigl.ram.commands.Command;

/**
 * 
 * @author Alexander Weigl <alexweigl@gmail.com>
 */
public interface LispType {
	public void visit(ExecutionContext ec, List<Command> commands);
}



