package weigl.ram.compiler.lisp;


public class LispFunction {
	private String[] param;
	private LispList body;
	private String name;

	public static LispFunction create(String name, LispList body,
			String... params) {
		LispFunction lm = new LispFunction();
		lm.name = name;
		lm.body = body;
		lm.param = params;
		return lm;
	}

	public String getName() {
		return name;
	}

	public String[] getParam() {
		return param;
	}

	public LispList getBody() {
		return body;
	}
}
