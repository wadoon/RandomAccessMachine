package weigl.ram.compiler;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import weigl.ram.compiler.lisp.Atom;
import weigl.ram.compiler.lisp.Constant;
import weigl.ram.compiler.lisp.LispList;
import weigl.std.collection.Tuple2;

/**
 * 
 * @author Alexander Weigl <alexweigl@gmail.com>
 * 
 */
public class LispParser {
	private Stack<LispList> stack = new Stack<LispList>();
	private List<LispList> rootLists = new LinkedList<LispList>();

	public LispParser(final String source) {
		Tokenizer t = new Tokenizer(source);
		for (Tuple2<TOKEN_TYPE, String> token : t) {
			handle(token);
		}
	}

	private void handle(Tuple2<TOKEN_TYPE, String> token) {
		System.out.println(token);
		switch (token.get1()) {
		case LPAREN:
			LispList list = new LispList();
			if (stack.size() == 0)
				rootLists.add(list);
			else
				stack.peek().add(list);
			stack.push(list);
			break;
		case RPAREN:
			stack.pop();
			break;
		case ATOM:
			stack.peek().add(new Atom(token.get2()));
			break;
		case CONSTANT:
			stack.peek().add(new Constant(token.get2()));
			break;
		}
	}

	public List<LispList> getRootLists() {
		return Collections.unmodifiableList(rootLists);
	}
}

class Tokenizer implements Iterable<Tuple2<TOKEN_TYPE, String>> {
	static final String ID_CHARS = "[a-zA-Z+<>=-_.:,;!$%&/=]";
	final static Pattern tokens = TOKEN_TYPE.buildRegex();
	private LinkedList<Tuple2<TOKEN_TYPE, String>> tokenList;

	public Tokenizer(String src) {
		Matcher m = tokens.matcher(src);
		tokenList = new LinkedList<Tuple2<TOKEN_TYPE, String>>();

		TOKEN_TYPE[] tt = TOKEN_TYPE.values();

		while (m.find()) {
			TOKEN_TYPE t = null;
			String s = m.group();
			for (TOKEN_TYPE token : tt) {
				if (token.REGEX.matcher(s).matches()) {
					t = token;
					break;
				}
			}
			tokenList.add(Tuple2.create(t, s));
		}
	}

	@Override
	public Iterator<Tuple2<TOKEN_TYPE, String>> iterator() {
		return tokenList.iterator();
	}
}

enum TOKEN_TYPE {
	LPAREN("\\("), RPAREN("\\)"), CONSTANT("\\d+"), ATOM("([a-zA-Z\\*+-/=<>]+)");

	public final Pattern REGEX;

	TOKEN_TYPE(String re) {
		REGEX = Pattern.compile(re);
	}

	public static Pattern buildRegex() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		TOKEN_TYPE[] a = values();
		for (int i = 0; i < a.length - 1; i++)
			sb.append(a[i].REGEX.pattern()).append("|");
		sb.append(a[a.length - 1].REGEX.pattern());
		sb.append(")");
		return Pattern.compile(sb.toString());
	}
}