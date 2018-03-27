package sample;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import static sample.Main.FindTargetNetwork;
import static sample.Main.networkList;


public class ET_Choice {

    private double oppacity = 0;
    private double screenOppacity = 0;

    public ET_Choice(Stage primaryStage)
    {
        try{
            GridPane grid = GridSettings("evilTwin", new int[]{30, 30, 30}, new int[]{25, 25, 25, 25});
            grid.getStyleClass().add("page");
            grid.setOpacity(0);

            Label labelTarget = new Label();
            labelTarget.setId("label_Target");
            labelTarget.setText(">NOMMEZ VOTRE CLONE ...");
            GridPane.setConstraints(labelTarget, 1, 0);
            GridPane.setHalignment(labelTarget, HPos.CENTER);

            TextField textField = new TextField();
            textField.setId("ET_textfield");
            GridPane.setConstraints(textField,1,1);

            ImageButton button = new ImageButton("ET_button_Go",primaryStage,1,2);
            button.setText(">LANCER LE CLONAGE");

            ImageButton button1 = new ImageButton("button_EvilTwin_Back",primaryStage,1,3);

            grid.getChildren().addAll(labelTarget,textField,button,button1);

            button.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    if(textField.getText().equals(""))
                        new Script("/scripts/script_EvilTwin",FindTargetNetwork(networkList).getBSSID(),FindTargetNetwork(networkList).getChannel(),FindTargetNetwork(networkList).getESSID());
                    else
                        new Script("/scripts/script_EvilTwin",FindTargetNetwork(networkList).getBSSID(),FindTargetNetwork(networkList).getChannel(),textField.getText());

                    new Advice(primaryStage,"evilTwin_Advice");
                }
            });

            primaryStage.getScene().setRoot(grid);

            new AnimationTimer()
            {
                public void handle(long currentNanoTime){

                    if(textField.getText().contains(" "))
                        button.setDisable(true);
                    else
                        button.setDisable(false);

                    if(screenOppacity<1)
                    {
                        screenOppacity+=0.01;
                        grid.setOpacity(screenOppacity);
                    }

                    FadeInOut();

                }
            }.start();
        }catch (Exception e)
        {
            new Error(primaryStage,"choix EvilTwin");
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
