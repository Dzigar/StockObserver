package com.observer.app;

import java.awt.EventQueue;
import java.io.IOException;

import com.observer.views.View;

public class StocksObserver {

	public static void main(String[] args) throws IOException {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				View.getInstance();
			}
		});
	}
}
