package weigl.ram.compiler.lisp;

import java.util.HashMap;

public class ExecutionContext {
	private static final int STACK_SZ = 100;

	private HashMap<String, Integer> varMap = new HashMap<String, Integer>();

	public static int MAX_POS = 100;
	public static int MIN_POS = 10;

	private int counter;

	private int stackOffset = MAX_POS + 1;
	private boolean[] stackMap = new boolean[STACK_SZ];

	public ExecutionContext() {
		counter = MIN_POS;
	}

	public int getFieldPosition(String varname) {
		if (!varMap.containsKey(varname)) {
			System.err.println(varname + "  was not defined, define it now!");
			return -1;
		}
		return varMap.get(varname);
	}

	public int defineVariable(String varname) {
		int i = findFree();
		varMap.put(varname, i);
		return i;
	}

	private int findFree() {
		return ++counter;
	}

	public int reserve() {
		for (int i = 0; i < stackMap.length; i++) {
			if (!stackMap[i]) {
				stackMap[i] = true;
				return i + stackOffset;
			}
		}
		return -1;
	}

	public void free(int i) {
		i -= stackOffset;
		stackMap[i] = false;
	}

	public void free(int... pos) {
		for (int i : pos) free(i);
	}
}
