package com.observer.services;

import com.observer.models.Stock;

public class StocksDataService extends Thread {

	private BaseService baseService;

	private volatile boolean mFinish = false;

	private Stock stock;

	public StocksDataService(Stock stock) {
		this.stock = stock;
		baseService = new BaseService();
	}

	public void finish() {
		mFinish = true;
	}

	@Override
	public void run() {
		do {
			try {
				if (!mFinish) {
					baseService.searchBases(stock);
				} else
					return;
				Thread.sleep(500);
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
		} while (true);
	}

	/**
	 * @return the observer
	 */
	// public Observer getObserver() {
	// return observer;
	// }
	//
	// /**
	// * @param observer
	// * the observer to set
	// */
	// public void setObserver(Observer observer) {
	// this.observer = observer;
	// baseService.registerObserver(observer);
	// }
}
