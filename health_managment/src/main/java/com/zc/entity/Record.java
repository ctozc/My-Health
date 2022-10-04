package com.zc.entity;

public class Record {
  private Long id;
  private Long userId;
  private String weight;
  private String temperature;
  private String bloodPressure;
  private String textualNote;
  private String createTime;

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getWeight() {
    return weight;
  }

  public void setWeight(String weight) {
    this.weight = weight;
  }

  public String getTemperature() {
    return temperature;
  }

  public void setTemperature(String temperature) {
    this.temperature = temperature;
  }

  public String getBloodPressure() {
    return bloodPressure;
  }

  public void setBloodPressure(String bloodPressure) {
    this.bloodPressure = bloodPressure;
  }

  public String getTextualNote() {
    return textualNote;
  }

  public void setTextualNote(String textualNote) {
    this.textualNote = textualNote;
  }

  @Override
  public String toString() {
    return "Record{" +
            "userId=" + userId +
            ", weight='" + weight + '\'' +
            ", temperature='" + temperature + '\'' +
            ", bloodPressure='" + bloodPressure + '\'' +
            ", textualNote='" + textualNote + '\'' +
            '}';
  }
}