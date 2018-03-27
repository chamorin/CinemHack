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

public class Error {

    private double oppacity = 0;
    private double screenOppacity = 0;

    public Error(Stage primaryStage,String message)
    {
        GridPane grid = GridSettings("menu", new int[]{30,30,30}, new int[]{20,40,40});
        grid.getStyleClass().add("page");
        grid.setOpacity(0);

        Label errorMessage = new Label();
        errorMessage.setId("label_Error");
        errorMessage.setText(">ERROR : "+message);
        GridPane.setConstraints(errorMessage, 1, 0);
        GridPane.setHalignment(errorMessage, HPos.CENTER);

        ImageButton logo = new ImageButton("logo", primaryStage,1, 1);

        Label exitMessage = new Label();
        exitMessage.setId("label_Error");
        exitMessage.setText("Fermeture de l'application...");
        GridPane.setConstraints(exitMessage, 1, 2);
        GridPane.setHalignment(exitMessage, HPos.CENTER);

        grid.getChildren().addAll(logo, errorMessage,exitMessage);

        primaryStage.getScene().setRoot(grid);

        new AnimationTimer()
        {
            public void handle(long currentNanoTime){

                if(screenOppacity<1)
                    FadeInScreen(grid);

                FadeInOut(logo,errorMessage,exitMessage);

                if(logo.getOpacity() == -0.008407247367125526)
                    primaryStage.close();

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

    private void FadeInScreen(GridPane grid)
    {
        screenOppacity+=0.01;
        grid.setOpacity(screenOppacity);
    }

    private void FadeInOut(Node... nodes)
    {
        double y = Math.sin(oppacity);

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

}
