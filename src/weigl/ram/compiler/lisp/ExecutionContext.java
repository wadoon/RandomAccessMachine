package weigl.ram.compiler.lisp;

import java.util.HashMap;

/**
 * Class holds the that is needed during compilation 
 * @author Alexander Weigl <alexweigl@gmail.com>
 *
 */
public class ExecutionContext {
	/**
	 * size of the area for temporary objects
	 */
	public static final int STACK_SZ = 100;

	/**
	 * maximal position for known variables
	 */
	public static int MAX_POS = 100;

	/**
	 * minimal position for known variables
	 */
	public static int MIN_POS = 10;

	private HashMap<String, Integer> varMap = new HashMap<String, Integer>();
	private boolean[] varAreaMap = new boolean[MAX_POS - MIN_POS];
	private int stackOffset = MAX_POS + 1;
	private boolean[] stackMap = new boolean[STACK_SZ];

	public ExecutionContext() {}

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
			return -1;
		}
		return varMap.get(varname);
	}

	/**
	 * register the variable. a register will be reserved for this variable
	 * 
	 * @param varname
	 * @return absolute register position
	 */
	public int defineVariable(String varname) {
		int i = findFree();
		varMap.put(varname, i);
		return i;
	}

	/**
	 * remove the register that is hold by this variable
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
	 * @param i
	 */
	public void free(int i) {
		i -= stackOffset;
		stackMap[i] = false;
	}

	/**
	 * release the stack registers
	 * @param pos
	 */
	public void free(int... pos) {
		for (int i : pos)
			free(i);
	}
}
