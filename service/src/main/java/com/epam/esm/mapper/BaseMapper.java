package com.epam.esm.mapper;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Base Mapper
 */

public interface BaseMapper<E,D>{
   E fromDTOToEntity(D d);

   D fromEntityToDTO(E e);

}
