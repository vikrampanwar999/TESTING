package com.example.websocket.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ResultWrapper {
private Result result;

public Result getResult() {
	return result;
}

public void setResult(Result result) {
	this.result = result;
}

}
