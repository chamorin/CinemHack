package sample;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main extends Application {

    public static ArrayList<Network> networkList = new ArrayList<>();
    public static String currentGame = "None";

    public static boolean CheckConnectedNetwork()
    {
        String s;
        Process p;
        try {
            p = Runtime.getRuntime().exec("iwgetid -r");
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            s = br.readLine();
            p.waitFor();
            p.destroy();
            if(s.equals(FindTargetNetwork(networkList).getESSID()))
                return true;
            else
                return false;
        }catch (Exception e) {}
        return false;
    }

    public static void ResetTargetNetwork(ArrayList<Network> networkList) // remove the current target network
    {
        for(Network network:networkList)
            network.setTarget(false);
    }

    public static Network FindTargetNetwork(ArrayList<Network> networkList) // find and return the network targeted by the user for the next attacks
    {
        for(Network network:networkList)
        {
            if(network.isTarget())
                return network;
        }
        return new Network();
    }

    public static Network FindNetworkByBSSID(ArrayList<Network> networkList,String BSSID) // find a network by providing a mac address
    {
        for(Network network:networkList)
        {
            if(network.getBSSID().equals(BSSID.trim()))
                return network;
        }
        return new Network();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("CinemHack");
        primaryStage.setFullScreen(true);
        primaryStage.setAlwaysOnTop(true);

        new Welcome(primaryStage); // starts the application with the welcome page
    }

    public static void main(String[] args) {
        launch(args);
    }
}
