package com.thread.app.exception;

import io.grpc.*;
import io.micronaut.http.server.exceptions.NotFoundException;
import jakarta.inject.Singleton;

@Singleton
public class GlobalGrpcExceptionHandler {

    public RuntimeException handle(Exception e) {
        return Status.INTERNAL
                .withDescription(e.getMessage())
                .asRuntimeException();
    }

    public Status handleNotFound(NotFoundException e) {
        return Status.NOT_FOUND.withDescription(e.getMessage());
    }

    public Status handleExists(AlreadyExistsException e) {
        return Status.ALREADY_EXISTS.withDescription(e.getMessage());
    }
}
