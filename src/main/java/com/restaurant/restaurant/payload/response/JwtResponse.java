package com.restaurant.restaurant.payload.response;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -5189053744936023257L;
    private String token;
    public JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
