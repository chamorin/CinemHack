package sample;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class Lose {

    private double screenOppacity = 0;
    private boolean beginFadeOut = false;

    public Lose(Stage primaryStage)
    {
        try{
            GridPane grid = GridSettings("lose", new int[]{100}, new int[]{});
            grid.getStyleClass().add("page");
            grid.setOpacity(0);

            primaryStage.getScene().setRoot(grid);

            new AnimationTimer()
            {
                public void handle(long currentNanoTime){
                    FadeInOut(grid,primaryStage,this);
                }
            }.start();
        }catch (Exception e)
        {
            new Error(primaryStage,"perdu");
        }
    }

    private void FadeInOut(GridPane grid, Stage primaryStage,AnimationTimer animationTimer)
    {
        double y = Math.sin(screenOppacity);
        grid.setOpacity(y);
        screenOppacity+=0.01;

        if(grid.getOpacity() == 0.9999996829318346)
            beginFadeOut = true;

        if(beginFadeOut && grid.getOpacity() == -0.008407247367125526)
        {
            new Menu(primaryStage);
            animationTimer.stop();
        }
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
