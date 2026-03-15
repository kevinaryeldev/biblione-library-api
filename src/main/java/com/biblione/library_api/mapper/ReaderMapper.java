package com.biblione.library_api.mapper;

import com.biblione.library_api.dto.request.ReaderRequest;
import com.biblione.library_api.dto.response.ReaderResponse;
import com.biblione.library_api.entity.Reader;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReaderMapper {

    ReaderResponse toResponse(Reader reader);

    @Mapping(target = "policy", ignore = true)
    @Mapping(target = "blocked", ignore = true)
    @Mapping(target = "blockedReason", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Reader toEntity(ReaderRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "policy", ignore = true)
    @Mapping(target = "blocked", ignore = true)
    @Mapping(target = "blockedReason", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(ReaderRequest request, @MappingTarget Reader reader);
}
