package weigl.ram;

public class ExecutionOptions {
	/**
	 * logorithmic costs
	 */
	private boolean logCosts = false;
	/**
	 * uniform costs
	 */
	private boolean uniformCosts = false;
	/**
	 * start gui view
	 */
	private boolean guiView = false;
	/**
	 * start console logging
	 */
	private boolean consoleLogging = false;
	/**
	 * wait after each command
	 */
	private boolean waitAfterEachCommand = false;
	/**
	 * use the brainfuck compiler
	 */
	private boolean bfCompiler = false;

	/**
	 * 
	 */
	private boolean lispCompiler = false;
	/**
	 * 
	 */
	private boolean checkRegisters = false;

	/**
	 * input source
	 */
	private String inputFile;

	/**
	 * filename for register input
	 */
	private String registerFile;
	private int waitTime;

	public ExecutionOptions(String inputFile) {
		this.inputFile = inputFile;
	}

	public ExecutionOptions() {
	}

	public final boolean isLogCosts() {
		return logCosts;
	}

	public final void setLogCosts(boolean logCosts) {
		this.logCosts = logCosts;
	}

	public final boolean isUniformCosts() {
		return uniformCosts;
	}

	public final void setUniformCosts(boolean uniformCosts) {
		this.uniformCosts = uniformCosts;
	}

	public final boolean isGuiView() {
		return guiView;
	}

	public final void setGuiView(boolean guiView) {
		this.guiView = guiView;
	}

	public final boolean isConsoleLogging() {
		return consoleLogging;
	}

	public final void setConsoleLogging(boolean consoleLogging) {
		this.consoleLogging = consoleLogging;
	}

	public final boolean isWaitAfterEachCommand() {
		return waitAfterEachCommand;
	}

	public final void setWaitAfterEachCommand(boolean waitAfterEachCommand) {
		this.waitAfterEachCommand = waitAfterEachCommand;
	}

	public final boolean isBfCompiler() {
		return bfCompiler;
	}

	public final void setBfCompiler(boolean bfCompiler) {
		this.bfCompiler = bfCompiler;
	}

	public final boolean isLispCompiler() {
		return lispCompiler;
	}

	public final void setLispCompiler(boolean lispCompiler) {
		this.lispCompiler = lispCompiler;
	}

	public final boolean isCheckRegisters() {
		return checkRegisters;
	}

	public final void setCheckRegisters(boolean checkRegisters) {
		this.checkRegisters = checkRegisters;
	}

	public final String getInputFile() {
		return inputFile;
	}

	public final void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public final String getRegisterFile() {
		return registerFile;
	}

	public final void setRegisterFile(String registerFile) {
		this.registerFile = registerFile;
	}

	public int getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(int time) {
		waitTime = time;
	}
}
