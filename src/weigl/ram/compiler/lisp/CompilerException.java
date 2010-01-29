package weigl.ram.compiler.lisp;

public class CompilerException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public CompilerException() {
		super();

	}

	public CompilerException(String message, Throwable cause) {
		super(message, cause);

	}

	public CompilerException(String message) {
		super(message);

	}

	public CompilerException(Throwable cause) {
		super(cause);

	}

}
