
package com.springboot.library_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BookDto(
        @NotBlank(message = "Title cannot be blank")
        String title,

        @NotBlank(message = "Author cannot be blank")
        String author,

        @NotBlank(message = "ISBN cannot be blank")
        String isbn,

        @NotNull(message = "Year cannot be null")
        @Positive(message = "Year must be positive")
        Integer year
) {
}
