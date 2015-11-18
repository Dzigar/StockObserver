package com.observer.chart;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JPanel;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.HighLowRenderer;
import org.jfree.data.time.Minute;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.ui.ApplicationFrame;

public class PriceVolumeChart extends ApplicationFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3440605997620241295L;
	private static BufferedReader bufferedReader;

	/**
	 * Default constructor
	 * 
	 * @throws IOException
	 */
	public PriceVolumeChart(String title, String stockSymbol)
			throws IOException {
		super(title);
		JPanel panel = createDemoPanel(stockSymbol);
		panel.setPreferredSize(new Dimension(1200, 700));
		setContentPane(panel);
	}

	private static OHLCDataset createPriceDataset(String stockSymbol)
			throws IOException {
		OHLCSeries s1 = new OHLCSeries(stockSymbol);
		String strUrl = "http://www.google.com/finance/getprices?q="
				+ stockSymbol + "&i=60&p=1d&f=d,o,h,l,c,v";
		try {
			URL url = new URL(strUrl);
			URLConnection uc = url.openConnection();
			bufferedReader = new BufferedReader(new InputStreamReader(
					uc.getInputStream()));
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		String s;
		int time = 27000000;
		int min = 300000;
		int n = 0;
		while ((s = bufferedReader.readLine()) != null) {
			String[] w = s.toString().split(",");
			try {
				s1.add(new Minute(new Date(time + (min * n))),
						Double.parseDouble(w[1]), Double.parseDouble(w[2]),
						Double.parseDouble(w[3]), Double.parseDouble(w[4]));
				n++;
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
			}

		}
		bufferedReader.close();
		OHLCSeriesCollection dataset = new OHLCSeriesCollection();
		dataset.addSeries(s1);
		return dataset;
	}

	private static JFreeChart createCombinedChart(String stockSymbol)
			throws IOException {
		OHLCDataset data1 = createPriceDataset(stockSymbol);
		HighLowRenderer renderer1 = new HighLowRenderer();
		renderer1.setTickLength(3);
		renderer1.setBaseToolTipGenerator(new StandardXYToolTipGenerator(
				StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
				new SimpleDateFormat("d-MMM-yyyy"), new DecimalFormat("0.00")));
		renderer1.setSeriesPaint(0, Color.blue);

		DateAxis domainAxis = new DateAxis("Date");
		NumberAxis rangeAxis = new NumberAxis("Price");
		rangeAxis.setNumberFormatOverride(new DecimalFormat("$0.00"));
		rangeAxis.setAutoRange(true);
		rangeAxis.setAutoRangeIncludesZero(false);
		ChartColor chartColor = new ChartColor(0, 0, 0);

		XYPlot plot1 = new XYPlot(data1, domainAxis, rangeAxis, renderer1);
		plot1.getRangeCrosshairPaint();

		plot1.setRangePannable(true);

		JFreeChart chart = new JFreeChart(stockSymbol,
				JFreeChart.DEFAULT_TITLE_FONT, plot1, false);
		
		
		// ChartUtilities.applyCurrentTheme(chart);

		return chart;
	}

	// create a panel
	public static JPanel createDemoPanel(String stockSymbol) throws IOException {
		JFreeChart chart = createCombinedChart(stockSymbol);
		return new ChartPanel(chart);
	}
}