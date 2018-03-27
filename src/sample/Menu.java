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

import static sample.Main.CheckConnectedNetwork;
import static sample.Main.networkList;

public class Menu {

    private double oppacity = 0;
    private double screenOppacity = 0;

    public Menu(Stage primaryStage)
    {
        try {
            GridPane grid = GridSettings("menu", new int[]{30, 30, 30}, new int[]{25, 25, 25, 25}); // create a grid for the menu page
            grid.getStyleClass().add("page");
            grid.setOpacity(0);

            Label labelTarget = new Label(); // this label shows the name of the current target network
            labelTarget.setId("label_Target");
            labelTarget.setText(">TARGET : "+Main.FindTargetNetwork(networkList).getESSID());
            GridPane.setConstraints(labelTarget, 1, 0);
            GridPane.setHalignment(labelTarget, HPos.CENTER);

            ImageButton btnDataStealer = new ImageButton("button_DataStealer_Disable", primaryStage,0, 3); // button for data stealer
            ImageButton btnPasswordCrack; // button for the password crack
            ImageButton btnEvilTwin; // button for the evil twin
            ImageButton btnWifiJam; // button for the wifi jam
            ImageButton btnMassiveScan; // button for the massive scan

            if(!Main.FindTargetNetwork(networkList).getBSSID().equals("")) // check if there is a target network
            {
                btnPasswordCrack = new ImageButton("button_PasswordCrack", primaryStage,0, 1);
                btnEvilTwin = new ImageButton("button_EvilTwin", primaryStage,2, 1);
                btnWifiJam = new ImageButton("button_WifiJam", primaryStage,2, 3);

                if(CheckConnectedNetwork()) // check if the user is connected to that target network
                    btnDataStealer.setId("button_DataStealer");
            }
            else // if there is no target network
            {
                btnPasswordCrack = new ImageButton("button_PasswordCrack_Disable", primaryStage,0, 1);
                btnEvilTwin = new ImageButton("button_EvilTwin_Disable", primaryStage,2, 1);
                btnWifiJam = new ImageButton("button_WifiJam_Disable", primaryStage,2, 3);
            }

            btnMassiveScan = new ImageButton("button_MassiveScan", primaryStage,1, 2); // this button is always active

            grid.getChildren().addAll(btnWifiJam, btnPasswordCrack, btnMassiveScan, btnEvilTwin, btnDataStealer, labelTarget);

            primaryStage.getScene().setRoot(grid);

            new AnimationTimer()
            {
                public void handle(long currentNanoTime){

                    if(screenOppacity<1)
                        FadeInScreen(grid);

                    FadeInOut(btnDataStealer,btnEvilTwin,btnMassiveScan,btnPasswordCrack,btnWifiJam,labelTarget); // BTNDATASTEALER

                }
            }.start();
        }catch (Exception e)
        {
            new Error(primaryStage,"menu");
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

    private void FadeInScreen(GridPane grid)
    {
        screenOppacity+=0.01;
        grid.setOpacity(screenOppacity);
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
