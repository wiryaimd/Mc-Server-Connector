package com.wiryaimd.mcconnect;

public class DataModel {

    private String ip, port, mcpeport;
    private int number;

    public DataModel(){}

    public DataModel(String ip, String port, int number, String mcpeport) {
        this.ip = ip;
        this.port = port;
        this.number = number;
        this.mcpeport = mcpeport;
    }

    public String getMcpeport() {
        return mcpeport;
    }

    public void setMcpeport(String mcpeport) {
        this.mcpeport = mcpeport;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
