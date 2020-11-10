package server;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class MailBox<E> {

	private LinkedBlockingQueue<E> items;

	public MailBox() {
		items = new LinkedBlockingQueue<E>();
	}

	public void addItem(E item) {	 
		items.add(item);
	}

	public ArrayList<E> fetchAll() {
		ArrayList<E> newItems = new ArrayList<E>();
		while (!items.isEmpty()) {
			newItems.add(items.remove());
		}
		return newItems;
	}

}
