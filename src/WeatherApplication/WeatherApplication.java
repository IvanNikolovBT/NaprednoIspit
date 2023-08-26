import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SimpleTimeZone;

public class WeatherApplication {

    public static void main(String[] args) {
        WeatherDispatcher weatherDispatcher = new WeatherDispatcher();

        CurrentConditionsDisplay currentConditions = new CurrentConditionsDisplay(weatherDispatcher);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherDispatcher);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");
            weatherDispatcher.setMeasurements(Float.parseFloat(parts[0]), Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
            if (parts.length > 3) {
                int operation = Integer.parseInt(parts[3]);
                if (operation == 1) {
                    weatherDispatcher.remove(forecastDisplay);

                }
                if (operation == 2) {
                    weatherDispatcher.remove(currentConditions);
                }
                if (operation == 3) {
                    weatherDispatcher.register(forecastDisplay);
                }
                if (operation == 4) {
                    weatherDispatcher.register(currentConditions);
                }
               
            }
        }
    }
}

class WeatherDispatcher {
    float temperature;
    float humidity;
    float pressure;
    List<Display>displays;
    public WeatherDispatcher() {
    displays=new ArrayList<>();
    }

    public void setMeasurements(float temperature, float humidity, float pressure) {
        setHumidity(humidity);
        setPressure(pressure);
        setTemperature(temperature);
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    static float previousPressure;

    public static void setPreviousPressure(float np) {
        previousPressure = np;
    }
    public void register(Display d) {
        displays.add(d);
    }
    public void remove(Display d){
        displays.remove(d);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();
        for(Display d:displays)
            stringBuilder.append(d);


        return stringBuilder.toString();
    }
}

abstract class Display {
    WeatherDispatcher wd;

    public Display(WeatherDispatcher wd) {
        this.wd = wd;
    }


}

class CurrentConditionsDisplay extends Display {
    public CurrentConditionsDisplay(WeatherDispatcher wd) {
        super(wd);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format("Temperature: %.2fF\n", wd.getTemperature()));
        stringBuilder.append(String.format("Humidity: %.2f%%\n", wd.humidity));
        return stringBuilder.toString();
    }
}

class ForecastDisplay extends Display {
    public ForecastDisplay(WeatherDispatcher wd) {
        super(wd);
    }

    @Override
    public String toString() {
        if(wd.pressure<WeatherDispatcher.previousPressure){
            WeatherDispatcher.setPreviousPressure(wd.pressure);
            return String.format("Forecast: Improving\n");}
        else if (wd.pressure==WeatherDispatcher.previousPressure) {
            return String.format("Forecast: Same\n");}
        else {
            WeatherDispatcher.setPreviousPressure(wd.pressure);
            return String.format("Forecast: Cooler\n");
        }
    }


}