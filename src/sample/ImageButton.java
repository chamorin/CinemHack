package sample;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import static sample.Main.FindTargetNetwork;
import static sample.Main.currentGame;
import static sample.Main.networkList;

public class ImageButton extends Button {

    public ImageButton(String style, Stage primaryStage, int column, int row)
    {
        this.setId(style);
        this.getStyleClass().add("button");
        ImageButton temp = this;
        GridPane.setConstraints(this,column,row);

        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {

                    if(getId().equals("home"))
                    {
                        new Menu(primaryStage);
                    }

                    if(getId().equals("button_MassiveScan"))
                    {
                        currentGame = "MassiveScan";
                        new MS_Game(primaryStage);
                    }

                    if(getId().equals("button_WifiJam"))
                    {
                        currentGame = "WifiJam";
                        new WJ_Game(primaryStage);
                    }

                    if(getId().equals("button_EvilTwin"))
                    {
                        currentGame = "EvilTwin";
                        new ET_Game(primaryStage);
                    }

                    if(getId().equals("button_DataStealer"))
                    {
                        currentGame = "DataStealer";
                        new DS_Game(primaryStage);
                    }

                    if(getId().equals("button_PasswordCrack"))
                    {
                        currentGame = "PasswordCrack";
                        new PC_Game(primaryStage);
                    }

                    if(getId().equals("PC_button_crunch_Go"))
                    {
                        new PC_Crunch(primaryStage);
                    }

                    if(getId().equals("PC_button_wordlist_Go"))
                    {
                        new Script("/scripts/script_PasswordCrack","1",Main.FindTargetNetwork(networkList).getBSSID(),FindTargetNetwork(networkList).getChannel(),FindTargetNetwork(networkList).getESSID(),"0","0","null","scripts/wordlist.txt");
                        new PC_Output(primaryStage);
                    }

                    if(getId().equals("MS_button_Router"))
                    {
                        Main.ResetTargetNetwork(networkList);
                        Main.FindNetworkByBSSID(networkList,temp.getText().split("\n")[1]).setTarget(true);
                        new Menu(primaryStage);
                    }

                    if(getId().equals("WJ_button_User"))
                    {
                        new Script("/scripts/script_WifiJamSingle",Main.FindTargetNetwork(networkList).getBSSID(),temp.getText().split("\n")[1],FindTargetNetwork(networkList).getChannel());
                        new Advice(primaryStage,"wifiJam_Advice");
                    }

                    if(getId().equals("DS_button_User"))
                    {
                        new Script("/scripts/script_DataStealer",temp.getText().split("\n")[0]); //SCRIPT DATASTEALER
                        new DS_Data(primaryStage);
                    }

                    if(getId().equals("WJ_button_Router"))
                    {
                        new Script("/scripts/script_WifiJamAll",Main.FindTargetNetwork(networkList).getBSSID(),FindTargetNetwork(networkList).getChannel());
                        new Advice(primaryStage,"wifiJam_Advice");
                    }

                    if(getId().equals("WJ_button_User_Choice"))
                    {
                        if(Main.CheckConnectedNetwork())
                            new Script(networkList,"/scripts/script_UserScan");

                        new WJ_Users(primaryStage);
                    }

                    if(getId().equals("button_DataStealer_Back"))
                    {
                        new Advice(primaryStage,"dataStealer_Advice");
                    }

                    if(getId().equals("button_WifiJam_Back"))
                    {
                        new Menu(primaryStage);
                    }

                    if(getId().equals("button_EvilTwin_Back"))
                    {
                        new Menu(primaryStage);
                    }

                    if(getId().equals("button_PasswordCrack_Back"))
                    {
                        new Menu(primaryStage);
                    }

                }catch (Exception e){System.out.println(e.getMessage());}
            }
        });
    }
}