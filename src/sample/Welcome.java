package sample;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Welcome {

    private double oppacity = 0;
    private double screenOppacity = 0;
    private boolean isClicked = false;

    public Welcome(Stage primaryStage) {
        try {
            GridPane grid = GridSettings("welcome", new int[]{100}, new int[]{});
            grid.getStyleClass().add("page");
            grid.setOpacity(0);

            Scene scene = new Scene(grid, 800, 600);
            scene.setFill(Color.BLACK);

            grid.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    isClicked = true;
                    new Home(primaryStage); // lead to the menu after a click action
                }
            });

            scene.getStylesheets().addAll(this.getClass().getResource("Styles.css").toExternalForm());
            primaryStage.setFullScreen(true);
            primaryStage.setAlwaysOnTop(true);
            primaryStage.setScene(scene);
            primaryStage.show();

            new AnimationTimer() {
                public void handle(long currentNanoTime) {

                    if (isClicked)
                        this.stop();

                    if (screenOppacity < 1)
                        FadeInScreen(grid);

                    FadeInOut(grid);

                }
            }.start();
        }catch (Exception e)
        {
            new Error(primaryStage,"démarrage");
        }
    }

    private void FadeInScreen(GridPane grid)
    {
        screenOppacity+=0.01;
        grid.setOpacity(screenOppacity);
    }

    static private GridPane GridSettings(String style, int[] numberColumns, int[] numberRows)
    {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(50, 50, 50, 50));
        gridPane.setVgap(15);
        gridPane.setHgap(15);
        gridPane.setId(style);

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
}
