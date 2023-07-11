package com.digital.DigitaBooking.models.dtos;

import com.amazonaws.Request;
import com.amazonaws.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class Response extends ResponseEntity {

    public Response(HttpStatusCode status) {
        super(status);
    }

    public Response(Object body, HttpStatusCode status) {
        super(body, status);
    }


}
