package com.observer.views;

import java.awt.Color;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultListSelectionModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;

import com.observer.controllers.Controller;
import com.observer.controllers.StockListMouseListener;
import com.observer.controllers.StockListSelectionListener;
import com.observer.interfaces.Observer;
import com.observer.models.RowTable;
import com.observer.models.Stock;
import com.observer.models.TableModel;
import com.observer.models.Trend;
import com.observer.services.UppercaseDocumentFilter;

public class View implements Observer {

	public static volatile View instance;

	private static JTextField textField;

	private JMenuBar menuBar;

	private JMenu mnNewMenu;

	private JMenu menuSettings;

	private JMenu infoMenu;

	private JButton button;

	private RowTable table;

	private View() {
		createGUI();
	}

	private void createGUI() {
		JFrame frmTickers = new JFrame();
		frmTickers.setTitle("StocksObsever");
		frmTickers.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		table = new RowTable();
		table.setBackground(Color.WHITE);
		table.setModel(new TableModel().getTableModel());
		table.setEnabled(true);
		ListSelectionModel cellSelectionModel = new DefaultListSelectionModel();
		cellSelectionModel
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		cellSelectionModel
				.addListSelectionListener(new StockListSelectionListener(table));
		table.setSelectionModel(cellSelectionModel);
		table.addMouseListener(new StockListMouseListener(table));

		frmTickers.setSize(400, 400);
		frmTickers.setVisible(true);
		frmTickers.setFocusable(true);
		frmTickers.setLocationRelativeTo(null);
		menuBar = new JMenuBar();
		menuBar.setMargin(new Insets(1, 0, 0, 0));

		mnNewMenu = new JMenu("File");
		mnNewMenu.setHorizontalAlignment(SwingConstants.LEFT);
		mnNewMenu.setBackground(SystemColor.menu);
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Import");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser fileopen = new JFileChooser();             
                int ret = fileopen.showDialog(null, "«берегти список");                
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                                    }
				
			}
		});
		mnNewMenu.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem1 = new JMenuItem("Save list");
		mnNewMenu.add(mntmNewMenuItem1);

		menuSettings = new JMenu("Settings");
		menuSettings.setHorizontalAlignment(SwingConstants.LEFT);
		menuSettings.setBackground(SystemColor.menu);
		menuBar.add(menuSettings);

		infoMenu = new JMenu("Info");
		infoMenu.setHorizontalAlignment(SwingConstants.LEFT);
		infoMenu.setBackground(SystemColor.menu);
		menuBar.add(infoMenu);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setViewportBorder(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);

		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(10, 11, 86, 20);

		panel.add(textField);
		DocumentFilter filter = new UppercaseDocumentFilter();
		((AbstractDocument) textField.getDocument()).setDocumentFilter(filter);
		button = new JButton("Add");
		button.setBounds(107, 10, 91, 23);

		panel.add(button);
		GroupLayout groupLayout = new GroupLayout(frmTickers.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout
				.createParallelGroup(Alignment.LEADING)
				.addComponent(menuBar, GroupLayout.DEFAULT_SIZE, 392,
						Short.MAX_VALUE)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 392,
						Short.MAX_VALUE)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 392,
						Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addComponent(menuBar, GroupLayout.PREFERRED_SIZE, 32,
								GroupLayout.PREFERRED_SIZE)
						.addGap(1)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE,
								141, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 77,
								GroupLayout.PREFERRED_SIZE).addGap(16)));
		frmTickers.getContentPane().setLayout(groupLayout);

		button.addActionListener(new Controller(table, textField));
		frmTickers.getContentPane().setLayout(groupLayout);
	}

	@Override
	public synchronized void update(Stock stock) {
		table.refresh(stock.getId());
		if (stock.getTrend() == null) {
			table.setRowColorDefault(stock.getId());
		} else if (stock.getTrend().equals(Trend.Up)) {
			table.setRowColorUp(stock.getId());
		} else
			table.setRowColorDown(stock.getId());
	}

	public synchronized static View getInstance() {
		if (instance == null) {
			synchronized (View.class) {
				if (instance == null)
					instance = new View();
			}
		}
		return instance;
	}
}
