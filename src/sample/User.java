package sample;

public class User {
    private String machine;
    private String ipAdresse;
    private String macAdresse;

    public User(String macAdresse)
    {
        setMacAdresse(macAdresse);
    }

    public User(String ipAdresse, String macAdresse, String machine)
    {
        setIpAdresse(ipAdresse);
        setMacAdresse(macAdresse);
        setMachine(machine);
    }

    public String getMachine() {
        return machine;
    }

    public String getIpAdresse() {
        return ipAdresse;
    }

    public String getMacAdresse() {
        return macAdresse;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public void setIpAdresse(String ipAdresse) {
        this.ipAdresse = ipAdresse;
    }

    public void setMacAdresse(String macAdresse) {
        this.macAdresse = macAdresse;
    }
}
