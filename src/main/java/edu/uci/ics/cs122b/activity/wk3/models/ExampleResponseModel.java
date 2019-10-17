package edu.uci.ics.cs122b.activity.wk3.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

// Example response model
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExampleResponseModel{

    @JsonProperty(value = "resultCode", required = true)
    private int resultCode;

    @JsonProperty(value = "message", required = true)
    private String message;

    @JsonProperty(value = "sum")
    private Integer sum;

    @JsonCreator
    public ExampleResponseModel(@JsonProperty(value = "resultCode", required = true) int resultCode,
                                @JsonProperty(value = "message", required = true) String message,
                                @JsonProperty(value = "sum") Integer sum) {
        this.resultCode = resultCode;
        this.message = message;
        this.sum = sum;
    }

    @JsonProperty("resultCode")
    public int getResultCode(){
        return resultCode;
    }

    @JsonProperty("message")
    public String getMessage(){
        return message;
    }

    @JsonProperty("sum")
    public Integer getSum() {
        return sum;
    }
}
