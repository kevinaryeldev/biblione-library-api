package com.biblione.library_api.mapper;

import com.biblione.library_api.dto.response.CopyResponse;
import com.biblione.library_api.entity.Copy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CopyMapper {

    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    CopyResponse toResponse(Copy copy);
}
