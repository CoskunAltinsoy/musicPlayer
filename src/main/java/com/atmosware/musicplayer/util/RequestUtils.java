package com.atmosware.musicplayer.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.Optional;

public enum RequestUtils {
    ;
    private static final String FORWARDED_FOR = "X-Forwarded-For";
    private static Optional<HttpServletRequest> getHttpServletRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(ServletRequestAttributes.class::cast)
                .map(ServletRequestAttributes::getRequest);
    }
    public static String getClientIPAddress() {
        return getHttpServletRequest().map(RequestUtils::getClientIPAddress).orElseThrow();
    }
    public static String getClientIPAddress(final HttpServletRequest httpServletRequest) {
        return Optional.of(httpServletRequest)
                .map(
                        request -> {
                            final var forwardedFor = request.getHeader(FORWARDED_FOR);
                            return Objects.requireNonNullElse(forwardedFor, request.getRemoteAddr());
                        })
                .orElseThrow();
    }
}
