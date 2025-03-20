package com.zerobase.SelfWash.customer.search.service;

import com.zerobase.SelfWash.customer.search.dto.SearchMachineDto;
import com.zerobase.SelfWash.customer.search.dto.SearchStoreDto;
import java.util.List;

public interface SearchStoreService {

  /**
   * 주변 500m 내 있는 매장 검색
   */
  List<SearchStoreDto> searchStore(double latitude, double longitude);

  /**
   * 특정 매장 기계 조회
   */
  List<SearchMachineDto> searchMachine(Long storeId);
}
