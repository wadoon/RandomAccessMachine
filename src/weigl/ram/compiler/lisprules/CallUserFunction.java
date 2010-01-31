package weigl.ram.compiler.lisprules;

import java.util.LinkedList;
import java.util.List;

import weigl.ram.commands.Command;
import weigl.ram.compiler.lisp.ExecutionContext;
import weigl.ram.compiler.lisp.LispFunction;
import weigl.ram.compiler.lisp.LispList;

public class CallUserFunction {
	public List<Command> visit(ExecutionContext ec, LispList caller,  LispFunction method ) {
		LispList body = method.getBody();
		String[] params = method.getParam();
		List<Command> list = new LinkedList<Command>();

		//create a new scope
		ExecutionContext oldec = ec.pushNames();
		//load vars
		for (int i = 1; i < caller.getElements().size(); i++) 
		{
			int r = ec.defineVariable(params[i-1]);
			list.addAll(TranslationRule.valueTo(oldec, caller, i, r));
		}
		
		ec.getTranslator().translate(ec, list, body);		
		
		ec.popNames(); //throw scope to rubbish
		return list;
	}
}
