package weigl.ram.compiler.lisp;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import weigl.ram.compiler.LispCompiler;
import weigl.ram.compiler.lisprules.Translator;

/**
 * Class holds the that is needed during compilation
 * 
 * @author Alexander Weigl <alexweigl@gmail.com>
 * 
 */
public class ExecutionContext {
	/**
	 * size of the area for temporary objects
	 */
	public static int STACK_SZ = 100;

	/**
	 * maximal position for known variables
	 */
	public static int MAX_POS = 100;

	/**
	 * minimal position for known variables
	 */
	public static int MIN_POS = 10;

	private Map<String, Integer> varMap = new HashMap<String, Integer>();
	private boolean[] varAreaMap = new boolean[MAX_POS - MIN_POS];
	private int stackOffset = MAX_POS + 1;
	private boolean[] stackMap = new boolean[STACK_SZ];
	private Stack<Map<String, Integer>> scopeStack = new Stack<Map<String, Integer>>();

	private LispCompiler compiler;

	public ExecutionContext(LispCompiler c) {
		this.compiler = c;
	}

	public ExecutionContext(LispCompiler compiler,
			Map<String, Integer> varMap,
			boolean[] varAreaMap,
			boolean[] stackMap, 
			int stackOffset) 
	{
		this.varMap = new HashMap<String, Integer>(varMap);
		this.varAreaMap = varAreaMap;
		this.stackMap = stackMap;
		this.stackOffset=stackOffset;
		this.compiler = compiler;
	}

	/**
	 * set execution options
	 * 
	 * @see Option
	 * @param s
	 * @param value
	 */
	public void setOption(String s, int value) {
		Option o = Option.valueOf(s);
		switch (o) {
		case STACK_SIZE:
			STACK_SZ = value;
			break;
		case MAX_STACK:
			MAX_POS = value;
		case MIN_STACK:
			MIN_POS = value;
		}
	}

	/**
	 * get register for the variable
	 * 
	 * @param varname
	 *            - the name of the variable
	 * @return the absolute register
	 */
	public int getFieldPosition(String varname) {
		if (!varMap.containsKey(varname)) {
			System.err.println(varname + "  was not defined, define it now!");
			defineVariable(varname);
		}
		return varMap.get(varname);
	}

	/**
	 * 
	 * @param varname
	 * @param register
	 * @return
	 */
	public int bindField(String varname, int register) {
		varMap.put(varname, register);
		return register;
	}

	/**
	 * register the variable. a register will be reserved for this variable
	 * 
	 * @param varname
	 * @return absolute register position
	 */
	public int defineVariable(String varname) {
		int i = findFree();
		return bindField(varname, i);
	}

	/**
	 * remove the register that is hold by this variable
	 * 
	 * @param varname
	 */
	public void releaseVariable(String varname) {
		int p = getFieldPosition(varname);
		varMap.remove(varname);
		varAreaMap[p - MIN_POS] = false;
	}

	private int findFree() {
		for (int i = 0; i < varAreaMap.length; i++) {
			if (!varAreaMap[i]) {
				varAreaMap[i] = true;
				return i + MIN_POS;
			}
		}
		throw new CompilerException(
				"Variable space is overflowed into stack area. please reduce the amount of variables");
	}

	/**
	 * return a free stack register
	 * 
	 * @return
	 */
	public int reserve() {
		for (int i = 0; i < stackMap.length; i++) {
			if (!stackMap[i]) {
				stackMap[i] = true;
				return i + stackOffset;
			}
		}
		return -1;
	}

	/**
	 * release a stack register
	 * 
	 * @param i
	 */
	public void free(int i) {
		i -= stackOffset;
		stackMap[i] = false;
	}

	/**
	 * release the stack registers
	 * 
	 * @param pos
	 */
	public void free(int... pos) {
		for (int i : pos)
			free(i);
	}

	public LispCompiler getCompiler() {
		return compiler;
	}

	public Translator getTranslator() {
		return getCompiler().getTranslator();
	}

	public ExecutionContext pushNames() {
		scopeStack.push(varMap);
		varMap = new HashMap<String, Integer>();
		return new ExecutionContext(getCompiler(), scopeStack.peek(), varAreaMap, this.stackMap, stackOffset);
	}

	public void popNames() {
		for (Integer i : varMap.values()) 
			varAreaMap[i - MIN_POS] = false;
		varMap = scopeStack.pop();
	}
}

enum Option {
	STACK_SIZE, MIN_STACK, MAX_STACK;
}