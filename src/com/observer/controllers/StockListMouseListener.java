package com.observer.controllers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import com.observer.models.RowTable;

public class StockListMouseListener extends MouseAdapter {

	private RowTable table;

	public StockListMouseListener(RowTable table) {
		this.table = table;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int r = table.rowAtPoint(e.getPoint());
		if (r >= 0 && r < table.getRowCount()) {
			table.setRowSelectionInterval(r, r);
		} else {
			table.clearSelection();
		}

		int rowindex = table.getSelectedRow();
		if (rowindex < 0)
			return;
		if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
			JPopupMenu popup = new JPopupMenu();
			JMenuItem showChart = new JMenuItem("Show chart");
			showChart.addActionListener(new ShowChartMenuListener(table
					.getStocks().get(r).getSymbol()));
			popup.add(showChart);
			popup.show(e.getComponent(), e.getX(), e.getY());
		}
	}
}
