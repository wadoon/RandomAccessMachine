package weigl.ram.web;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Formatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JWindow;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

import javax.swing.text.Highlighter.HighlightPainter;

import weigl.ram.COMMANDS_TABLE;

public class EditorScrollPane extends JScrollPane implements DocumentListener,
		KeyListener {

	private static final long serialVersionUID = -1856478938455202466L;

	private static final String[] KEYWORDS = COMMANDS_TABLE.valueList();

	protected Box m_linePanel = new Box(BoxLayout.Y_AXIS);
	protected JTextArea m_textComponent;
	protected Font m_currentFont;

	private DefaultHighlighter dh = new DefaultHighlighter();

	private DefaultHighlighter.DefaultHighlightPainter POINTER = new DefaultHighlighter.DefaultHighlightPainter(
			Color.lightGray);
	private DefaultHighlighter.DefaultHighlightPainter CONSTANT = new DefaultHighlighter.DefaultHighlightPainter(
			new Color(0xFF, 0xCC, 0xCC));
	private DefaultHighlighter.DefaultHighlightPainter OPCODE = new DefaultHighlighter.DefaultHighlightPainter(
			Color.ORANGE);
	private DefaultHighlighter.DefaultHighlightPainter COMMENT = new DefaultHighlighter.DefaultHighlightPainter(
			Color.GREEN.brighter().brighter().brighter().brighter());

	public EditorScrollPane(JTextArea component) {
		m_textComponent = component;
		m_currentFont = component.getFont();

		m_linePanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

		setViewportView(m_textComponent);
		setRowHeaderView(m_linePanel);
		component.getDocument().addDocumentListener(this);

		m_textComponent.setHighlighter(dh);

		m_textComponent.addKeyListener(this);

		for (int j = 1; j < 500; j++)
			m_linePanel.add(createLabel(j));

		update();
	}

	public void changedUpdate(DocumentEvent e) {
		update();
	}

	public void insertUpdate(DocumentEvent e) {
		update();
	}

	public void removeUpdate(DocumentEvent e) {
		update();
	}

	public void update() {

		updateHighlighter();

	}

	private void updateHighlighter() {
		dh.removeAllHighlights();
		hightlightWords(KEYWORDS, OPCODE);
		hightlightWords("([#][0-9]*)", CONSTANT);
		hightlightWords("(\\*[0-9]*)", POINTER);
		hightlightWords("^\\s*//.*$", COMMENT);
	}

	private void hightlightWords(String[] words, HighlightPainter hlcolor) {
		hightlightWords("(" + EditorScrollPane.join(words, "|") + ")", hlcolor);
	}

	private void hightlightWords(String regex, HighlightPainter hlcolor) {
		Pattern p = Pattern.compile(regex, Pattern.UNICODE_CASE
				| Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(m_textComponent.getText());

		while (m.find()) {
			try {
				dh.addHighlight(m.start(), m.end(), hlcolor);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}

	private Component createLabel(int i) {
		Formatter f = new Formatter((Appendable) null).format("%03d", i);
		JLabel lbl = new JLabel(f.out().toString());
		lbl.setFont(m_currentFont);
		return lbl;
	}

	public void keyPressed(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {
		// System.out.println("Char: " + e.getKeyChar() +","+e.getModifiers());
		if (e.getKeyChar() == ' '
				&& (e.getModifiers() & KeyEvent.CTRL_MASK) > 0) {
			final JWindow m_toolTip = new JWindow();
			m_toolTip.setLayout(new BorderLayout());
			JList list = new JList(KEYWORDS);
			m_toolTip.add(list);
			m_toolTip.pack();
			System.out.println("EditorScrollPane.keyTyped()trigger");
			Point loc = m_textComponent.getCaret().getMagicCaretPosition();
			loc.translate(m_textComponent.getLocationOnScreen().x,
					m_textComponent.getLocationOnScreen().y);
			m_toolTip.setLocation(loc);
			m_toolTip.setVisible(true);
		}
	}

	public static String join(String[] words, String sep) {
		StringBuilder sb = new StringBuilder();

		int i = 0;
		for (; i < words.length - 1; i++)
			sb.append(words[i]).append(sep);
		sb.append(words[words.length - 1]);

		return sb.toString();
	}

}