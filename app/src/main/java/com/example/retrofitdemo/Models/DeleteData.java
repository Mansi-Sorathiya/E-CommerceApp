package com.example.retrofitdemo.Models;


public class DeleteData
{

    Integer connection;
    Integer result;

    public DeleteData(Integer connection, Integer result) {
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

    public void setResult(Integer result) {
        this.result = result;
    }
}
