package  Models;
public class DailyData {
    private int maxTmp;      // Maximum temperature
    private int minTmp;      // Minimum temperature
    private String time;     // Time (e.g., date or specific time)
    private double chance;   // Chance (e.g., chance of rain)
    private int condition; // Weather condition (e.g., Sunny, Rainy)
    private boolean status;

    // Constructor
    public DailyData(int maxTmp, int minTmp, String time, double chance, int condition,boolean status) {
        this.maxTmp = maxTmp;
        this.minTmp = minTmp;
        this.time = time;
        this.chance = chance;
        this.condition = condition;
        this.status = status;
    }

    // Getters and Setters
    public int getMaxTmp() {
        return maxTmp;
    }

    public void setMaxTmp(int maxTmp) {
        this.maxTmp = maxTmp;
    }

    public int getMinTmp() {
        return minTmp;
    }

    public void setMinTmp(int minTmp) {
        this.minTmp = minTmp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getChance() {
        return chance;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    // toString() method for displaying object data

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
