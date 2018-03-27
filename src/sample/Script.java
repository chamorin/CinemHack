package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class Script {

    private String name;
    private ArrayList<String> arguments = new ArrayList<>();

    public Script(String name) // for simple script execution
    {
        setName(name);
        ExecuteScript();
    }

    public Script(String name, String... args) // for simple script execution that contain arguments
    {
        for (String arg:args) {
            arguments.add(arg);
        }
        setName(name);
        ExecuteScript();
    }

    public Script(ArrayList<Network> networkList, String name) // for script using network list and user list with output handling or not
    {
        setName(name);
        ExecuteScript();
        UseOutput(networkList);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void ExecuteScript() // execute a bash script
    {
        String cmd = Paths.get("").toAbsolutePath().toString()+"/"+getName();
        Process p;
        String[] tab = new String[arguments.size()+1];

        tab[0] = cmd;

        for(int i=1;i<arguments.size()+1;++i)
            tab[i] = arguments.get(i-1);

        try{
            p = Runtime.getRuntime().exec(tab);
            p.waitFor();
            p.destroy();
        }catch (Exception e) {}
    }

    public void UseOutput(ArrayList<Network> networkList) // call functions for output handling depending on the script
    {
        try {
            if(getName().equals("/scripts/script_NetworkScan"))
                NetworkScan(networkList);

            if(getName().equals("/scripts/script_UserScan"))
                UserScan(networkList);

        }catch (Exception e) {}
    }

    public void NetworkScan(ArrayList<Network> networkList) // output handling for the first scan (not connected)
    {
        networkList.clear();
        String l;
        ArrayList<String> lines = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("scripts/NetworkScan-01.csv"));

            br.readLine();
            br.readLine();
            while(!(l=br.readLine()).isEmpty())
            {
                lines.add(l);
                String[] data = l.split(",");
                networkList.add(new Network(data[0].trim(),data[13].trim(),data[3].trim()));
            }
            lines.clear();
            br.readLine();
            while(!(l=br.readLine()).isEmpty())
            {
                lines.add(l);
            }

            for (int i =0; i<networkList.size();++i)
            {
                for (int j=0;j<lines.size();++j)
                {
                    String[] data = lines.get(j).split(",");

                    if(networkList.get(i).getBSSID().equals(data[5].trim()))
                        networkList.get(i).userList.add(new User(data[0]));
                }
            }
            Collections.reverse(networkList);
            new File(System.getProperty("user.dir")+"/scripts/NetworkScan-01.csv").delete();
        }catch (Exception e){}
    }

    public void UserScan(ArrayList<Network> networkList) // output handling for the second scan (connected)
    {
        String l;
        ArrayList<String> lines = new ArrayList<>();

        try {
            Network targetNetwork = FindTargetNetwork(networkList);
            BufferedReader br = new BufferedReader(new FileReader("scripts/UserScan.txt"));
            br.readLine();
            while (!(l = br.readLine()).isEmpty())
            {
                lines.add(l);
            }

            targetNetwork.userList.clear();
            for (int i = 0; i < lines.size(); ++i)
            {
                String line = lines.get(i).trim().replaceAll(" +", " ");
                String[] data = line.split(" ");
                targetNetwork.userList.add(new User(data[0].trim(), data[1].toUpperCase().trim(), data[4].trim().replaceAll(",","")));
            }
        }catch (Exception e){}
    }

    public Network FindTargetNetwork(ArrayList<Network> networkList) // find and return the network targeted by the user for the next attacks
    {
        for(Network network:networkList)
        {
            if(network.isTarget())
                return network;
        }
        return new Network();
    }
}
