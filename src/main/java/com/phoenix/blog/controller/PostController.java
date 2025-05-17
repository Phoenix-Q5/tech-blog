package com.phoenix.blog.controller;

import com.phoenix.blog.config.AuthenticationFacade;
import com.phoenix.blog.config.SecurityConfig;
import com.phoenix.blog.domain.Post;
import com.phoenix.blog.domain.PostVote;
import com.phoenix.blog.domain.User;
import com.phoenix.blog.dto.PostDTO;
import com.phoenix.blog.dto.PostDtoIn;
import com.phoenix.blog.dto.VoteRequest;
import com.phoenix.blog.mapper.PostMapper;
import com.phoenix.blog.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final AuthenticationFacade authenticationFacade;

    @GetMapping
    public Page<PostDTO> recent(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return postService.listRecent(page, size).map(postMapper::toDto);
    }

    @GetMapping("/{slug}")
    public PostDTO bySlug(@PathVariable String slug) {
        return postMapper.toDto(postService.getBySlug(slug));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_AUTHOR')")
    public PostDTO create(@Valid @RequestBody PostDtoIn input) {
        User me = authenticationFacade.currentUser();
        Post entity = postMapper.fromDto(input);
        entity.setAuthor(me);
        return postMapper.toDto(postService.create(entity));
    }

    @PostMapping("/{id}/vote")
    public ScoreDto vote(@PathVariable Long id, @RequestBody VoteRequest vr) {
        User me = authenticationFacade.currentUser();
        int total = postService.vote(me, id, vr.value());
        return new ScoreDto(total);
    }

    public record ScoreDto(int score) { }
}
