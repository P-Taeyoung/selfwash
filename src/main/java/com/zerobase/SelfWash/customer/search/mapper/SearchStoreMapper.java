package com.zerobase.SelfWash.customer.search.mapper;

import com.zerobase.SelfWash.customer.search.dto.SearchMachineDto;
import com.zerobase.SelfWash.customer.search.dto.SearchStoreDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SearchStoreMapper {
  List<SearchStoreDto> searchStore(
      @Param("latitude") double latitude,
      @Param("longitude") double longitude);

}
