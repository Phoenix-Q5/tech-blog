package com.phoenix.blog.controller;

import com.phoenix.blog.config.AuthenticationFacade;
import com.phoenix.blog.domain.User;
import com.phoenix.blog.dto.CommentDTO;
import com.phoenix.blog.mapper.CommentMapper;
import com.phoenix.blog.service.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService service;
    private final CommentMapper mapper;
    private final AuthenticationFacade auth;

    @GetMapping("/{postId}")
    public Page<CommentDTO> list(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return service.list(postId, page, size).map(mapper::toDto);
    }

    @PostMapping("/{postId}")
    public CommentDTO comment(
            @PathVariable Long postId,
            @RequestBody @Valid CommentIn body) {
        User me = auth.currentUser();
        return mapper.toDto(service.add(me, postId, body.body()));
    }

    public record CommentIn(@NotBlank String body) { }
}
