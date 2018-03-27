package sample;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Random;

public class MS_Game {
    public Random rand = new Random();
    public  int hits = 0;
    public  int goal= rand.nextInt(7) + 7;
    int n;
    private double screenOppacity = 0;

    public MS_Game(Stage primaryStage)
    {
        try{
            GridPane grid = GridSettings("massiveScan",new int[]{20,20,20}, new int[]{25, 25, 25, 25});
            grid.getStyleClass().add("page");

            ArrayList<MS_Button> btnList = CreateButtonList();
            int i = 0;
            for (int row = 1; row < 4; ++row)
            {
                for (int col = 0; col < 3; ++col)
                {
                    GridPane.setConstraints(btnList.get(i), col, row);
                    grid.getChildren().add(btnList.get(i));
                    GridPane.setHalignment(btnList.get(i), HPos.CENTER);
                    i++;
                }
            }


            Label lblTimeRemaining = new Label();
            lblTimeRemaining.setText(hits + "/" + goal);
            lblTimeRemaining.setFont(Font.font("consolas", 20));
            lblTimeRemaining.setTextFill(Color.web("#FFFFFF"));

            GridPane.setConstraints(lblTimeRemaining,2,0);
            GridPane.setHalignment(lblTimeRemaining, HPos.CENTER);
            grid.getChildren().add(lblTimeRemaining);

            ProgressBar timeBar = new ProgressBar();
            timeBar.setProgress(1f);
            GridPane.setConstraints(timeBar , 1,0);
            GridPane.setHalignment(timeBar,HPos.CENTER);
            timeBar.setId("MS_progressbar");
            timeBar.setPrefWidth(Double.MAX_VALUE);
            grid.getChildren().add(timeBar);

            //ACTIVER LES BOUTTONS
            Timeline StartGame = new Timeline(new KeyFrame(Duration.millis(1000), new  EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event)
                {
                    ActivateRandomButtons(btnList);
                }
            }));
            StartGame.setCycleCount(Animation.INDEFINITE);
            StartGame.play();

            // PROGRESS
            IntegerProperty seconds = new SimpleIntegerProperty();
            timeBar.progressProperty().bind(seconds.divide(60.0));
            Timeline progress = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(seconds, 60)),
                    new KeyFrame(Duration.seconds(13), e->
                    {
                        DeactivateAllButtons(btnList);
                        StopTimeLine(StartGame);
                        new Lose(primaryStage);
                    }, new KeyValue(seconds, 0))
            );
            progress.setCycleCount(1);
            progress.play();

            HandleClick(btnList,lblTimeRemaining , StartGame, progress,primaryStage);
            primaryStage.getScene().setRoot(grid);

            new AnimationTimer()
            {
                public void handle(long currentNanoTime){

                    if(screenOppacity<1)
                    {
                        screenOppacity+=0.01;
                        grid.setOpacity(screenOppacity);
                    }
                    else
                        this.stop();

                }
            }.start();
        }catch (Exception e)
        {
            new Error(primaryStage,"jeu MassiveScan");
        }
    }

    // MÉTHODES
    private ColumnConstraints columnWithPercentage(final double percentage) {
        final ColumnConstraints constraints = new ColumnConstraints();
        constraints.setPercentWidth(percentage);
        return constraints;
    }

    private RowConstraints rowWithPercentage(final double percentage) {
        final RowConstraints constraints = new RowConstraints();
        constraints.setPercentHeight(percentage);
        return constraints;
    }

    public ArrayList<MS_Button> CreateButtonList()
    {
        ArrayList<MS_Button> btnList = new ArrayList<>();
        for (int i = 0 ; i < 9 ; i++)
        {
            btnList.add(new MS_Button(false));
        }
        return  btnList;
    }

    public void DeactivateAllButtons(ArrayList<MS_Button> btnList)
    {
        for (MS_Button m: btnList)
            m.setId("MS_Button");
    }

    public void HandleClick(ArrayList<MS_Button> btnList , Label l , Timeline t, Timeline t2,Stage primaryStage)
    {

        for (MS_Button m : btnList)
        {
            EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event)
                {
                    if (m.getActive())
                    {
                        //Désactivés les bouttons
                        m.ChangeImageNotActive();
                        hits++;
                        l.setText( hits+ "/" + goal);
                        //   ActivateRandomButtons(btnList);
                        if (hits == goal)
                        {
                            DeactivateAllButtons(btnList);
                            StopTimeLine(t);
                            StopTimeLine(t2);
                            new Win(primaryStage);
                        }
                    }
                }
            };
            m.setOnMouseClicked(handler);
        }

    }

    public void ActivateRandomButtons(ArrayList<MS_Button> btnList )
    {
        try
        {
            ArrayList<MS_Button> activeList = new ArrayList<>();
            boolean allActive = false;
            int index = 0;
            while (btnList.get(index).getActive() && index < btnList.size())
            {
                allActive = true;
                ++index;
            }
            if(allActive)
                DeactivateAllButtons(btnList);

            int numberOfButtons = rand.nextInt(3) + 1;
            for (int i = 0; i < numberOfButtons; i++)
            {
                n = rand.nextInt(9);

                while (btnList.get(n).getActive())
                    n = rand.nextInt(9);

                activeList.add(btnList.get(n));
                btnList.get(n).ChangeImageActive();
            }
            closeBtnWithDelay(activeList);
        }
        catch (java.lang.Exception e)
        {
            System.out.print(e.getMessage().toString());
        }
    }



    public void closeBtnWithDelay(ArrayList<MS_Button> btnList ) {

        for(MS_Button btn:btnList)
        {
            final KeyFrame kf1 = new KeyFrame(Duration.seconds(0));
            final KeyFrame kf2 = new KeyFrame(Duration.seconds(1), e -> btn.ChangeImageNotActive());
            final Timeline timeline = new Timeline(kf1, kf2);
            Platform.runLater(timeline::play);
        }
    }

    private GridPane GridSettings(String style, int[] numberColumns, int[] numberRows)
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

    public void StopTimeLine(Timeline t)
    {
        t.stop();
    }
}



