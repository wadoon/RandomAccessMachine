package weigl.ram.compiler.lisptest;

import java.io.IOException;

import org.junit.Test;

import weigl.ram.compiler.LispCompiler;

public class LispCompilerTest {
	@Test public void run_while() throws IOException
	{
		LispCompiler.runTestCompiler("examples/while");
	}

	@Test public void run_iftest() throws IOException
	{
		LispCompiler.runTestCompiler("examples/iftest");
	}

	@Test public void run_functest() throws IOException
	{
		LispCompiler.runTestCompiler("examples/func");
	}
}
