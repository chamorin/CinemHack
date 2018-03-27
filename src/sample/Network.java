package sample;

import java.util.ArrayList;

public class Network {
    private String BSSID;
    private String ESSID;
    private String channel;
    private boolean isTarget;

    public ArrayList<User> userList = new ArrayList<>();

    public Network(){
        setBSSID("");
        setESSID("");
        setChannel("");
        setTarget(false);
    }

    public Network(String BSSID, String ESSID, String channel)
    {
        setBSSID(BSSID);
        setESSID(ESSID);
        setChannel(channel);
    }

    public String getBSSID() {
        return BSSID;
    }

    public String getESSID() {
        return ESSID;
    }

    public String getChannel() {
        return channel;
    }

    public boolean isTarget() {return isTarget;}

    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }

    public void setESSID(String ESSID) {
        this.ESSID = ESSID;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setTarget(boolean target) {
        isTarget = target;
    }
}
