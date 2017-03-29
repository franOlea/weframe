package com.weframe.controllers.errors;

import org.apache.commons.lang3.Validate;

public class Error {

    private String title;
    private String detail;

    public Error() {
    }

    public Error(final String title, final String detail) {
        Validate.notBlank(title, "The title cannot be blank.");
        Validate.notBlank(detail, "The detail cannot be blank.");

        this.title = title;
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Error{" +
                "title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }

}
