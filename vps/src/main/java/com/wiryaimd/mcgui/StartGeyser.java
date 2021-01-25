package com.wiryaimd.mcgui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class StartGeyser {

    private Process process;

    private ArrayList<Process> arrgey;
    public static final String GEYSER_LOCATION = "/root/mymcgui/wiryaimd_mcgui";

    public ArrayList<Process> getInstance() {
        if (arrgey != null) {
            return arrgey;
        } else {
            arrgey = new ArrayList<>();
            return arrgey;
        }
    }

    public void start(String ip, String port, int size, String mcpeport) throws IOException {
        System.out.println("masuk write");
        writeConfig(ip, port, size, mcpeport);
        arrgey = getInstance();

        System.out.println("starting ip: " + ip + "\nport: " + port);
        process = new ProcessBuilder("java", "-jar", GEYSER_LOCATION + "/Geyser.jar").start();
        arrgey.add(process);
        System.out.println("size: " + arrgey.size());

    }

    public void stop(int position) {
        arrgey = getInstance();
        arrgey.get(position).destroyForcibly();
    }

    public void writeConfig(String ip, String port, int size, String mcpeport) throws IOException {

        File file = new File("/root/mymcgui/wiryaimd_mcgui" + "/config.yml");
        FileReader freader = new FileReader(file);
        BufferedReader reader = new BufferedReader(freader);

        StringBuffer buffer1 = new StringBuffer();
        String line = reader.readLine();
        while(line != null){
            buffer1.append(line + "\n");
            line = reader.readLine();
        }

        String config = buffer1.toString();
        String[] arrconfig = config.split("\n", -1);
        StringBuffer buffer2 = new StringBuffer();

        boolean skip = false;
        for(int i = 0; i < arrconfig.length; i++){

            if(i == 14){
                arrconfig[i] = "  port: " + mcpeport + "\n";
                buffer2.append(arrconfig[i]);
                skip = true;
            }

            if(i == 24){
                arrconfig[i] = "  address: " + ip + "\n";
                buffer2.append(arrconfig[i]);
                skip = true;
            }

            if(i == 26){
                arrconfig[i] = "  port: " + port + "\n";
                buffer2.append(arrconfig[i]);
                skip = true;
            }

            if(!skip){
                arrconfig[i] = arrconfig[i] + "\n";
                buffer2.append(arrconfig[i]);
            }

            skip = false;
        }

        System.out.println("masuk write 2");

        FileWriter writer = new FileWriter("/root/mymcgui/wiryaimd_mcgui" + "/config.yml");
        writer.write(buffer2.toString());
        writer.flush();
        System.out.println("masuk write 3");

        freader.close();
        writer.close();
        reader.close();

        System.out.println("masuk write 4");

    }


}