package de.bangl.WGJails.core.threads;

public abstract class SThread implements Runnable {
	Thread t;
	public SThread() {
		t = new Thread(this);
		t.start();
	}
}
