package com.observer.controllers;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.observer.models.RowTable;

public class StockListSelectionListener implements ListSelectionListener {

	private RowTable table;

	public StockListSelectionListener(RowTable table) {
		this.table = table;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		try {
			String selectedData = null;

			int[] selectedRow = table.getSelectedRows();
			int[] selectedColumns = table.getSelectedColumns();

			table.setRowSelectionInterval(0, 0);

			for (int i = 0; i < selectedRow.length; i++) {
				for (int j = 0; j < selectedColumns.length; j++) {
					selectedData = (String) table.getValueAt(selectedRow[i],
							selectedColumns[j]);
				}
			}
		} catch (Exception e2) {
			System.out.println(e2.getLocalizedMessage());
		}
	}
}
