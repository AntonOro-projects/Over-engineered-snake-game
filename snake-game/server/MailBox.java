package server;

import java.util.ArrayList;

import data.Request;

public class MailBox {

	private ArrayList<Request> requests;
	// private Object sync;
	// private boolean isInUse;

	public MailBox() {
		requests = new ArrayList<Request>();
		// isInUse = false;
	}

	public void addRequest(Request request) {
		// if (isInUse) {
		// try {
		// sync.wait();
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// }
		requests.add(request);
	}

	public ArrayList<Request> fetchAll() {
		// isInUse = true;
		ArrayList<Request> newRequests = (ArrayList<Request>) requests.clone(); // TODO måste vi här tänka på att
																				// någonting skulle kunna stoppas in i
																				// listan medan clone körs?
		requests.clear();
		// sync.notify();
		// isInUse = false;
		return newRequests;
	}

}
