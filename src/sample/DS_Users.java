package sample;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import static sample.Main.FindTargetNetwork;
import static sample.Main.networkList;


public class DS_Users {

    private double oppacity = 0;
    private double screenOppacity = 0;

    public DS_Users(Stage primaryStage)
    {
        try{
            int userNumber = 0;

            GridPane grid = GridSettings("dataStealer", new int[]{20, 20, 20, 20, 20}, new int[]{16,16,16,16,16,16});
            grid.getStyleClass().add("page");
            grid.setOpacity(0);

            for(int col=0;col<5;++col)
            {
                for(int row=1;row<6;++row)
                {
                    if(userNumber<FindTargetNetwork(networkList).userList.size())
                    {
                        ImageButton button = new ImageButton("DS_button_User", primaryStage,col, row);
                        button.setText(FindTargetNetwork(networkList).userList.get(userNumber).getIpAdresse()+"\n"+FindTargetNetwork(networkList).userList.get(userNumber).getMacAdresse()+"\n"+FindTargetNetwork(networkList).userList.get(userNumber).getMachine());
                        grid.getChildren().add(button);
                        ++userNumber;
                    }
                }
            }

            if(userNumber == 0)
                new Menu(primaryStage);

            primaryStage.getScene().setRoot(grid);

            new AnimationTimer()
            {
                public void handle(long currentNanoTime){

                    if(screenOppacity<1)
                    {
                        screenOppacity+=0.01;
                        grid.setOpacity(screenOppacity);
                    }

                    FadeInOut(grid);

                }
            }.start();
        }catch (Exception e)
        {
            new Error(primaryStage,"usagers DataStealer");
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
