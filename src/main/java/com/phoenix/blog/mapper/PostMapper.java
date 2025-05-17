package com.phoenix.blog.mapper;

import com.phoenix.blog.domain.Post;
import com.phoenix.blog.domain.Tag;
import com.phoenix.blog.dto.PostDTO;
import com.phoenix.blog.dto.PostDtoIn;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(source = "author.name",          target = "authorName")
    @Mapping(source = "author.avatarUrl",    target = "authorAvatarUrl")
    @Mapping(source = "tags",                target = "tags", qualifiedByName = "tagsToNames")
    PostDTO toDto(Post post);

    @Mapping(target = "id",           ignore = true)
    @Mapping(target = "slug",         ignore = true)
    @Mapping(target = "author",       ignore = true)
    @Mapping(target = "upVotes",      ignore = true)
    @Mapping(target = "downVotes",    ignore = true)
    @Mapping(target = "publishedAt",  ignore = true)
    @Mapping(target = "updatedAt",    ignore = true)
    @Mapping(target = "tags",         ignore = true)
    Post toEntity(PostDtoIn dto);

    @Named("tagsToNames")
    default List<String> tagsToNames(Set<Tag> tags) {
        return tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
    }

    default Post fromDto(PostDtoIn dto) {
        return toEntity(dto);
    }
}
