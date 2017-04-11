package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import main.MVC;
import model.Party;

import java.util.Optional;

public class NumericElectionsPane extends BorderPane implements View {
    private final MVC mvc;
    private final TableView<Party> tableView;

    public NumericElectionsPane(MVC mvc) {
        this.mvc = mvc;
        mvc.register(this);

        Button addParty = new Button("Add Party");
        addParty.setOnAction(e -> mvc.controller.askForNewParty(this));

        Button getVotes = new Button("Get Votes");
        getVotes.setOnAction(e -> mvc.controller.votesFromServer());

        Button removeParty = new Button("Remove Party");
        removeParty.setOnAction(e -> mvc.controller.askForRemoveParty(this));

        Button voteParty = new Button("Vote Party");
        voteParty.setOnAction(e -> mvc.controller.askForVoteParty(this));

        HBox bottomButtons = new HBox(10);
        bottomButtons.getChildren().addAll(addParty, removeParty, voteParty, getVotes);
        bottomButtons.setAlignment(Pos.BOTTOM_CENTER);
        setMargin(bottomButtons, new Insets(10,10,10,10));
        setBottom(bottomButtons);

        TableColumn<Party, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setPrefWidth(250);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Party, String> votesColumn = new TableColumn<>("Votes");
        votesColumn.setPrefWidth(180);
        votesColumn.setCellValueFactory(new PropertyValueFactory<>("votes"));
        votesColumn.setStyle("-fx-alignment: CENTER;");

        tableView = new TableView<>();
        tableView.getColumns().addAll(nameColumn, votesColumn);
        tableView.setItems(mvc.model.getPartyList());

        this.setCenter(tableView);
    }

    @Override
    public void askForNewParty() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Party");
        dialog.setHeaderText(null);
        dialog.setContentText("Please, enter party name:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().isEmpty())
            mvc.controller.addParty(new Party(result.get(), 0));
    }

    @Override
    public void askForRemoveParty() {
        Alert alert;
        Party party;

        try {
            party = tableView.getSelectionModel().getSelectedItem();

            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Party Removal");
            alert.setHeaderText(null);
            alert.setContentText("Remove party: " + party.getName() + " ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
                mvc.controller.removeParty(party);
        }
        catch (NullPointerException npe) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Party Removal");
            alert.setHeaderText(null);
            alert.setContentText("Please, select party to remove.");

            alert.showAndWait();
        }
    }

    @Override
    public void askForVoteParty() {
        Alert alert;

        try {
            mvc.controller.voteParty(tableView.getSelectionModel().getSelectedItem());
        }
        catch (NullPointerException npe) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Party Votation");
            alert.setHeaderText(null);
            alert.setContentText("Please, select party to vote.");

            alert.showAndWait();
        }
    }

    @Override
    public void onModelChanged() {
        tableView.refresh();
    }
}
