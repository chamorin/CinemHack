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

public class PC_Choice {

    private double oppacity = 0;
    private double screenOppacity = 0;

    public PC_Choice(Stage stage)
    {

            GridPane grid = GridSettings("passwordCrack", new int[]{5, 25, 25, 25, 5}, new int[]{25, 25, 25, 25});
            grid.getStyleClass().add("page");
            grid.setOpacity(0);

            Label labelTarget = new Label();
            labelTarget.setId("label_passwordCrack");
            labelTarget.setText(">TYPE DE PIRATAGE...");
            GridPane.setConstraints(labelTarget, 2, 1);
            GridPane.setHalignment(labelTarget, HPos.CENTER);

            ImageButton button1 = new ImageButton("PC_button_wordlist_Go", stage, 1, 2);
            ImageButton button2 = new ImageButton("PC_button_crunch_Go", stage, 3, 2);
            ImageButton button3 = new ImageButton("button_PasswordCrack_Back", stage, 2, 3);

            button1.setText(">LISTE DE MOTS");
            button2.setText(">PERSONNALISÉ");





        new AnimationTimer() {
                public void handle(long currentNanoTime) {

                    if (screenOppacity < 1) {
                        screenOppacity += 0.01;
                        grid.setOpacity(screenOppacity);
                    }

                    FadeInOut(button1, button2);

                }
            }.start();

        grid.getChildren().addAll(labelTarget,button1,button2,button3);
        stage.getScene().setRoot(grid);
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
}
