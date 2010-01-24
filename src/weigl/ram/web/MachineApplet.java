package weigl.ram.web;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import weigl.ram.Parser;
import weigl.ram.RAMachine;
import weigl.ram.view.ConsoleMachineListener;
import weigl.ram.view.ListenerEDTAdapter;
import weigl.ram.view.RegistersView;
import weigl.ram.view.WaitListener;

public class MachineApplet extends JApplet {
	private static final long serialVersionUID = 5731151288408915938L;

	public static int DELAY = 500;
	private static final String AUTO_START = "autostart";

	private JTabbedPane tabPane = new JTabbedPane(JTabbedPane.BOTTOM);
	private JTextArea txtEditor = new JTextArea();
	private EditorScrollPane pane = new EditorScrollPane(txtEditor);
	private JButton btn = new JButton("Run");

	@Override
	public void init() {
		setLayout(new BorderLayout());
		JPanel p = new JPanel();
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startMachine();
			}

		});

		p.setLayout(new BorderLayout());
		txtEditor.setText(getCodeFromParameter());

		p.add(pane, BorderLayout.CENTER);
		p.add(btn, BorderLayout.SOUTH);

		tabPane.addTab("Editor", p);
		add(tabPane);

		if (getOption(AUTO_START))
			startMachine();
	}

	private void startMachine() {
		Thread t = new Thread(new Start());
		t.start();
	}

	private boolean getOption(String name) {
		return Boolean.parseBoolean(getParameter(name));
	}

	private String getCodeFromParameter() {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < 1000; i++) {
			String s = getParameter("code" + i);
			if (s == null)
				break;
			sb.append(s).append('\n');
		}
		return sb.toString();
	}

	volatile int run = 0;

	class Start implements Runnable {
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
				rm = new RAMachine(new Parser(txtEditor.getText()).getCommands());
				
				tabPane.addTab("run-" + (run++), sp );
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
