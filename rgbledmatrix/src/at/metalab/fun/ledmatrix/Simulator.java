package at.metalab.fun.ledmatrix;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

/**
 * RGB-LED-Matrix simulation using Swing.
 * Beware, here be dragons!
 * @author m68k
 *
 */
public class Simulator implements Closeable {

	private static Logger LOG = Logger.getLogger(Simulator.class
			.getCanonicalName());

	private JFrame jFrame = null;
	private JTable leds = null;
	private LedTableModel ledTableModel = new LedTableModel();

	private int frameNumber = 0;

	public Simulator() {
		setup();
	}
	
	private class LedTableModel implements TableModel {
		private Pixel[][] frame;

		public void setFrame(Pixel[][] frame) {
			this.frame = frame;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		}

		@Override
		public void removeTableModelListener(TableModelListener l) {
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return frame[columnIndex][rowIndex];
		}

		@Override
		public int getRowCount() {
			return 24;
		}

		@Override
		public String getColumnName(int columnIndex) {
			return String.valueOf(columnIndex);
		}

		@Override
		public int getColumnCount() {
			return 24;
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return Pixel.class;
		}

		@Override
		public void addTableModelListener(TableModelListener l) {
		}
	}

	public void display(final Pixel[][] frame) throws IOException {
		frameNumber++;

		if (jFrame == null) {
			setup();
		}

		ledTableModel.setFrame(frame);
		jFrame.setTitle(getTitle());
		leds.repaint();

		try {
			Thread.sleep(20);
		} catch (InterruptedException interruptedException) {
		}
	}

	private static class LedMatrixCellRenderer extends DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Component c = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);
			Pixel pixel = (Pixel) value;
			c.setBackground(new Color(pixel.r, pixel.g, pixel.b));
			return c;
		}
	}

	public final static TableCellRenderer LED_MATRIX_CELL_RENDERER = new LedMatrixCellRenderer();

	private void setup() {
		LOG.info("setup");
		JFrame jFrame = new JFrame(getTitle());
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		JPanel jPanel = new JPanel();
		jFrame.add(jPanel);

		JTable leds = new JTable(24, 24);
		leds.setDefaultRenderer(Pixel.class, LED_MATRIX_CELL_RENDERER);
		leds.setPreferredSize(new Dimension(500, 500));

		jPanel.add(leds);

		this.jFrame = jFrame;
		this.leds = leds;
		this.ledTableModel = new LedTableModel();
		leds.setModel(ledTableModel);

		jFrame.pack();
		jFrame.setVisible(true);
		jFrame.setLocationRelativeTo(null);
	}

	private String getTitle() {
		return String.format("jFnord RGB LedMatrix [Frame #%d]", frameNumber);
	}

	@Override
	public void close() {
		if (jFrame != null) {
			jFrame.dispose();
		}
	}

}
