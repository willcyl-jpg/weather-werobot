package com.bitanswer.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.bitanswer.weather")
public class WeatherApplication {

   public static void main(String[] args) {
      SpringApplication.run(WeatherApplication.class, args);
   }
}
