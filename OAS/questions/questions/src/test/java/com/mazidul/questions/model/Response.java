package com.mazidul.questions.model;

import lombok.Data;

@Data  // Lombok annotation to generate boilerplate code like getters, setters, toString, equals, and hashCode.
public class Response {

    // Unique identifier for the question.
    private Integer id;

    // The user's response to the question.
    private String response;

    // Default constructor required for creating an empty instance.
    public Response() {
    }

    /**
     * Parameterized constructor to create a fully initialized Response instance.
     *
     * @param id       The unique identifier of the question.
     * @param response The user's response to the question.
     */
    public Response(Integer id, String response) {
        this.id = id;
        this.response = response;
    }

    // Getter and setter methods (Lombok's @Data generates these automatically).

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
