package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.*;

public class PC_Game {

    private String[] words = {"ordinateur","souris","internet","wifi","reseau","hacker","usager","identite","vulnerabilite","pirate","clavier","securite","machine","voleur","web","anonyme"};
    private String mysteryWord = GetRandomWord();
    private Random rand;
    private double screenOppacity = 0;
    private int points = 0;
    private int goal = rand.nextInt(3)+4;
    private int timeRemaining = rand.nextInt(30)+15;

    public PC_Game(Stage primaryStage)
    {

        GridPane grid = GridSettings("passwordCrack", new int[]{25 ,50, 25}, new int[]{25, 25, 25, 25});
        grid.getStyleClass().add("page");
        Label lblWord = new Label();
        lblWord.setText(">FIND : "+ MixLetters(mysteryWord) );

        Label lblPoints = new Label();
        lblPoints.setAlignment(Pos.CENTER);
        lblPoints.setText(points + "/" + goal);

        Label lblTimeRemaining = new Label();
        lblTimeRemaining.setAlignment(Pos.CENTER);
        lblTimeRemaining.setText("Temps:"+timeRemaining);



        ProgressBar d = new ProgressBar();
        ProgressIndicator myProgressIndicator2 = new ProgressIndicator();
        myProgressIndicator2.setProgress(d.getProgress());
        grid.getChildren().add(myProgressIndicator2);
        GridPane.setConstraints(myProgressIndicator2,0,2);

        TimeRemainingTimer(lblTimeRemaining);
        TextField textField = new TextField();

        new AnimationTimer() {
            public  void handle(long currentnanotime)
            {
                if (screenOppacity < 1) {
                    screenOppacity += 0.01;
                    grid.setOpacity(screenOppacity);
                }
                if(CheckAnswer(textField.getText(),mysteryWord))
                {
                    if(!CheckWin()) {
                        mysteryWord = GetRandomWord();
                        lblWord.setText(">FIND : "+ MixLetters(mysteryWord));
                        lblPoints.setText(points +"/" + goal);
                        textField.setText("");
                    }
                    else
                    {
                        this.stop();
                        new Win(primaryStage);
                        lblPoints.setText(points +"/" + goal);
                        lblWord.setText("G A G N E");
                        textField.setText("");
                    }
                }
                if ( timeRemaining == 0)
                {
                    this.stop();
                    new Lose(primaryStage);
                    lblTimeRemaining.setText("P E R D U");
                    textField.setDisable(true);
                    textField.clear();
                }
            }
        }.start();


        textField.setId("PC_textfield");
        lblWord.setId("label_passwordCrack");
        lblPoints.setId("label_passwordCrack");
        lblTimeRemaining.setId("label_passwordCrack");
        GridPane.setConstraints(lblWord,1,1);
        GridPane.setConstraints(textField,1,2);
        GridPane.setConstraints(lblPoints, 2,1);
        GridPane.setConstraints(lblTimeRemaining, 2,0);
        GridPane.setHalignment(lblPoints, HPos.CENTER);
        GridPane.setHalignment(lblTimeRemaining, HPos.CENTER);
        GridPane.setHalignment(lblWord, HPos.CENTER);
        grid.getChildren().addAll(lblWord, textField,lblPoints , lblTimeRemaining);
        primaryStage.getScene().setRoot(grid);
    }


    public String GetRandomWord()
    {
        rand = new Random();
        int temp = rand.nextInt(words.length);
        String word = words[temp];

        while(word.equals(""))
        {
            temp = rand.nextInt(words.length);
            word = words[temp];
        }


        words[temp] = "";

        return word;
    }

    public boolean CheckAnswer(String text,String word)
    {
        if(text.equals(word))
        {
            points++;
            return true;
        }
        else
            return false;
    }

    public boolean CheckWin()
    {
        if(points == goal)
        {
            return true;
        }
        else
            return false;
    }

    public String MixLetters(String word)
    {
        StringBuilder builder = new StringBuilder();
        List<Character> letters = new ArrayList<>();
        for (char letter : word.toCharArray()) {
            letters.add(letter);
        }

        Collections.shuffle(letters);

        for (char letter : letters) {
            builder.append(letter+" ");
        }

        builder.append(" ");

        builder.toString().split(" ");

        if(!(builder.toString().trim().equals(word)))
            return builder.toString();
        else
            MixLetters(word);

        return "";
    }


    public boolean TimeRemainingTimer(Label lblTimeRemaining)
    {
        Timeline countDown = new Timeline(new KeyFrame(Duration.seconds(1), new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                timeRemaining--;
                lblTimeRemaining.setText("Temps:" + timeRemaining);
            }
        }));
        countDown.setCycleCount(timeRemaining);
        countDown.play();
        if (CheckWin())
            countDown.stop();
        if (timeRemaining <= 0)
        {
            return false;
        }
        else
            return true;

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