package weigl.ram.view;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import weigl.ram.RAMachine;
import weigl.ram.commands.AbstractCommand;
import weigl.ram.commands.Command;
import weigl.ram.listeners.MachineListener;

/**
 * Graphical overview of the execution of a program in an {@link RAMachine}
 * 
 * 
 * @author Alexander Weigl <alexweigl@gmail.com>
 * @date 10.11.2009
 * @version 1
 * 
 */
public class RegistersView extends JFrame implements MachineListener {
	private static final long serialVersionUID = -5905409223665793L;

	public static final int REGISTER_LINE = 10;

	static Font LBLFONT = new Font(Font.MONOSPACED, Font.PLAIN, 12);

	private TableRegisterModel tblRModel;
	private TableInstructionModel tblIModel;

	private JTable tblInstructions = createTable();
	private JTable tblRegister = createTable();

	private JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

	public RegistersView() {
		setLayout(new BorderLayout());

		sp.setRightComponent(new JScrollPane(tblRegister));
		sp.setLeftComponent(new JScrollPane(tblInstructions));

		sp.setDividerLocation(300);
		add(sp);
	}

	private JTable createTable() {
		JTable tbl = new JTable();
		tbl.getTableHeader().setReorderingAllowed(false);
//		tbl.getTableHeader().setResizingAllowed(false);
		tbl.setColumnSelectionAllowed(false);
		tbl.setRowSelectionAllowed(true);
		tbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbl.setShowGrid(false);
		return tbl;
	}

	public void nextInstruction(RAMachine aThis) {
		tblInstructions.changeSelection(aThis.getProgramCounter(), 0, false,
				false);
	}

	public void valueSet(RAMachine aThis, int position) {
		tblRModel.addWrite(position);
		tblRModel.fireRegisterChanged(position);
	}

	@Override
	public void valueRead(RAMachine machine, int position) {
		tblRModel.addRead(position);
	}

	@Override
	public void machineStart(RAMachine machine) {
		tblIModel = new TableInstructionModel(machine.getCommands());
		tblRModel = new TableRegisterModel(machine);

		tblRegister.setModel(tblRModel);
		tblRegister.setDefaultRenderer(Integer.class, tblRModel);
		
		tblInstructions.setModel(tblIModel);
		tblInstructions.getColumnModel().getColumn(0).setWidth(20);
		tblInstructions.getColumnModel().getColumn(2).setWidth(20);
		invalidate();
	}

	@Override
	public void machineStop(RAMachine machine) {
		Component c = SwingUtilities.getRoot(this);
		if (!(c instanceof Applet)) {
			setVisible(false);
			dispose();
		}
	}

	@Override
	public void instructionEnd(RAMachine machine) {

	}

}

class TableRegisterModel extends DefaultTableModel implements TableCellRenderer {
	private static final long serialVersionUID = 2293196592585322598L;
	private static final int MAX_HISTORY = 10;
	private RAMachine machine;

	private static Color[] CL_READS = clarray(MAX_HISTORY, Color.LIGHT_GRAY);
	private static Color[] CL_WRITES = clarray(MAX_HISTORY, Color.orange);

	private Queue<Integer> lastsReads = new LinkedList<Integer>();
	private Queue<Integer> lastsWrites = new LinkedList<Integer>();

	public TableRegisterModel(RAMachine machine) {
		this.machine = machine;
	}

	private static Color[] clarray(int length, Color c) {
		Color[] cl = new Color[length];
		cl[0] = c;
		for (int i = 1; i < cl.length; i++)
			cl[i] = cl[i - 1].brighter().brighter();
		return cl;
	}

	public void addRead(int position) {
		lastsReads.add(position);
		if (lastsReads.size() > MAX_HISTORY)
			lastsReads.poll();
	}

	public void addWrite(int position) {
		lastsWrites.add(position);
		if (lastsWrites.size() > MAX_HISTORY)
			lastsWrites.poll();

	}

	public void fireRegisterChanged(int p) {
		super.fireTableCellUpdated(p / 10, p % 10);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return Integer.class;
	}

	@Override
	public int getColumnCount() {
		return RegistersView.REGISTER_LINE;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return "" + columnIndex;
	}

	@Override
	public int getRowCount() {
		if (machine == null)
			return 0;
		return machine.getRegisters().size() / RegistersView.REGISTER_LINE;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return machine.getRegisters().get(position(columnIndex, rowIndex));
	}

	private int position(int columnIndex, int rowIndex) {
		return columnIndex + RegistersView.REGISTER_LINE * rowIndex;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		int i = (Integer) aValue;
		machine.getRegisters().set(position(column, row), i);
	}

	JLabel lblCell = new JLabel();

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		int p = position(column, row);

		lblCell.setText(String.format("%3d", value));
		lblCell.setHorizontalTextPosition(JLabel.RIGHT);

		lblCell.setOpaque(true);
		lblCell.setBackground(Color.WHITE);
		lblCell.setFont(RegistersView.LBLFONT);

		int x = 0;
		for (Integer i : lastsReads) {
			if (p == i) {
				lblCell.setBackground(CL_READS[x]);
				break;
			}
			x++;
		}

		x = 0;
		for (Integer i : lastsWrites) {
			if (p == i) {
				lblCell.setBackground(CL_WRITES[x]);
				break;
			}
			x++;
		}

		return lblCell;
	}
}

class TableInstructionModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	private Command[] commands;

	private String[] HEADER = { "  #", "Instruction", "Register" };

	public TableInstructionModel(Command[] c) {
		this.commands = c;
	}

	@Override
	public int getColumnCount() {
		return HEADER.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return HEADER[columnIndex];
	}

	@Override
	public int getRowCount() {
		if (commands == null)
			return 0;
		return commands.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.format("%2d", rowIndex);
		case 1:
			return commands[rowIndex].toString();
		case 2:
			if (commands[rowIndex] instanceof AbstractCommand) {
				AbstractCommand c = (AbstractCommand) commands[rowIndex];
				return c.getOperand();
			}
		default:
			return "";
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
}