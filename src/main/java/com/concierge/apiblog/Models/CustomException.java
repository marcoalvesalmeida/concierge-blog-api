package com.concierge.apiblog.Models;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class CustomException implements Serializable {
    private String status;
    private String message;
    private Date date;

    public CustomException() {
        this.date = new Date();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date.toString();
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomException that = (CustomException) o;
        return Objects.equals(status, that.status) && message.equals(that.message) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, message, date);
    }

    @Override
    public String toString() {
        return "CustomException{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", date=" + date +
                '}';
    }
}
