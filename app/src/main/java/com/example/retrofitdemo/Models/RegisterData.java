package com.example.retrofitdemo.Models;


public class RegisterData
{

    Integer connection;
    Integer result;

    public RegisterData(Integer connection, Integer result) {
        this.connection = connection;
        this.result = result;
    }

    public Integer getConnection() {
        return connection;
    }

    public void setConnection(Integer connection) {
        this.connection = connection;
    }

    public Integer getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "RegisterData{" +
                "connection=" + connection +
                ", result=" + result +
                '}';
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
