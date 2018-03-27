package sample;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Random;

public class DS_Game {

    private double oppacity = 0;
    private double screenOppacity = 0;
    private int points = 0;
    private int heart = 3;
    private Circle player;
    private Circle goal;
    private int lastXposWall = 110;
    private int lastXposGoal = 1200;
    private boolean upDown = false;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    public DS_Game(Stage primaryStage)
    {
        try{
            Pane pane = new Pane();
            pane.setId("dataStealer");

            ArrayList<Rectangle> walls = new ArrayList<>();

            pane.getStyleClass().add("page");

            player = AddPlayer();
            goal = AddGoal();

            AddWalls(pane,walls);

            pane.getChildren().addAll(player,goal);
            primaryStage.getScene().setRoot(pane);

            new AnimationTimer()
            {
                public void handle(long currentNanoTime){
                    if (screenOppacity < 1) {
                        screenOppacity += 0.01;
                        pane.setOpacity(screenOppacity);
                    }

                    CheckCollisionWallPlayer(walls);

                    if(!CheckAlive())
                    {
                        new Lose(primaryStage);
                        this.stop();
                    }

                    if(CheckCollisionGoalPlayer())
                    {
                        if(!CheckWin())
                        {
                            goal = AddGoal();
                            player = AddPlayer();
                            AddWalls(pane,walls);
                            pane.getChildren().add(goal);
                            pane.getChildren().add(player);
                        }
                        else
                        {
                            new Win(primaryStage);
                            this.stop();
                        }

                    }
                    FadeInOut(player);
                }
            }.start();
        }catch (Exception e)
        {
            new Error(primaryStage,"jeu DataStealer");
        }
    }

    private Circle AddPlayer()
    {
        Random random = new Random();
        Circle player = new Circle();
        player.setRadius(15.0f);
        player.setCursor(Cursor.NONE);
        player.setCenterX(50);
        player.setCenterY(random.nextInt(600) + 100);
        player.setOnMousePressed(circleOnMousePressedEventHandler);
        player.setOnMouseDragged(circleOnMouseDraggedEventHandler);
        player.setFill(Color.web("ff8a00"));
        player.setStroke(Color.web("b16000"));
        player.setStrokeWidth(5);
        return player;
    }

    private Circle AddGoal()
    {
        Random random = new Random();
        Circle goal = new Circle();
        goal.setRadius(30.0f);
        goal.setCenterX(lastXposGoal);
        lastXposGoal += 100;
        goal.setCenterY(random.nextInt(600)+100);
        goal.setFill(Color.web("3fff56"));
        goal.setStroke(Color.web("229630"));
        goal.setStrokeWidth(5);
        return goal;
    }

    private void AddWalls(Pane pane,ArrayList<Rectangle> walls)
    {
        Random random = new Random();
        int nbWall = random.nextInt(3)+5;

        for(int i = 0; i<nbWall;++i)
        {
            Rectangle wall = new Rectangle();
            wall.setHeight(random.nextInt(600)+200);
            wall.setWidth(35);
            wall.setX(lastXposWall);
            lastXposWall +=random.nextInt(100)+75;

            if(upDown)
            {
                wall.setY(0);
                upDown=false;
            }
            else
            {
                wall.setY(random.nextInt(800)+200);
                upDown=true;
            }

            wall.setFill(Color.web("ff0000"));
            walls.add(wall);
            pane.getChildren().add(wall);

        }
    }

    private boolean CheckAlive()
    {
        if(heart==0)
            return false;

        return true;
    }

    private boolean CheckWin()
    {
        if(points == 3)
            return true;
        else
            return false;
    }

    private boolean CheckCollisionWallPlayer(ArrayList<Rectangle> walls) {
        boolean collisionDetected = false;
        boolean isDeadly = false;
        for (Shape static_bloc : walls) {
            if (static_bloc != player) {
                Shape intersect = Shape.intersect(player, static_bloc);
                if (intersect.getBoundsInLocal().getWidth() != -1) {
                    collisionDetected = true;
                    if(static_bloc.getFill().equals(Color.web("ff0000")))
                        isDeadly = true;
                }
            }
        }
        if (collisionDetected) {
            player.setFill(Color.web("ff0000"));
            if(isDeadly)
                --heart;
            return true;
        } else {
            player.setFill(Color.web("ff8a00"));
            return false;
        }
    }


    private boolean CheckCollisionGoalPlayer() {
        boolean collisionDetected = false;
        if (goal != player) {
            Shape intersect = Shape.intersect(player, goal);
            if (intersect.getBoundsInLocal().getWidth() != -1) {
                collisionDetected = true;
            }
        }

        if (collisionDetected)
        {
            goal.setVisible(false);
            player.setVisible(false);
            ++points;
            return true;
        }

        return false;
    }

    EventHandler<MouseEvent> circleOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    orgSceneX = t.getSceneX();
                    orgSceneY = t.getSceneY();
                    orgTranslateX = ((javafx.scene.shape.Circle)(t.getSource())).getTranslateX();
                    orgTranslateY = ((javafx.scene.shape.Circle)(t.getSource())).getTranslateY();
                }
            };

    EventHandler<MouseEvent> circleOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    double offsetX = t.getSceneX() - orgSceneX;
                    double offsetY = t.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;

                    ((javafx.scene.shape.Circle)(t.getSource())).setTranslateX(newTranslateX);
                    ((javafx.scene.shape.Circle)(t.getSource())).setTranslateY(newTranslateY);
                }
            };

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
