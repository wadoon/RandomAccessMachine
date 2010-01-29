package weigl.ram.commands.ext;

import java.util.Map;

import weigl.ram.COMMANDS_TABLE;
import weigl.ram.RAMachine;
import weigl.ram.commands.AbstractCommand;

public class ExtendedCommands {
	public static class Add extends weigl.ram.commands.Add {
		public Add(String p) {
			super(p);

		}
	}

	public static class Div extends weigl.ram.commands.Div {

		public Div(String p) {
			super(p);

		}
	}

	public static class End extends weigl.ram.commands.End {
	}

	public static class Goto extends weigl.ram.commands.Goto {
		public Goto(Map<String, Integer> jumpTable, String p) {
			super(jumpTable, p);
		}
	}

	public static class Input extends weigl.ram.commands.Input {
	}

	public static class JZero extends weigl.ram.commands.JZero {
		public JZero(Map<String, Integer> jumpTable, String p) {
			super(jumpTable, p);

		}
	}


	public static class LT extends AbstractCommand {
		public LT(String p) {
			super(p,COMMANDS_TABLE.LT);
		}
		
		//> 6 5
		// c(0) = 6 
		public void exec(RAMachine machine) 
		{
			int r1= machine.rget(0);
			int r2 = machine.rget(getOperand());
			machine.rset(0, r1<r2?1:0);
			machine.nextInstruction();
		}
	}
	
	public static class GT extends AbstractCommand {
		public GT(String p) {
			super(p,COMMANDS_TABLE.GT);
		}
		
		//> 6 5
		// c(0) = 6 
		public void exec(RAMachine machine) 
		{
			int r1= machine.rget(0);
			int r2 = machine.rget(getOperand());
			machine.rset(0, r1>r2?1:0);
			machine.nextInstruction();
		}
	}
	
	public static class Load extends weigl.ram.commands.Load {
		public Load(String string) {
			super(string);

		}
	}

	public static class Multi extends weigl.ram.commands.Multi {
		public Multi(String p) {
			super(p);

		}
	}

	public static class Print extends weigl.ram.commands.Print {
	}

	public static class Store extends weigl.ram.commands.Store {
		public Store(String p) {
			super(p);

		}
	}

	public static class Sub extends AbstractCommand {
		public Sub(String p) {
			super(p,COMMANDS_TABLE.ESUB);
		}

		@Override
		public void exec(RAMachine machine) {
			int i = machine.rget(0) - machine.rget(operand);
			machine.rset(0, i);
			machine.nextInstruction();
		}
		
	}
}
