package com.example.weather.Forecast.retrofit;

import android.text.Html;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static com.example.weather.mai.MainActivity.DEFAULT_TEMP;

public class main {
    @SerializedName("temp")
    @Expose
    private double temp;
    @SerializedName("feels_like")
    @Expose
    private Double feelsLike;
    @SerializedName("temp_min")
    @Expose
    private Double tempMin;
    @SerializedName("temp_max")
    @Expose
    private Double tempMax;
    @SerializedName("pressure")
    @Expose
    private Integer pressure;
    @SerializedName("sea_level")
    @Expose
    private Integer seaLevel;
    @SerializedName("grnd_level")
    @Expose
    private Integer grndLevel;
    @SerializedName("humidity")
    @Expose
    private Integer humidity;
    @SerializedName("temp_kf")
    @Expose
    private Double tempKf;

    public String getTemp() {
        return getTempAverage(DEFAULT_TEMP);
    }

    public String getTempAverage(String type) {
        int temp2= (int) temp;
        switch (type) {
            case "Kelvin":
                return (String.valueOf(temp2));
            case "Celsius":
                return (String.valueOf((temp2 - 273)) + Html.fromHtml("&#8451;"));
            case "Fahrenheit":
                return String.valueOf((((temp2 - 273) * 1.8) + 32));
            default:
                break;
        }
        return String.valueOf(temp2);
    }

    public Double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(Double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(Integer seaLevel) {
        this.seaLevel = seaLevel;
    }

    public Integer getGrndLevel() {
        return grndLevel;
    }

    public void setGrndLevel(Integer grndLevel) {
        this.grndLevel = grndLevel;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getTempKf() {
        return tempKf;
    }

    public void setTempKf(Double tempKf) {
        this.tempKf = tempKf;
    }
}
