package com.phoenix.blog.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record PostDtoIn(@NotBlank String title,
                        @NotBlank String body,
                        String coverImage,
                        List<String> tags) {
}
