package sample;

import javafx.animation.AnimationTimer;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;

public class DS_Data {
    private double screenOppacity = 0;

    public DS_Data(Stage primaryStage)
    {
        try{
            GridPane grid = GridSettings("dataStealer", new int[]{25,  50, 25}, new int[]{25, 75, 25});
            grid.getStyleClass().add("page");
            grid.setOpacity(0);

            Label label = new Label(); // this label shows the name of the current target network
            label.setId("label_DataStealer");
            label.setText(">DONNEES RECOLTES");
            GridPane.setConstraints(label, 1, 0);
            GridPane.setHalignment(label, HPos.CENTER);

            TextArea textArea = new TextArea();
            textArea.setEditable(false);
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(textArea);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);
            ImageButton backBtn = new ImageButton("button_DataStealer_Back", primaryStage, 1, 2);

            textArea.setWrapText(true);
            OutputLogContent(textArea);

            new AnimationTimer() {
                public  void handle(long currentnanotime)
                {
                    if (screenOppacity < 1) {
                        screenOppacity += 0.01;
                        grid.setOpacity(screenOppacity);
                    }

                }
            }.start();

            scrollPane.setId("DS_scrollpane");
            textArea.setId("DS_textarea");
            GridPane.setConstraints(scrollPane, 1,1);

            grid.getChildren().addAll(textArea,scrollPane,backBtn,label);
            primaryStage.getScene().setRoot(grid);

        }catch (Exception e)
        {
            new Error(primaryStage,"page DataStealer");
        }
    }

    private void OutputLogContent(TextArea textArea)
    {
        String l;
        try {
            BufferedReader br = new BufferedReader(new FileReader("scripts/data.txt"));
            while (!(l = br.readLine()).isEmpty())
                textArea.appendText(l);
        }catch (Exception e){}
    }

    static  private ColumnConstraints columnWithPercentage(final double percentage) {
        final ColumnConstraints constraints = new ColumnConstraints();
        constraints.setPercentWidth(percentage);
        return constraints;
    }

    static private RowConstraints rowWithPercentage(final double percentage) {
        final RowConstraints constraints = new RowConstraints();
        constraints.setPercentHeight(percentage);
        return constraints;
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
}
