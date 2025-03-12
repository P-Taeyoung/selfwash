package com.zerobase.SelfWash.customer.search_store.mapper;

import com.zerobase.SelfWash.customer.search_store.dto.SearchStoreDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SearchStoreMapper {
  List<SearchStoreDto> searchStore(
      @Param("latitude") double latitude,
      @Param("longitude") double longitude);

}
