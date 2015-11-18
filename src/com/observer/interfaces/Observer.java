package com.observer.interfaces;

import com.observer.models.Stock;

public interface Observer {

	void update(Stock stock);
}
