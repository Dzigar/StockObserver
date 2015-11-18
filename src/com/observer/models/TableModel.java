package com.observer.models;

import javax.swing.table.DefaultTableModel;

public class TableModel extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6472152847811533026L;

	public TableModel() {
		super(new Object[] { "Symbol", "Bid", "Ask", "Price" }, 0);
	}

	/**
	 * @return the tableModel
	 */
	public TableModel getTableModel() {
		return this;
	}

}
