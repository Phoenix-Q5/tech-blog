package com.phoenix.blog.dto;

import jakarta.validation.constraints.NotNull;

public record VoteRequest(
        @NotNull(message = "Vote value must be provided")
        Short value
) {}
