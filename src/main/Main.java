package main;

import controller.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Model;
import view.HistogramElectionsPane;
import view.NumericElectionsPane;
import view.PieChartElectionsPane;

public class Main extends Application {
    private final int WINDOW_WIDTH = 430;
    private final int WINDOW_HEIGHT = 300;
    private final double SCREEN_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
    private final double SCREEN_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
    private final int CORNER_X = (int) (SCREEN_WIDTH - 2 * WINDOW_WIDTH + 100) / 2;
    private final int CORNER_Y = (int) (SCREEN_HEIGHT - 2 * WINDOW_HEIGHT - 30) / 2;

    @Override
    public void start(Stage primaryStage) throws Exception{
        MVC mvc = new MVC(new Model(), new Controller());

        primaryStage.setTitle("Numeric Elections");
        primaryStage.setScene(new Scene(new NumericElectionsPane(mvc), WINDOW_WIDTH, WINDOW_HEIGHT));
        primaryStage.setOnCloseRequest(e -> Platform.exit());
        primaryStage.setX(CORNER_X);
        primaryStage.setY(CORNER_Y);
        primaryStage.show();

        Stage secondStage = new Stage();
        secondStage.setTitle("Numeric Elections");
        secondStage.setScene(new Scene(new NumericElectionsPane(mvc), WINDOW_WIDTH, WINDOW_HEIGHT));
        secondStage.setOnCloseRequest(e -> Platform.exit());
        secondStage.setX(CORNER_X + 10 + WINDOW_WIDTH);
        secondStage.setY(CORNER_Y);
        secondStage.show();

        Stage thirdStage = new Stage();
        thirdStage.setTitle("Histogram Elections");
        thirdStage.setScene(new Scene(new HistogramElectionsPane(mvc), WINDOW_WIDTH, WINDOW_HEIGHT));
        thirdStage.setOnCloseRequest(e -> Platform.exit());
        thirdStage.setX(CORNER_X);
        thirdStage.setY(CORNER_Y + 40 + WINDOW_HEIGHT);
        thirdStage.show();

        Stage fourthStage = new Stage();
        fourthStage.setTitle("PieChart Elections");
        fourthStage.setScene(new Scene(new PieChartElectionsPane(mvc), WINDOW_WIDTH, WINDOW_HEIGHT));
        fourthStage.setOnCloseRequest(e -> Platform.exit());
        fourthStage.setX(CORNER_X + 10 + WINDOW_WIDTH);
        fourthStage.setY(CORNER_Y + 40 + WINDOW_HEIGHT);
        fourthStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
