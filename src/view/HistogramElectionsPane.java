package view;

import annotations.ThreadSafe;
import annotations.UiThread;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.MVC;
import model.Party;

@ThreadSafe
public class HistogramElectionsPane extends BorderPane implements View {
	private final MVC mvc;
	private final GridPane gridPane;

	@UiThread
	public HistogramElectionsPane(MVC mvc) {
		this.mvc = mvc;
		mvc.register(this);

        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.getColumnConstraints().add(new ColumnConstraints(150)); // column 1 is 150 wide
        gridPane.getColumnConstraints().add(new ColumnConstraints(200)); // column 2 is 200 wide
        gridPane.getColumnConstraints().add(new ColumnConstraints(50)); // column 2 is 40 wide

        setCenter(new ScrollPane(gridPane));

		onModelChanged();
	}

	@Override @UiThread
	public void onModelChanged() {
		int totalVotes = 0;
		for (Party party: mvc.model.getPartyList())
			totalVotes += party.getVotes();

		gridPane.getChildren().clear();

		int row = 0;
		for (Party party: mvc.model.getPartyList()) {
			Button partyButton = new Button(party.getName());
			partyButton.setPrefSize(150, 23);
            partyButton.setOnAction(e -> mvc.controller.voteParty(party));

            Rectangle rectangle = new Rectangle(totalVotes == 0 ? 0 : party.getVotes() * 200 / totalVotes,20);
            rectangle.setFill(Color.RED);

            Label percentage = new Label(Integer.toString(totalVotes == 0 ? 0 : party.getVotes() * 100 / totalVotes) + "%");
            percentage.setPrefSize(50, 20);

            gridPane.addRow(row++, partyButton, rectangle, percentage);

		}
	}

	@Override @UiThread
	public void askForNewParty() {
	}

	@Override
	public void askForRemoveParty() {

	}

	@Override
	public void askForVoteParty() {

	}
}