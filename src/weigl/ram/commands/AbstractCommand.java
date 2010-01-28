package weigl.ram.commands;

import weigl.ram.COMMANDS_TABLE;
import weigl.ram.RAMachine;

public abstract class AbstractCommand implements Command {
	protected COMMANDS_TABLE type;
	protected String operand;

	public AbstractCommand(String op, COMMANDS_TABLE type) {
		operand = op;
		this.type = type;
	}

	@Override
	public abstract void exec(RAMachine machine);

	@Override
	public String repr() {
		return toString() + " " + getOperand();
	}

	@Override
	public final String toString()
    {
        return getType().toString();
    }

	public String getOperand() {
		return operand;
	}

	@Override
	public COMMANDS_TABLE getType() {
		return type;
	}

}
