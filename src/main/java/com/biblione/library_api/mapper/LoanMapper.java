package com.biblione.library_api.mapper;

import com.biblione.library_api.dto.response.LoanResponse;
import com.biblione.library_api.entity.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    @Mapping(target = "copyId", source = "copy.id")
    @Mapping(target = "bookId", source = "copy.book.id")
    @Mapping(target = "bookTitle", source = "copy.book.title")
    @Mapping(target = "readerId", source = "reader.id")
    @Mapping(target = "readerName", source = "reader.name")
    LoanResponse toResponse(Loan loan);
}
