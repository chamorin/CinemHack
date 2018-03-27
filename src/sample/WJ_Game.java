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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class WJ_Game {
    private double screenOppacity = 0;
    private String Word = GetRandomWord();
    static private Random rand;
    private int points = 0;
    private int goal = rand.nextInt(5)+5;
    private int timeRemaining=0;
    private TextArea textArea = new TextArea();
    private int indexcount =0;
    static private String[] words = {"url","join","alpha","beta","string","access","byte"};
    static private String[] MediumWords = {"network","connection","private","protected","binary","struct","driver"};
    static private String[] Hardwords = {"datahandler","monitoring","handshake","multicast","unsigned","constructor","passwordkey"};
    static private String[] Functions =
            {
                    "while (system){ \n if (reset > 3XLUwH && reset < MaKJ)\n" +
                            " {\n" +
                            "  add(ys0, UqZ2m0m5);\n" +
                            "  break;\n" +
                            " }\n" +
                            "}\n\n"
                    ,
                    "function user(2suzP)" +
                            "{\n" +
                            " var anon = anon[wlan0];\n"+
                            " join = Xf8ixZxv;\n" +
                            " return write;\n" +
                            "}\n\n"
                    ,

                    "while (net) {\n"+
                            "if (get > lfz26DxE && get < rUv6ih) " +
                            "{\n"+
                            " return ESSID; \n"+
                            "}\n\n"

                    ,
                    "if (user == 8TSxMtS){\n"+
                            " for (var file = 0; file < jnz ; file ++){ \n"+
                            "  list(json.liste[4PS2TS2]);\n"+
                            " }\n"+
                            "}\n\n"
                    ,
                    "crunch 10 10 -t @@@@@@0728 -o /root/birthdaywordlist.lst\n\n"
                    ,
                    "airodump-ng -c 10 --bssid 00:14:BF:E0:E8:D5 -w /root/Desktop/ mon0\n\n"
                    ,
                    "function thread()BoJon {\n" +
                            " var userid = userid[ROQEFH64];\n" +
                            " filedir = nsiw;\n" +
                            " return log;\n"+
                            "}\n\n"
                    ,
                    "var target = Networklist[EDG32].getEssid()\n\n"
            };

    public WJ_Game(Stage primaryStage)
    {
        try{
            //GRID
            GridPane grid = GridSettings("wifiJam", new int[]{30,  50, 24}, new int[]{25, 75, 25});
            grid.getStyleClass().add("page");
            grid.setOpacity(0);
            //WORD
            Label lblWord = new Label();
            lblWord.setText(">ENTER: "+ Word);
            // TIME REMAINING
            Label lblTimeRemaining = new Label();
            lblTimeRemaining.setAlignment(Pos.CENTER);
            setTimeRemaining(goal);
            lblTimeRemaining.setText("Temps:"+ timeRemaining);
            //SCROLL PANE
            textArea.setEditable(false);
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(textArea);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);

            //PROGRESS BAR
            ProgressBar timeBar = new ProgressBar();
            timeBar.setProgress(0f);
            timeBar.setPrefWidth(Double.MAX_VALUE);

            TimeRemainingTimer(lblTimeRemaining);

            TextField textField = new TextField();
            textField.prefWidthProperty().bind(primaryStage.widthProperty().divide(3));

            new AnimationTimer() {
                public  void handle(long currentnanotime)
                {
                    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                    if(CheckAnswer(textField.getText(),Word,timeBar) )
                    {
                        if(!CheckWin()) {
                            if (GetDifficulty(timeBar) == 0)
                                Word = GetRandomWord();

                            if (GetDifficulty(timeBar) == 1)
                                Word = GetRandomMediumWord();

                            if (GetDifficulty(timeBar) == 2)
                                Word = GetRandomHardWord();

                            lblWord.setText(">"+ Word);
                            textField.setText("");
                        }
                        else
                        {
                            lblWord.setText("G A G N E");
                            textField.setText("");
                            new Win(primaryStage);
                        }
                    }
                    if (screenOppacity < 1) {
                        screenOppacity += 0.01;
                        grid.setOpacity(screenOppacity);
                    }
                    if (points == goal)
                        this.stop();
                    if (timeRemaining == 0)
                    {
                        lblWord.setText("P E R D U");
                        textField.setDisable(true);
                        textField.clear();
                        new Lose(primaryStage);
                        this.stop();
                    }
                }
            }.start();

            textArea.setWrapText(true);
            textField.setId("WJ_textfield");
            lblWord.setId("WJ_label");
            lblTimeRemaining.setId("WJ_label");
            timeBar.setId("WJ_progressbar");
            scrollPane.setId("WJ_scrollpane");
            textArea.setId("WJ_textarea");

            GridPane.setConstraints(lblWord,0,2);
            GridPane.setConstraints(textField,1,2);
            GridPane.setConstraints(lblTimeRemaining, 2,0);
            GridPane.setConstraints(scrollPane, 1,1);
            GridPane.setConstraints(timeBar , 1,0);
            GridPane.setHalignment(lblTimeRemaining, HPos.CENTER);
            GridPane.setHalignment(lblWord, HPos.CENTER);
            GridPane.setHalignment(timeBar,HPos.CENTER);

            grid.getChildren().addAll(lblWord, textField,textArea, lblTimeRemaining,scrollPane, timeBar);
            primaryStage.getScene().setRoot(grid);

        }catch (Exception e)
        {
            new Error(primaryStage,"jeu WifiJam");
        }
    }


    static public String GetRandomWord()
    {
        rand = new Random();
        int i = rand.nextInt(words.length);
        String word = words[i];
        while(word.equals(""))
        {
            i = rand.nextInt(words.length);
            word = words[i];
        }
        words[i] = "";

        return word;
    }


    static public String GetRandomMediumWord()
    {
        rand = new Random();
        int i = rand.nextInt(MediumWords.length);
        String word = MediumWords[i];
        while(word.equals(""))
        {
            i = rand.nextInt(MediumWords.length);
            word = MediumWords[i];
        }
        MediumWords[i] = "";
        return word;
    }


    static public String GetRandomHardWord()
    {
        rand = new Random();
        int i = rand.nextInt(Hardwords.length);
        String word = Hardwords[i];
        while(word.equals(""))
        {
            i = rand.nextInt(Hardwords.length);
            word = Hardwords[i];
        }
        Hardwords[i] = "";
        return word;
    }

    public boolean CheckAnswer(String text,String word , ProgressBar p )
    {
        if(text.equals(word))
        {
            points++;
            SetProgress(p);
            int i = rand.nextInt(Functions.length);
            if (Functions[i]!= "")
                PrintTextWithDelay(Functions[i]);
            return true;
        }
        else
            return false;
    }

    public boolean CheckWin()
    {
        if(points == goal)
            return true;
        else
            return false;
    }


    public boolean TimeRemainingTimer(Label lblTimeRemaining)
    {
        Timeline countDown = new Timeline(new KeyFrame(Duration.seconds(1), new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                timeRemaining--;
                lblTimeRemaining.setText("Temps:"+ timeRemaining);
            }
        }));
        countDown.setCycleCount(timeRemaining);
        countDown.play();
        if (CheckWin())
            countDown.stop();
        if (timeRemaining <= 0)
            return false;
        else
            return true;

    }

    public void SetProgress(ProgressBar progressBar)
    {
        float progressIndicator = (points * 1f) / goal;
        progressBar.setProgress(progressIndicator);
    }

    public int GetDifficulty(ProgressBar progressBar)
    {
        if (progressBar.getProgress() <= 0.3)
            return  0;
        else if (progressBar.getProgress()>= 0.3&& progressBar.getProgress() < 0.6 )
            return 1;
        else if (progressBar.getProgress() >= 0.6  && progressBar.getProgress() <= 1f)
            return  2;
        else
            return  0;
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


    public  void setTimeRemaining(int wordcount)
    {
        if (wordcount <= 6)
            timeRemaining = rand.nextInt(5)+12;
        if (wordcount == 7)
            timeRemaining = 17;
        if (wordcount >7)
            timeRemaining = rand.nextInt(3)+17;
    }

    public void PrintTextWithDelay(String text)
    {
        String[] words = text.split(" ");
        indexcount = text.length();

        new Thread(() -> {
            try
            {
                for (String s : words) {
                    textArea.appendText(s + " ");
                    TimeUnit.MILLISECONDS.sleep(30);
                }
            }
            catch (Exception e)
            {
            }
        }).start();
    }
}
