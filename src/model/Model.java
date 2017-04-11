package model;

import annotations.ThreadSafe;
import annotations.UiThread;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import main.MVC;
import view.View;

/**
 * This class is thread-safe because of thread confinement.
 */

@ThreadSafe
public class Model {
	private MVC mvc;
    private final ObservableList<Party> partyList = FXCollections.observableArrayList();
    private final ObservableList<PieChart.Data> pieList = FXCollections.observableArrayList();

    public void setMVC(MVC mvc) {
		this.mvc = mvc;
	}

	@UiThread
    public ObservableList<Party> getPartyList() {
	    return partyList;
	}

    @UiThread
    public ObservableList<PieChart.Data> getPieList() {
        return pieList;
    }

	@UiThread
    public void addParty(Party party) {
	    if(!partyList.contains(party)) {
            partyList.add(party);
            pieList.add(new PieChart.Data(party.getName(), party.getVotes()));
        }

        mvc.forEachView(View::onModelChanged);
    }

	@UiThread
    public void removeParty(Party party) {
        partyList.remove(party);

        for(PieChart.Data pcd: pieList) {
            if (pcd.getName().equals(party.getName())) {
                pieList.remove(pcd);
                break;
            }
        }

        mvc.forEachView(View::onModelChanged);
    }

	@UiThread
    public void voteParty(Party party) {
	    party.setVotes(party.getVotes() + 1);

        for(PieChart.Data pcd: pieList) {
            if (pcd.getName().equals(party.getName())) {
                pcd.setPieValue(party.getVotes());
                break;
            }
        }

        mvc.forEachView(View::onModelChanged);
    }

    @UiThread
    public void serverVotes(String party, int votes) {
        for(Party p: partyList) {
            if(p.getName().equals(party)) {
                p.setVotes(p.getVotes() + votes);
                break;
            }
        }

        for(PieChart.Data pcd: pieList) {
            if (pcd.getName().equals(party)) {
                pcd.setPieValue(pcd.getPieValue() + votes);
                break;
            }
        }

        mvc.forEachView(View::onModelChanged);
    }
}