package sample;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Popup;
import javafx.stage.Stage;

import static sample.Main.FindTargetNetwork;
import static sample.Main.networkList;


public class PC_Crunch {

    private double oppacity = 0;
    private double screenOppacity = 0;
    public  static  boolean infoActive = false;
    public PC_Crunch(Stage stage)
    {


        GridPane grid = GridSettings("passwordCrack", new int[]{30,40,20}, new int[]{25, 25, 25, 25,25});
        grid.getStyleClass().add("page");
        grid.setOpacity(0);

        Label lblRangeMin = new Label();
        lblRangeMin.setId("label_passwordCrack");
        lblRangeMin.setText("MIN");
        GridPane.setConstraints(lblRangeMin, 0, 1);
        GridPane.setHalignment(lblRangeMin, HPos.CENTER);
        // lblRangeMin.setMinWidth(Region.USE_PREF_SIZE);


        Label lblRangeMax = new Label();
        lblRangeMax.setId("label_passwordCrack");
        lblRangeMax.setText("MAX");
        GridPane.setConstraints(lblRangeMax, 0, 2);
        GridPane.setHalignment(lblRangeMax, HPos.CENTER);


        Slider sliderMin = new Slider();
        sliderMin.getStyleClass().add("slider");
        sliderMin.setMin(0);
        sliderMin.setMax(100);
        sliderMin.setValue(0);
        sliderMin.setShowTickLabels(true);
        sliderMin.setShowTickMarks(true);
        sliderMin.setMajorTickUnit(50);
        sliderMin.setMinorTickCount(5);
        sliderMin.setBlockIncrement(1);
        sliderMin.valueProperty().addListener((obs, oldval, newVal) ->
                sliderMin.setValue(newVal.intValue()));
        GridPane.setConstraints(sliderMin, 1, 1);


        Label lblMin = new Label();
        lblMin.setId("label_passwordCrack");
        GridPane.setConstraints(lblMin, 2, 1);
        lblMin.setText("0");

        Slider sliderMax = new Slider();
        sliderMax.setMin(0);
        sliderMax.setMax(100);
        sliderMax.setValue(50);
        sliderMax.setShowTickLabels(true);
        sliderMax.setShowTickMarks(true);
        sliderMax.setMajorTickUnit(50);
        sliderMax.setMinorTickCount(5);
        sliderMax.setBlockIncrement(1);
        sliderMax.valueProperty().addListener((obs, oldval, newVal) ->
                sliderMax.setValue(newVal.intValue()));
        GridPane.setConstraints(sliderMax, 1, 2);


        Label lblMax = new Label();
        lblMax.setId("label_passwordCrack");
        GridPane.setConstraints(lblMax, 2, 2);
        lblMax.setText("100");



        Label lblPassword = new Label();
        lblPassword.setId("label_passwordCrack");
        lblPassword.setText("Mot de passe");
        GridPane.setConstraints(lblPassword, 0, 3);
        GridPane.setHalignment(lblPassword, HPos.CENTER);


        TextField txtPassword = new TextField ();
        txtPassword.setId("PC_TextField_Crunch");
        GridPane.setConstraints(txtPassword, 1, 3);



        ImageButton button2 = new ImageButton("PC_button_Go", stage, 1, 4);
        button2.setText(">PIRATER");



       // ImageButton button3 = new ImageButton("button_PasswordCrack_Info", stage, 2, 0);
        final Popup popup = new Popup();
        popup.setX(1500);
        popup.setY(1000);


        /*button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (infoActive)
                {
                    popup.hide();
                    infoActive = false;
                }
                else
                {
                    popup.show(stage);
                    infoActive = true;
                }
            }
        });*/

        new AnimationTimer() {
            public void handle(long currentNanoTime) {

                lblMin.setText(((int) sliderMin.getValue()) + "");
                lblMax.setText(((int) sliderMax.getValue()) + "");
                if (sliderMin.getValue() > sliderMax.getValue() || txtPassword.getText().equals("") || txtPassword.getText().contains(" "))
                    button2.setDisable(true);
                else
                    button2.setDisable(false);

                if (screenOppacity < 1) {
                    screenOppacity += 0.01;
                    grid.setOpacity(screenOppacity);
                }

                FadeInOut( button2);

            }
        }.start();


        button2.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                new Script("/scripts/script_PasswordCrack","0",Main.FindTargetNetwork(networkList).getBSSID(),FindTargetNetwork(networkList).getChannel(),FindTargetNetwork(networkList).getESSID(),lblMin.getText(),lblMax.getText(),txtPassword.getText(),"wordlist.txt");
                new PC_Output(stage);
            }
        });



        grid.getChildren().addAll(lblPassword,lblRangeMin,lblRangeMax,button2,txtPassword,sliderMax,sliderMin , lblMin,lblMax/*,button3*/);
        stage.getScene().setRoot(grid);
        stage.show();
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
