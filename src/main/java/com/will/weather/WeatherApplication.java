package com.will.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.will.weather")
public class WeatherApplication {

   public static void main(String[] args) {
      SpringApplication.run(WeatherApplication.class, args);
   }
}
