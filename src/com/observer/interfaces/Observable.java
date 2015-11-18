package com.observer.interfaces;

import com.observer.models.Stock;

public interface Observable {

	void registerObserver(Observer o);

	void removeObserver(Observer o);

	void notifyObservers(Stock stock, int row);
}
