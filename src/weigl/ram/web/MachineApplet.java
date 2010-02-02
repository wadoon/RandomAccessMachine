package weigl.ram.web;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import weigl.gui.editor.EditorScrollPane;
import weigl.gui.editor.EditorScrollPane.Highlight;
import weigl.ram.COMMANDS_TABLE;
import weigl.ram.Parser;
import weigl.ram.RAMachine;
import weigl.ram.commands.Command;
import weigl.ram.compiler.LispCompiler;
import weigl.ram.compiler.lisp.Atom;
import weigl.ram.view.ConsoleMachineListener;
import weigl.ram.view.ListenerEDTAdapter;
import weigl.ram.view.RegistersView;
import weigl.ram.view.WaitListener;

public class MachineApplet extends JApplet {
	private static final long serialVersionUID = 5731151288408915938L;

	public static int DELAY = 500;
//	private static final String AUTO_START = "autostart";

	private JTabbedPane tabPane = new JTabbedPane(JTabbedPane.BOTTOM);
	private JEditorPane txtASMEditor = new JEditorPane();
	private EditorScrollPane pane = new EditorScrollPane(txtASMEditor);

	private JEditorPane txtLispPane = new JEditorPane();
	private EditorScrollPane lispPane = new EditorScrollPane(txtLispPane);

	@Override
	public void init() {
		setLayout(new BorderLayout());

		pane.setKeywords(COMMANDS_TABLE.valueList());
		pane.addHighlight(new Highlight(Color.ORANGE, COMMANDS_TABLE
				.valueList()));
		pane.addHighlight("[#][0-9]*", new Color(0xFF, 0xCC, 0xCC));
		pane.addHighlight("\\*[0-9]*", Color.LIGHT_GRAY);
		pane.addHighlight("^\\s*//.*$", Color.green.brighter().brighter()
				.brighter().brighter());

		JPanel p = new JPanel();
		JButton btn = new JButton("Run");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Command[] c;
				try {
					c = new Parser(txtASMEditor.getText()).getCommands();
					startMachine(c, "asm");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(MachineApplet.this, e1
							.getMessage());
				}
			}
		});

		p.setLayout(new BorderLayout());
		txtASMEditor.setText(getCodeFromParameter("asm"));

		p.add(pane, BorderLayout.CENTER);
		p.add(btn, BorderLayout.SOUTH);

		tabPane.addTab("ASM", p);
		add(tabPane);

		/*********************************************************************************/
		LispCompiler lc = new LispCompiler();
		Set<Atom> rules = lc.getRules();
		String[] keywords = new String[rules.size()];
		Iterator<Atom> iter = rules.iterator();
		for (int i = 0; i < keywords.length; i++)
			keywords[i] = '(' + iter.next().TEXT;

		lispPane.setKeywordDelimiters("() \n");
		lispPane.setKeywords(keywords);

		lispPane.addHighlight("\\(|\\)", Color.green);
		lispPane.addHighlight("(?<=\\()\\w*", Color.yellow);
		lispPane.addHighlight("\\d+", Color.PINK);

		p = new JPanel();
		btn = new JButton("Run");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LispCompiler lc = new LispCompiler();
				Command[] c = (Command[]) lc.compile(txtLispPane.getText())
						.toArray(new Command[] {});
				startMachine(c, "lisp");
			}
		});

		p.setLayout(new BorderLayout());
		txtLispPane.setText(getCodeFromParameter("lisp"));

		p.add(lispPane, BorderLayout.CENTER);
		p.add(btn, BorderLayout.SOUTH);

		tabPane.addTab("Lisp", p);
		add(tabPane);

		// if (getOption(AUTO_START))
		// startMachine();
	}

	private void startMachine(Command[] c, String string) {
		Start target = new Start(c, string);
		new Thread(target).start();
	}

//	private boolean getOption(String name) {
//		return Boolean.parseBoolean(getParameter(name));
//	}

	private String getCodeFromParameter(String prefix) {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < 1000; i++) {
			String s = getParameter(prefix + i);
			if (s == null)
				break;
			sb.append(s).append('\n');
		}
		return sb.toString();
	}

	volatile int run = 0;

	class Start implements Runnable {
		final Command[] commands;
		final String name;

		public Start(Command[] commands, String name) {
			this.commands = commands;
			this.name = name;
		}

		public void run() {
			JTextArea txtLog = new JTextArea();
			RegistersView rw = new RegistersView();
			rw.pack();
			JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			sp.setDividerLocation(.5);

			sp.setRightComponent(rw.getRootPane());
			sp.setLeftComponent(new JScrollPane(txtLog));

			RAMachine rm;
			try {
				rm = new RAMachine(commands, 250);

				tabPane.addTab("run-" + name + "-" + (run++), sp);
				tabPane.setSelectedIndex(1);

				rm.addListener(new ListenerEDTAdapter(
						new ConsoleMachineListener(createTextLog(txtLog))));
				rm.addListener(new WaitListener(DELAY));
				rm.addListener(new ListenerEDTAdapter(rw));
				rm.start();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(MachineApplet.this, e1
						.getMessage());
			}
		}

		private PrintWriter createTextLog(final JTextArea txt) {
			return new PrintWriter(new Writer() {

				@Override
				public void write(char[] cbuf, int off, int len)
						throws IOException {
					txt.append(new String(cbuf, off, len));
					txt.setCaretPosition(txt.getText().length());
				}

				@Override
				public void flush() throws IOException {

				}

				@Override
				public void close() throws IOException {

				}
			});
		}

	}
}
