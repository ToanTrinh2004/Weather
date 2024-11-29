import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
public class WeatherApi {
    public static JSONObject getWeatherData(double latitude, double longitude){
        // extract latitude and longitude data

        // build API request URL with location coordinates
        String urlString = "https://api.open-meteo.com/v1/forecast?" +
                "latitude=" + latitude + "&longitude=" + longitude +
                "&current=temperature_2m,relative_humidity_2m,is_day,weather_code,surface_pressure,wind_speed_10m,apparent_temperature&hourly" +
                "=temperature_2m,relative_humidity_2m,dew_point_2m,weather_code,visibility,precipitation_probability," +
                "wind_speed_10m&daily=weather_code,temperature_2m_max,temperature_2m_min,uv_index_max,precipitation_sum,wind_speed_10m_max&timezone=auto&forecast_days=14";

        try{
            // call api and get response
            HttpURLConnection conn = fetchApiResponse(urlString);

            // check for response status
            // 200 - means that the connection was a success
            if(conn.getResponseCode() != 200){
                System.out.println("Error: Could not connect to API");
                return null;
            }

            // store resulting json data
            StringBuilder resultJson = new StringBuilder();
            Scanner scanner = new Scanner(conn.getInputStream());
            while(scanner.hasNext()){
                // read and store into the string builder
                resultJson.append(scanner.nextLine());
            }

            // close scanner
            scanner.close();

            // close url connection
            conn.disconnect();

            // parse through our data
            JSONParser parser = new JSONParser();
            JSONObject resultJsonObj = (JSONObject) parser.parse(String.valueOf(resultJson));

            // retrieve hourly data
            JSONObject current = (JSONObject) resultJsonObj.get("current");
            // retrieve hourly data
            JSONObject hourly = (JSONObject) resultJsonObj.get("hourly");
            JSONArray hourlyTime = (JSONArray) hourly.get("time");
            JSONArray hourlyTemperature = (JSONArray) hourly.get("temperature_2m");
            JSONArray hourlyRelativeHumidity = (JSONArray) hourly.get("relativehumidity_2m");
            JSONArray hourlyDewPoint = (JSONArray) hourly.get("dew_point_2m");
            JSONArray hourlyRainychance = (JSONArray) hourly.get("precipitation_probability");
            JSONArray hourlyWeathercode = (JSONArray) hourly.get("weather_code");
            JSONArray hourlyPressure = (JSONArray) hourly.get("surface_pressure");
            JSONArray hourlyVisibility = (JSONArray) hourly.get("visibility");
            JSONArray hourlyWindspeed = (JSONArray) hourly.get("wind_speed_10m");
            // daily data
            JSONObject daily = (JSONObject) resultJsonObj.get("daily");
            JSONArray dailyTime = (JSONArray) daily.get("time");
            JSONArray dailyMaxTemperature = (JSONArray) daily.get("temperature_2m_max");
            JSONArray dailyMinTemperature = (JSONArray) daily.get("temperature_2m_min");
            JSONArray dailyWeathercode = (JSONArray) daily.get("weather_code");
            JSONArray dailyRainychance = (JSONArray) daily.get("precipitation_sum");
            JSONArray dailyWindspeed = (JSONArray) daily.get("wind_speed_10m_max");
            JSONArray dailyUv  = (JSONArray) daily.get("uv_index_max");





// Extract data from the current hour's JSON object
            String time = (String) current.get("time");
            boolean status = ((Number) current.get("is_day")).intValue() == 1;
            double temperature = ((Number) current.get("temperature_2m")).doubleValue();
            double feelslike = ((Number) current.get("apparent_temperature")).doubleValue();
            double humidity = ((Number) current.get("relative_humidity_2m")).doubleValue();
            double pressure = ((Number) current.get("surface_pressure")).doubleValue();
            double windspeed = ((Number) current.get("wind_speed_10m")).doubleValue();
            int weatherCondition = ((Number) current.get("weather_code")).intValue();
            double dewpoint = (double) hourlyDewPoint.get(1);
            double visibility = (double) hourlyVisibility.get(1) /1000;
            long rainyChance = (long) hourlyRainychance.get(1);
            double uv = (double) dailyUv.get(1);


// Build the weather JSON data object to be accessed on the frontend
            JSONObject weatherData = new JSONObject();
            weatherData.put("time", time);
            weatherData.put("status", status); // Adding the day/night status
            weatherData.put("temperature", temperature);
            weatherData.put("humidity", humidity);
            weatherData.put("pressure", pressure); // Adding pressure to match JSON structure
            weatherData.put("windspeed", windspeed);
            weatherData.put("weather_condition", weatherCondition);
            weatherData.put("feelslike",feelslike);
            weatherData.put("dewpoint",dewpoint);
            weatherData.put("visibility",visibility);
            weatherData.put("rainyChance",rainyChance);
            weatherData.put("uv",uv);
            // Hourly data
            weatherData.put("HourlyTime", hourlyTime); // Time for each hour
            weatherData.put("HourlyTemperature", hourlyTemperature); // Temperature for each hour
            weatherData.put("HourlyHumidity", hourlyRelativeHumidity); // Humidity for each hour
            weatherData.put("HourlyDewPoint", hourlyDewPoint); // Dewpoint for each hour
            weatherData.put("HourlyRainyChance", hourlyRainychance); // Precipitation probability for each hour
            weatherData.put("HourlyWeatherCode", hourlyWeathercode); // Weather condition for each hour
            weatherData.put("HourlyPressure", hourlyPressure); // Surface pressure for each hour
            weatherData.put("HourlyVisibility", hourlyVisibility); // Visibility for each hour
            weatherData.put("HourlyWindspeed", hourlyWindspeed); // Wind speed for each hour

// Daily data
            weatherData.put("DailyMaxTemperature", dailyMaxTemperature); // Max temperature for each day
            weatherData.put("DailyMinTemperature", dailyMinTemperature); // Min temperature for each day
            weatherData.put("DailyWeatherCode", dailyWeathercode); // Weather condition for each day
            weatherData.put("DailyRainyChance", dailyRainychance); // Precipitation sum for each day
            weatherData.put("DailyWindspeed", dailyWindspeed); // Max wind speed for each day
            weatherData.put("DailyUv", dailyUv); // UV index for each day
            weatherData.put("DailyTime",dailyTime);


            return weatherData;

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
    public static JSONArray getLocationData(String locationName){
        // replace any whitespace in location name to + to adhere to API's request format
        locationName = locationName.replaceAll(" ", "+");

        // build API url with location parameter
        String urlString = "https://geocoding-api.open-meteo.com/v1/search?name=" +
                locationName + "&count=5&language=en&format=json";

        try{
            // call api and get a response
            HttpURLConnection conn = fetchApiResponse(urlString);

            // check response status
            // 200 means successful connection
            if(conn.getResponseCode() != 200){
                System.out.println("Error: Could not connect to API");
                return null;
            }else{
                // store the API results
                StringBuilder resultJson = new StringBuilder();
                Scanner scanner = new Scanner(conn.getInputStream());

                // read and store the resulting json data into our string builder
                while(scanner.hasNext()){
                    resultJson.append(scanner.nextLine());
                }

                // close scanner
                scanner.close();

                // close url connection
                conn.disconnect();

                // parse the JSON string into a JSON obj
                JSONParser parser = new JSONParser();
                JSONObject resultsJsonObj = (JSONObject) parser.parse(String.valueOf(resultJson));

                // get the list of location data the API gtenerated from the lcoation name
                JSONArray locationData = (JSONArray) resultsJsonObj.get("results");
                return locationData;
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        // couldn't find location
        return null;
    }
    // Function to convert a JSONArray to ArrayList<String> based on a specific key



    private static HttpURLConnection fetchApiResponse(String urlString){
        try{
            // attempt to create connection
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // set request method to get
            conn.setRequestMethod("GET");

            // connect to our API
            conn.connect();
            return conn;
        }catch(IOException e){
            e.printStackTrace();
        }

        // could not make connection
        return null;
    }

}
