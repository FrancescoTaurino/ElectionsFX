package controller;

import annotations.ThreadSafe;
import annotations.UiThread;
import main.MVC;
import model.Party;
import view.View;

/**
 * This class is thread-safe because of thread-confinement
 */

@ThreadSafe
public class Controller {
	private MVC mvc;

	public void setMVC(MVC mvc) {
		this.mvc = mvc;
	}

	// 1: the user did something
	@UiThread
    public void askForNewParty(View view) {
		view.askForNewParty();
	}

	@UiThread
	public void askForRemoveParty(View view) {
		view.askForRemoveParty();
	}

	@UiThread
    public void askForVoteParty(View view) {
	    view.askForVoteParty();
    }

	@UiThread
    public void addParty(Party party) {
		mvc.model.addParty(party);
	}

	@UiThread
    public void removeParty(Party party) {
		mvc.model.removeParty(party);
	}

    @UiThread
    public void voteParty(Party party) {
        mvc.model.voteParty(party);
    }

	@UiThread
	public void votesFromServer() {
		new Thread(new WorkerThread(mvc)).start();
	}
}