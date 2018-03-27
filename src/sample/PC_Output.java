package sample;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;



public class PC_Output{

    private double oppacity = 0;
    private double screenOppacity = 0;
    public  String allText="";
    public  boolean isClicked = false;
    public  Label lblPassword = new Label();
    public PC_Output(Stage primaryStage) {
        try{

            GridPane grid = GridSettings("passwordCrack", new int[]{30,  50, 30}, new int[]{25, 75, 25});
            grid.getStyleClass().add("page");
            grid.setOpacity(0);

            lblPassword.setId("label_passwordCrack");
            try {
                BufferedReader br = new BufferedReader(new FileReader("scripts/passwordkey.txt"));
                StringBuilder sb = new StringBuilder();
                String line= br.readLine();
                while (line != null)
                {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                allText = sb.toString();
                br.close();
            }
            catch (Exception ex)
            {


            }
            if (allText.contains("KEY FOUND!"))
            {
                String passwordkey = allText.substring(allText.lastIndexOf("KEY FOUND! ")+1);
                String password = passwordkey.substring(passwordkey.indexOf("[")+1,passwordkey.indexOf("]"));
                lblPassword.setText(">MOT DE PASSE TROUVE: "+ password);
            }
            else
            {
                lblPassword.setText(">MOT DE PASSE NON TROUVE...");
                new Menu(primaryStage);
            }


            grid.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    isClicked = true;
                    new Advice(primaryStage,"passwordCrack_Advice");
                }
            });


            GridPane.setConstraints(lblPassword,1,1);
            GridPane.setHalignment(lblPassword, HPos.CENTER);


            grid.getChildren().addAll(lblPassword);
            primaryStage.getScene().setRoot(grid);

            new AnimationTimer() {
                public void handle(long currentNanoTime) {
                    if (isClicked)
                        this.stop();
                    if (screenOppacity < 1) {
                        screenOppacity += 0.01;
                        grid.setOpacity(screenOppacity);
                    }

                    FadeInOut(grid);

                }
            }.start();

        }catch (Exception e)
        {
            new Error(primaryStage,"Clé non trouvé");
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
