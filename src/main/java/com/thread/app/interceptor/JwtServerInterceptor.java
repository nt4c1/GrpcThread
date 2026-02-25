package com.thread.app.interceptor;

import com.thread.app.jwtUtil.JwtUtil;
import io.grpc.*;
import jakarta.inject.Singleton;

@Singleton
public class JwtServerInterceptor implements ServerInterceptor {

    private static final Metadata.Key<String> AUTH_KEY =
            Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER);

    public static final Context.Key<String> USER_CONTEXT =
            Context.key("username");

    private final JwtUtil jwtUtil;


    public JwtServerInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {
        String serviceName = call.getMethodDescriptor().getServiceName();

        if (serviceName.equals("AuthService")) {
            return next.startCall(call, headers);
        }

        String authHeader = headers.get(AUTH_KEY);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            call.close(
                    Status.UNAUTHENTICATED.withDescription("Missing or invalid Authorization header"),
                    new Metadata()
            );
            return new ServerCall.Listener<>() {};
        }

        String token = authHeader.replace("Bearer ", "");

        try {
            String username = jwtUtil.validateAndExtract(token);

            Context context = Context.current()
                    .withValue(USER_CONTEXT, username);

            return Contexts.interceptCall(context, call, headers, next);

        } catch (Exception e) {
            call.close(
                    Status.UNAUTHENTICATED.withDescription("Invalid token"),
                    new Metadata()
            );
            return new ServerCall.Listener<>() {};
        }
    }
}