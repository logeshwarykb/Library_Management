
package com.springboot.library_management.dto;

import java.util.Date;

public record TokenDto(
        String token,
        Date expiration,
        String timestamp) {
}
