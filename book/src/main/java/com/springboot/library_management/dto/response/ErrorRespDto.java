package com.springboot.library_management.dto.response;

import java.time.Instant;

public record ErrorRespDto(
        String message,
        String comments,
        Instant timestamp) {
}