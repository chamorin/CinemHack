package sample;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;


public class ET_Game {

    private double oppacity = 0;
    private double screenOppacity = 0;
    private int points = 0;
    private int heart = 3;

    ArrayList<ET_Card> cardsList = new ArrayList<ET_Card>();

    public ET_Game(Stage primaryStage)
    {
        try{
            GridPane grid = GridSettings("evilTwin", new int[]{30,30,30}, new int[]{20,40,40});
            grid.getStyleClass().add("page");
            grid.setOpacity(0);

            ProgressBar lifeBar = new ProgressBar();
            lifeBar.setProgress(1f);
            lifeBar.setPrefWidth(Double.MAX_VALUE);
            lifeBar.setId("ET_progressbar");
            GridPane.setConstraints(lifeBar, 1, 0);

            ET_Card card1 = new ET_Card("ET_Card1");
            ET_Card card2 = new ET_Card("ET_Card1");

            ET_Card card3 = new ET_Card("ET_Card2");
            ET_Card card4 = new ET_Card("ET_Card2");

            ET_Card card5 = new ET_Card("ET_Card3");
            ET_Card card6 = new ET_Card("ET_Card3");

            Collections.addAll(cardsList,card1,card2,card3,card4,card5,card6);

            grid.getChildren().addAll(card1,card2,card3,card4,card5,card6,lifeBar);

            PlaceCards(cardsList);

            primaryStage.getScene().setRoot(grid);

            new AnimationTimer()
            {
                public void handle(long currentNanoTime){
                    if (screenOppacity < 1) {
                        screenOppacity += 0.01;
                        grid.setOpacity(screenOppacity);
                    }


                    CheckValidate(primaryStage,lifeBar);
                    FadeInOut(card1, card2, card3, card4, card5, card6);
                }
            }.start();
        }catch (Exception e)
        {
            new Error(primaryStage,"jeu EvilTwin");
        }
    }

    private void PlaceCards (ArrayList<ET_Card> cardsList)
    {
        Collections.shuffle(cardsList);

        int index = 0;
        for(int col = 0; col<3;++col)
        {
            for(int row = 1; row<3;++row)
            {
                GridPane.setConstraints(cardsList.get(index), col, row);
                ++index;
            }
        }
    }

    private void ResetCardState()
    {
        for(ET_Card card : cardsList)
        {
            card.setTurned(false);
            card.setId("ET_Card_Back");
        }
    }

    private void CheckValidate(Stage primaryStage,ProgressBar lifebar)
    {
        ArrayList<ET_Card> turnedCards = new ArrayList<ET_Card>();
        if (CheckTurned() == 2) {
            for (ET_Card card : cardsList) {
                if (card.isTurned())
                    turnedCards.add(card);
            }

            if (turnedCards.get(0).getStyleCSS().equals(turnedCards.get(1).getStyleCSS())) {
                ++points;
                for (ET_Card card : turnedCards) {
                    card.setVisible(false);
                }
            }
            else
            {
                --heart;
                lifebar.setProgress(lifebar.getProgress()-1/3f);
            }
            if(heart == 0)
                new Lose(primaryStage);
            if(CheckWin())
                new Win(primaryStage);
            ResetCardState();
        }
    }

    private boolean CheckWin()
    {
        if(points == 3 && heart > 0)
            return true;
        else
            return false;
    }

    private int CheckTurned()
    {
        int numberCardTurned = 0;
        for(ET_Card card : cardsList)
        {
            if(card.isTurned())
                ++numberCardTurned;
        }
        return numberCardTurned;
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
