package com.biblione.library_api.mapper;

import com.biblione.library_api.dto.request.BookRequest;
import com.biblione.library_api.dto.response.BookResponse;
import com.biblione.library_api.entity.Book;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface BookMapper {

    BookResponse toResponse(Book book);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "copies", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Book toEntity(BookRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "copies", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(BookRequest request, @MappingTarget Book book);
}
