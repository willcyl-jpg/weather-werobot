package com.will.weather.representation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RobotContent {
   @JsonProperty(value = "msgtype")
   private String           msgType;
   private RobotContentText text;

   public RobotContent() {
   }

   public RobotContent(String content) {
      this.msgType = "text";
      RobotContentText contentText = new RobotContentText();
      contentText.setContent(content);
      this.text = contentText;
   }
}
