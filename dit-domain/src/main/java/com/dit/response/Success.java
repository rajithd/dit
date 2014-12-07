package com.dit.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Success {

    public static final Success SUCCESS_CODE = new Success();
    private static final String SUCCESS_KEYWORD = "success";
    private String description;
    private Boolean ok = Boolean.TRUE;
    private Object payLoad;
    private String additionalInfo;

    public Success() {
    }

    public Success(Object payload) {
        this.payLoad = payload;
    }

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean value) {
        this.ok = value;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String value) {
        this.additionalInfo = value;
    }

    public String getDescription() {
        return (ok) ? SUCCESS_KEYWORD : "";
    }

    public void setDescription(String description) {
        this.description = (ok) ? SUCCESS_KEYWORD : "";
    }

    public Object getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(Object payLoad) {
        this.payLoad = payLoad;
    }
}
