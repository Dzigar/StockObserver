package com.observer.services;

import java.util.ArrayList;
import java.util.List;

import com.observer.interfaces.Observable;
import com.observer.interfaces.Observer;
import com.observer.models.Stock;
import com.observer.models.StockQuote;
import com.observer.models.Trend;
import com.observer.views.View;

public class BaseService implements Observable {

	private List<Observer> observers;
	private StockQuote[] stockQuotes; // stockQuotes of stock
	private int lenght; // quantity of the list of stockQuotes in stock
	private Stock stock; // Stock which goes to search base currently

	public BaseService() {
		observers = new ArrayList<Observer>();
		registerObserver(View.getInstance());
	}

	public void searchBases(Stock stk) {
		this.stock = stk;
		lenght = stock.getStockQuotes().size();
		stockQuotes = new StockQuote[lenght];
		stockQuotes = (StockQuote[]) stock.getStockQuotes()
				.toArray(stockQuotes);

		if (stockQuotes[lenght - 1].getHigh() == stockQuotes[lenght - 2]
				.getHigh()
				&& stockQuotes[lenght - 2].getHigh() == stockQuotes[lenght - 3]
						.getHigh()
				&& stockQuotes[lenght - 3].getHigh() == stockQuotes[lenght - 4]
						.getHigh()) {
			stock.setTrend(Trend.Down);
			notifyObservers(stock, stock.getId());
		} else if (stockQuotes[lenght - 1].getLow() == stockQuotes[lenght - 2]
				.getLow()
				&& stockQuotes[lenght - 2].getLow() == stockQuotes[lenght - 3]
						.getLow()
				&& stockQuotes[lenght - 3].getLow() == stockQuotes[lenght - 4]
						.getLow()) {
			stock.setTrend(Trend.Up);
			notifyObservers(stock, stock.getId());
		} else {
			stock.setTrend(null);
			notifyObservers(stock, stock.getId());
		}
	}

	public void searchLongBase() {
	}

	public void searchShortBase() {

	}

	/**
	 * @return the stockQuotes
	 */
	public StockQuote[] getstockQuotes() {
		return stockQuotes;
	}

	/**
	 * @param stockQuotes
	 *            the stockQuotes to set
	 */
	public void setstockQuotes(StockQuote[] stockQuotes) {
		this.stockQuotes = stockQuotes;
	}

	@Override
	public void registerObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers(Stock stock, int row) {
		for (Observer observer : observers) {
			observer.update(stock);
		}
	}

	public void setObservers(List<Observer> observers) {
		this.observers = observers;
	}

	public List<Observer> getObservers() {
		return observers;
	}
}
