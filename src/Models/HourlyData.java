package Models;

import java.util.ArrayList;

public class HourlyData {
    private ArrayList<String> hourlyTime;           // Time for each hour
    private ArrayList<Double> hourlyTemperature;    // Temperature for each hour
    private ArrayList<Double> hourlyHumidity;       // Humidity for each hour
    private ArrayList<Double> hourlyDewPoint;       // Dewpoint for each hour
    private ArrayList<Double> hourlyRainyChance;    // Precipitation probability for each hour
    private ArrayList<Integer> hourlyWeatherCode;   // Weather condition for each hour
    private ArrayList<Double> hourlyPressure;       // Surface pressure for each hour
    private ArrayList<Double> hourlyVisibility;     // Visibility for each hour
    private ArrayList<Double> hourlyWindspeed;      // Wind speed for each hour

    // Constructor to initialize all ArrayLists
    public HourlyData() {
        this.hourlyTime = new ArrayList<>();
        this.hourlyTemperature = new ArrayList<>();
        this.hourlyHumidity = new ArrayList<>();
        this.hourlyDewPoint = new ArrayList<>();
        this.hourlyRainyChance = new ArrayList<>();
        this.hourlyWeatherCode = new ArrayList<>();
        this.hourlyPressure = new ArrayList<>();
        this.hourlyVisibility = new ArrayList<>();
        this.hourlyWindspeed = new ArrayList<>();
    }

    // Getters and Setters for each attribute
    public ArrayList<String> getHourlyTime() {
        return hourlyTime;
    }

    public void setHourlyTime(ArrayList<String> hourlyTime) {
        this.hourlyTime = hourlyTime;
    }

    public ArrayList<Double> getHourlyTemperature() {
        return hourlyTemperature;
    }

    public void setHourlyTemperature(ArrayList<Double> hourlyTemperature) {
        this.hourlyTemperature = hourlyTemperature;
    }

    public ArrayList<Double> getHourlyHumidity() {
        return hourlyHumidity;
    }

    public void setHourlyHumidity(ArrayList<Double> hourlyHumidity) {
        this.hourlyHumidity = hourlyHumidity;
    }

    public ArrayList<Double> getHourlyDewPoint() {
        return hourlyDewPoint;
    }

    public void setHourlyDewPoint(ArrayList<Double> hourlyDewPoint) {
        this.hourlyDewPoint = hourlyDewPoint;
    }

    public ArrayList<Double> getHourlyRainyChance() {
        return hourlyRainyChance;
    }

    public void setHourlyRainyChance(ArrayList<Double> hourlyRainyChance) {
        this.hourlyRainyChance = hourlyRainyChance;
    }

    public ArrayList<Integer> getHourlyWeatherCode() {
        return hourlyWeatherCode;
    }

    public void setHourlyWeatherCode(ArrayList<Integer> hourlyWeatherCode) {
        this.hourlyWeatherCode = hourlyWeatherCode;
    }

    public ArrayList<Double> getHourlyPressure() {
        return hourlyPressure;
    }

    public void setHourlyPressure(ArrayList<Double> hourlyPressure) {
        this.hourlyPressure = hourlyPressure;
    }

    public ArrayList<Double> getHourlyVisibility() {
        return hourlyVisibility;
    }

    public void setHourlyVisibility(ArrayList<Double> hourlyVisibility) {
        this.hourlyVisibility = hourlyVisibility;
    }

    public ArrayList<Double> getHourlyWindspeed() {
        return hourlyWindspeed;
    }

    public void setHourlyWindspeed(ArrayList<Double> hourlyWindspeed) {
        this.hourlyWindspeed = hourlyWindspeed;
    }
}
