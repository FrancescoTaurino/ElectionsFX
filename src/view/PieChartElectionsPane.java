package view;

import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.BorderPane;
import main.MVC;

import javax.script.Bindings;


public class PieChartElectionsPane extends BorderPane implements View {
    private final MVC mvc;
    private final PieChart pieChart;

    public PieChartElectionsPane(MVC mvc) {
        this.mvc = mvc;
        mvc.register(this);

        pieChart = new PieChart();
        pieChart.setTitle("Elections Pie Chart");
        pieChart.setLegendSide(Side.TOP);
        pieChart.setLabelsVisible(true);

        pieChart.setData(mvc.model.getPieList());

        this.setCenter(pieChart);
    }

    @Override
    public void askForNewParty() {

    }

    @Override
    public void askForRemoveParty() {

    }

    @Override
    public void askForVoteParty() {

    }

    @Override
    public void onModelChanged() {

    }
}
