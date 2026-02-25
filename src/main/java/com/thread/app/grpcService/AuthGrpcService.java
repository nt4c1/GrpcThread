package com.thread.app.grpcService;

import com.thread.app.proto.AuthResponse;
import com.thread.app.proto.AuthServiceGrpc;
import com.thread.app.proto.LoginRequest;
import com.thread.app.proto.RegisterRequest;
import com.thread.app.service.AuthBusinessService;
import io.grpc.stub.StreamObserver;
import jakarta.inject.Singleton;

@Singleton
public class AuthGrpcService extends AuthServiceGrpc.AuthServiceImplBase {

    private final AuthBusinessService authBusinessService;

    public AuthGrpcService(AuthBusinessService authBusinessService) {
        this.authBusinessService = authBusinessService;
    }

    @Override
    public void register(RegisterRequest request,
                         StreamObserver<AuthResponse> responseObserver) {

        String token = authBusinessService.register(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        );

        AuthResponse response = AuthResponse.newBuilder()
                .setToken(token)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void login(LoginRequest request,
                      StreamObserver<AuthResponse> responseObserver) {

        String token = authBusinessService.login(
                request.getUsername(),
                request.getPassword()
        );

        AuthResponse response = AuthResponse.newBuilder()
                .setToken(token)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}