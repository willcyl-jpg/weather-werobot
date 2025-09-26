package com.will.weather.middleware;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.databind.JsonNode;

public class WeatherMessage {

   private static final String[] keys = { "天气详情", "最高温度", "最低温度", "日间天气", "夜间天气", "日间风向", "风力等级" };

   private String detailPage;
   private String tempMax;
   private String tempMin;
   private String textDay;
   private String textNight;
   private String windDirDay;
   private String windScaleDay;

   public WeatherMessage() {
   }

   public WeatherMessage parse(JsonNode jsonNode) {
      JsonNode detail = jsonNode.get("fxLink");
      this.detailPage = detail.asText();
      JsonNode daily = jsonNode.get("daily");
      JsonNode firstDay = daily.get(0);
      this.tempMax = firstDay.get("tempMax").asText();
      this.tempMin = firstDay.get("tempMin").asText();
      this.textDay = firstDay.get("textDay").asText();
      this.textNight = firstDay.get("textNight").asText();
      this.windDirDay = firstDay.get("windDirDay").asText();
      this.windScaleDay = firstDay.get("windScaleDay").asText();
      return this;
   }

   public String toMessage() {
      Map<String, String> msg = new HashMap<>();
      putIfNonnull(msg, keys[0], detailPage);
      putIfNonnull(msg, keys[1], tempMax);
      putIfNonnull(msg, keys[2], tempMin);
      putIfNonnull(msg, keys[3], textDay);
      putIfNonnull(msg, keys[4], textNight);
      putIfNonnull(msg, keys[5], windDirDay);
      putIfNonnull(msg, keys[6], windScaleDay);
      StringBuilder res = new StringBuilder("### 今日海淀区天气: \n");
      for (int i = 0; i < keys.length; i++) {
         String val = msg.get(keys[i]);
         res.append(keys[i]).append("::").append(val == null ? "null" : val);
         if (i < keys.length - 1) {
            res.append("\n");
         }
      }
      return res.toString();
   }

   private void putIfNonnull(Map<String, String> map, String key, String value) {
      if (Objects.nonNull(key) && Objects.nonNull(value)) {
         map.put(key, value);
      }
   }
}
