/*
package com.thread.app.interceptor;

import com.thread.app.jwtUtil.JwtUtil;
import io.grpc.*;
import jakarta.inject.Singleton;

@Singleton
public class JwtServerInterceptor implements ServerInterceptor {
    private final JwtUtil jwtUtil;

    public JwtServerInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall
            (ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {

        String token = headers.get(Metadata.Key.of("token", Metadata.ASCII_STRING_MARSHALLER));

        if (token == null) {
            call.close(Status.UNAUTHENTICATED.withDescription("token is null."), headers);
            return new ServerCall.Listener<>() {};
        }

        try{
            jwtUtil.validate(token.replace("Bearer ", ""));
        }catch (Exception e){
            call.close(Status.UNAUTHENTICATED.withDescription(e.getMessage()), headers);
            return new ServerCall.Listener<>() {};
        }
        return next.startCall(call, headers);
    }
}
*/
