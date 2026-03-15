package com.biblione.library_api.mapper;

import com.biblione.library_api.dto.request.CategoryRequest;
import com.biblione.library_api.dto.response.CategoryResponse;
import com.biblione.library_api.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "parentId", source = "parent.id")
    @Mapping(target = "parentName", source = "parent.name")
    CategoryResponse toResponse(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true)
    Category toEntity(CategoryRequest request);

    @Mapping(target = "parent", ignore = true)
    void updateEntity(CategoryRequest request, @MappingTarget Category category);
}
