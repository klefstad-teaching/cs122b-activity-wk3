package edu.uci.ics.cs122b.activity.wk3.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

// Example request model
public class ExampleRequestModel{

    @JsonProperty(value = "x", required = true)
    private Integer x;
    @JsonProperty(value = "y", required = true)
    private Integer y;

    @JsonCreator
    public ExampleRequestModel(@JsonProperty(value = "x", required = true) Integer x,
                               @JsonProperty(value = "y", required = true) Integer y) {
        this.x = x;
        this.y = y;
    }

    @JsonProperty("x")
    public Integer getX() {
        return x;
    }

    @JsonProperty("y")
    public Integer getY() {
        return y;
    }
}
