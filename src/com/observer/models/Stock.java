package com.observer.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.observer.services.StocksDataService;

public class Stock implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3476850095280985805L;

	private int id;

	private Trend trend;

	private List<StockQuote> stockQuotes;

	private String symbol;

	public Stock(int id, String stockSymbol) {
		setId(id);
		setSymbol(stockSymbol);
		new StocksDataService(this).start();
	}

	public List<StockQuote> getStockQuotes() {
		try {
			stockQuotes = new ArrayList<StockQuote>();
			String strUrl = "http://www.google.com/finance/getprices?q="
					+ getSymbol() + "&i=300&p=3d&f=d,o,h,l,c,v";
			URLConnection uc = new URL(strUrl).openConnection();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(uc.getInputStream()));
			String s;
			while ((s = bufferedReader.readLine()) != null) {
				String[] w = s.toString().split(",");
				try {
					stockQuotes.add(new StockQuote(Integer.parseInt(w[0]),
							Double.parseDouble(w[1]), Double.parseDouble(w[2]),
							Double.parseDouble(w[3]), Double.parseDouble(w[4]),
							Integer.parseInt(w[5])));
				} catch (Exception e) {
					System.out.println(e.getLocalizedMessage());
				}
			}
			bufferedReader.close();
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}
		return stockQuotes;
	}

	/**
	 * @return the trend
	 */
	public Trend getTrend() {
		return trend;
	}

	/**
	 * @param trend
	 *            the trend to set
	 */
	public void setTrend(Trend trend) {
		this.trend = trend;
	}

	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol
	 *            the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getBid() {
		return round(new yahoofinance.Stock(getSymbol()).getQuote().getBid()
				.doubleValue());
	}

	public String getAsk() {
		return round(new yahoofinance.Stock(getSymbol()).getQuote().getAsk()
				.doubleValue());
	}

	public String getChange() {
		return round(new yahoofinance.Stock(getSymbol()).getQuote().getChange()
				.doubleValue());
	}

	public String getPrice() {
		return round(new yahoofinance.Stock(getSymbol()).getQuote().getPrice()
				.doubleValue());
	}

	private String round(Double value) {
		return String.format("%.2f", value);

	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

}
