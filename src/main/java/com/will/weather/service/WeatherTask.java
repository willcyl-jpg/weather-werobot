package com.will.weather.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WeatherTask {

   private WeatherService weatherService;

   @Autowired
   public void setWeatherService(WeatherService weatherService) {
      this.weatherService = weatherService;
   }

   @Scheduled(cron = "0 0 6 * * ?", zone = "Asia/Shanghai")
   public void dailyCall() {
      // weatherService.sendWeatherToRobot();
   }
}
