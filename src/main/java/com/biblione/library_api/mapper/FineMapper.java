package com.biblione.library_api.mapper;

import com.biblione.library_api.dto.response.FineResponse;
import com.biblione.library_api.entity.Fine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FineMapper {

    @Mapping(target = "loanId", source = "loan.id")
    @Mapping(target = "readerId", source = "reader.id")
    @Mapping(target = "readerName", source = "reader.name")
    FineResponse toResponse(Fine fine);
}
