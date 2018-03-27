package sample;

import javafx.animation.AnimationTimer;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Loading {

    private double oppacity = 0;
    private double screenOppacity = 0;

    public Loading(Stage primaryStage)
    {
        GridPane grid = GridSettings("loading", new int[]{100}, new int[]{});
        grid.getStyleClass().add("page");
        grid.setOpacity(0);

        Label labelLoading = new Label();
        labelLoading.setWrapText(true);
        labelLoading.setId("label_Loading");
        labelLoading.setText(ShowRandomFact());
        GridPane.setConstraints(labelLoading, 0, 0);
        GridPane.setHalignment(labelLoading, HPos.CENTER);

        grid.getChildren().addAll(labelLoading);

        primaryStage.getScene().setRoot(grid);

        new AnimationTimer()
        {
            public void handle(long currentNanoTime){

                if(screenOppacity<1)
                {
                    screenOppacity+=0.01;
                    grid.setOpacity(screenOppacity);
                }

                FadeInOut(labelLoading);

            }
        }.start();

    }

    static private GridPane GridSettings(String style, int[] numberColumns, int[] numberRows)
    {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(50, 50, 50, 50));
        gridPane.setVgap(15);
        gridPane.setHgap(15);
        gridPane.setId(style);
        gridPane.setAlignment(Pos.CENTER);


        for(int i=0;i<numberColumns.length;++i)
            gridPane.getColumnConstraints().add(columnWithPercentage(numberColumns[i]));

        for(int i=0;i<numberRows.length;++i)
            gridPane.getRowConstraints().add(rowWithPercentage(numberRows[i]));

        return gridPane;
    }

    private void FadeInOut(Node... nodes)
    {
        double y = Math.sin(oppacity)+1.5;

        for(Node node:nodes)
            node.setOpacity(y);

        oppacity+=0.01;
    }

    static private ColumnConstraints columnWithPercentage(final double percentage) {
        final ColumnConstraints constraints = new ColumnConstraints();
        constraints.setPercentWidth(percentage);
        return constraints;
    }

    static private RowConstraints rowWithPercentage(final double percentage) {
        final RowConstraints constraints = new RowConstraints();
        constraints.setPercentHeight(percentage);
        return constraints;
    }

    public String ShowRandomFact()
    {
        String fact;
        Random r = new Random();
        ArrayList<String> facts = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("Facts.txt"));
            br.readLine();
            br.readLine();
            while (!(fact = br.readLine()).isEmpty()) {
                facts.add(fact);
            }

            return facts.get(r.nextInt(facts.size()-1));

        }catch (IOException e)
        {
            return "ERROR";
        }
    }
}