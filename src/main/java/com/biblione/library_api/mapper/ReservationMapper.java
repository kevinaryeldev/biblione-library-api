package com.biblione.library_api.mapper;

import com.biblione.library_api.dto.response.ReservationResponse;
import com.biblione.library_api.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    @Mapping(target = "readerId", source = "reader.id")
    @Mapping(target = "readerName", source = "reader.name")
    ReservationResponse toResponse(Reservation reservation);
}
