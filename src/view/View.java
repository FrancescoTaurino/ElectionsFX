package view;

import annotations.UiThread;

public interface View {
	// 3: change your display
	@UiThread
	void askForNewParty();

	@UiThread
	void askForRemoveParty();

	@UiThread
    void askForVoteParty();

	// 4: I've changed
	@UiThread
	void onModelChanged();
}