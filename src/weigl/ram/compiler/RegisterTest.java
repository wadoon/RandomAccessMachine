package weigl.ram.compiler;

import java.util.Map;

import java.util.Map.Entry;

import weigl.ram.RAMachine;
import weigl.ram.Registers;
import weigl.ram.listeners.MachineAdapter;

public class RegisterTest extends MachineAdapter {

	private Map<Integer, Integer> registerMap;

	public RegisterTest(Map<Integer, Integer> map) {
		this.registerMap = map;
	}

	@Override
	public void machineStop(RAMachine machine) 
	{
		Registers r = machine.getRegisters();
		boolean error = false;
		for (Entry<Integer, Integer> e: registerMap.entrySet()) 
		{
			if( r.get(e.getKey()) == e.getValue() )
			{
				System.out.println("Position " + e.getKey() + " ... ok");
			}
			else
			{
				System.err.println("Position " + e.getKey() + " ... wrong");
				error = true;
			}
		}
		if(error)
		{
			System.err.println("Register mismatch");
			throw new RuntimeException("Register mismatch");
		}
		else
			System.out.println("Register match");
	}
}
