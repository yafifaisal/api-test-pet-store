package utils;

import io.restassured.response.Response;

public class ApiResponse<T> {
    private final T data;
    private final Response response;

    public ApiResponse(T data, Response response) {
        this.data = data;
        this.response = response;
    }

    public T getData() {
        return data;
    }

    public Response getResponse() {
        return response;
    }

    public int getStatusCode() {
        return response.getStatusCode();
    }

    public String getBodyAsString() {
        return response.getBody().asPrettyString();
    }

}