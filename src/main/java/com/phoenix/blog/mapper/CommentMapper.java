package com.phoenix.blog.mapper;

import com.phoenix.blog.domain.Comment;
import com.phoenix.blog.dto.CommentDTO;
import com.phoenix.blog.dto.CommentIn;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "author.name", target = "authorName")
    CommentDTO toDto(Comment comment);

    @Mapping(target = "id",         ignore = true)
    @Mapping(target = "author",     ignore = true)
    @Mapping(target = "post",       ignore = true)
    @Mapping(target = "createdAt",  ignore = true)
    Comment fromIn(CommentIn in);
}