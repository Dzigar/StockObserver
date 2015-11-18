package com.observer.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;

import org.jfree.ui.RefineryUtilities;

import com.observer.chart.PriceVolumeChart;

public class ShowChartMenuListener implements ActionListener {

	private String stockSymbol;

	public ShowChartMenuListener(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			PriceVolumeChart demo;
			demo = new PriceVolumeChart(stockSymbol + " chart", stockSymbol);
			demo.pack();
			RefineryUtilities.centerFrameOnScreen(demo);
			demo.setVisible(true);
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

}
