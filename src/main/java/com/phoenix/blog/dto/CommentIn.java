package com.phoenix.blog.dto;

import jakarta.validation.constraints.NotBlank;

public record CommentIn(@NotBlank(message = "Comment body must not be blank")
                        String body) { }
