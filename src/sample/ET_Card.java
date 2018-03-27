package sample;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ET_Card extends Button{

    private boolean isTurned;
    private boolean isFound;
    private String styleCSS;

    public String getStyleCSS() {
        return styleCSS;
    }

    public boolean isTurned() {
        return isTurned;
    }

    public void setStyleCSS(String styleCSS) {
        this.styleCSS = styleCSS;
    }

    public void setTurned(boolean turned) {
        isTurned = turned;
    }

    public ET_Card(String style)
    {
        this.getStyleClass().add("button");
        this.setId("ET_Card_Back");
        setTurned(false);
        setStyleCSS(style);

        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    setTurned(true);
                    setId(style);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

    }
}
