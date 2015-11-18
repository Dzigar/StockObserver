package com.observer.controllers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {

	public KeyListener() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if ((e.getKeyCode() == KeyEvent.VK_C)
				&& ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
			System.out.println("woot!");
		}
	}
}
