package weigl.ram.compiler.lisprules;

import java.util.List;

import weigl.ram.commands.Command;
import weigl.ram.compiler.lisp.ExecutionContext;
import weigl.ram.compiler.lisp.LispList;

public class CommentRule extends TranslationRule{

	public CommentRule(){
		super("comment");
	}

	@Override
	public List<Command> visit(ExecutionContext ec, LispList ll) {
		return CommandFactory.create(ll.toString());
	}

}
