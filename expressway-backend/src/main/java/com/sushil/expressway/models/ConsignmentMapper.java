package com.sushil.expressway.models;

import org.mapstruct.*;

import com.sushil.expressway.entitys.Consignment;

@Mapper(componentModel = "spring")
public interface ConsignmentMapper {

    // Maps fields from DTO to existing entity, skips nulls
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ConsignmentRequest dto, @MappingTarget Consignment entity);

    // (Optionally) create a new entity from DTO
    Consignment toEntity(ConsignmentRequest dto);

    // And mapping entity to DTO if needed
    ConsignmentRequest toDto(Consignment entity);
}
