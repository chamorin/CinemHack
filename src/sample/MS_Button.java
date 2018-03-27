package sample;


import javafx.scene.control.Button;

public class MS_Button extends  Button
{
    private final String Style = "-fx-background-color: transparent;";
    public boolean active = false;
    public void setActive(boolean active)
    {
        this.active = active;
    }
    public boolean getActive()
    {
        return active;
    }

    public void ChangeImageActive()
    {
        setId("MS_ButtonClick");
        setActive(true);

    }

    public void ChangeImageNotActive( )
    {
        setId("MS_Button");
        setActive(false);
    }

    public MS_Button(boolean active)
    {
        setStyle(Style);
        setActive(active);
        setId("MS_Button");
        getStyleClass().add("button");
    }
}


