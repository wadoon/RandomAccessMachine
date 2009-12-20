package weigl.ram;


/**
 * All commands as an constant.
 *
 *
 * @author Alexander Weigl <alexweigl@gmail.com>
 * @date   10.11.2009
 * @version 1
 *
 */
public enum COMMANDS_TABLE {
	STORE, LOAD, ADD, MULT, DIV, SUB, END, JZERO, GOTO, PRINT, INPUT, EMPTY;

	public static String[] valueList() {
		String[] s = new String[values().length];
		for (int i = 0; i < values().length; i++)
			s[i] = values()[i].name();
		return s;
	}
}