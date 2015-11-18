package com.observer.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.observer.models.RowTable;

public class Controller implements ActionListener {

	private RowTable table;

	private JTextField jTextField;

	private DefaultTableModel tableModel;

	public Controller(RowTable table, JTextField jTextField) {
		this.table = table;
		this.jTextField = jTextField;
		tableModel = (DefaultTableModel) table.getModel();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String stockSymbol = jTextField.getText().toString();
		if (!test(stockSymbol)) {
			return;
		}
		table.addStock(table.getRowCount(), stockSymbol);
		tableModel.addRow(new Object[] { stockSymbol, "" });
		clearFields();

	}

	private void clearFields() {
		jTextField.setText("");
	}

	private boolean test(String testString) {
		Matcher m = Pattern.compile("[a-zA-Z]+").matcher(testString);

		if (m.matches()) {
			for (int i = 0; i < table.getRowCount(); i++) {
				if (testString.equals(table.getValueAt(i, 0))) {
					JOptionPane.showMessageDialog(null,
							"Stock is already added!");
					return false;
				}
			}
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "Unknow tiker!");
			clearFields();
			return false;
		}
	}
}
