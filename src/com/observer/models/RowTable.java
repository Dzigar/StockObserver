package com.observer.models;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;

public class RowTable extends JTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<Integer, Color> rowColor;

	private List<Stock> stocks;

	public RowTable() {
		rowColor = new HashMap<Integer, Color>();
		stocks = new ArrayList<Stock>();
	}

	@Override
	public void setModel(javax.swing.table.TableModel dataModel) {
		super.setModel(dataModel);
	}

	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row,
			int column) {
		Component c = super.prepareRenderer(renderer, row, column);
		if (!isRowSelected(row)) {
			Color color = rowColor.get(row);
			c.setBackground(color == null ? getBackground() : color);
		}
		return c;
	}

	public List<Stock> getStocks() {
		return stocks;
	}

	public void addStock(int id, String stockSymbol) {
		stocks.add(new Stock(id, stockSymbol));
	}

	public void setRowColorDefault(int row) {
		try {
			rowColor.put(row, Color.WHITE);
			this.updateUI();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	public void setRowColorUp(int row) {
		rowColor.put(row, Color.CYAN);
		this.updateUI();
	}

	public void setRowColorDown(int row) {
		rowColor.put(row, Color.ORANGE);
		this.updateUI();
	}

	public void refresh(int row) {
		for (int i = 1; i < 4; i++) {
			switch (i) {
			case 1:
				this.getModel().setValueAt(stocks.get(row).getBid(), row, i);
				break;
			case 2:
				this.getModel().setValueAt(stocks.get(row).getAsk(), row, i);
				break;
			case 3:
				this.getModel().setValueAt(stocks.get(row).getPrice(), row, i);
				break;
			default:
				break;
			}
		}
		setCellRenderer();
	}

	public void setCellRenderer() {
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 1; i < getColumnCount(); i++) {
			this.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

	}

}