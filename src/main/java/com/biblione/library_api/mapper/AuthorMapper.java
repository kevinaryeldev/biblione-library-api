package com.biblione.library_api.mapper;

import com.biblione.library_api.dto.request.AuthorRequest;
import com.biblione.library_api.dto.response.AuthorResponse;
import com.biblione.library_api.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorResponse toResponse(Author author);
    Author toEntity(AuthorRequest request);
    void updateEntity(AuthorRequest request, @MappingTarget Author author);
}
