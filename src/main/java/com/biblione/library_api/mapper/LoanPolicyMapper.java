package com.biblione.library_api.mapper;

import com.biblione.library_api.dto.request.LoanPolicyRequest;
import com.biblione.library_api.dto.response.LoanPolicyResponse;
import com.biblione.library_api.entity.LoanPolicy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LoanPolicyMapper {

    LoanPolicyResponse toResponse(LoanPolicy policy);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    LoanPolicy toEntity(LoanPolicyRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(LoanPolicyRequest request, @MappingTarget LoanPolicy policy);
}
