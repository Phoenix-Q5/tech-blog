package com.phoenix.blog.dto;

import java.time.Instant;

public record CommentDTO(
        Long id,
        String body,
        Instant createdAt,
        String authorName,
        String authorAvatarUrl
) {}
