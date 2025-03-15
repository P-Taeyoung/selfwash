package com.zerobase.SelfWash.customer.search_store.service;

import com.zerobase.SelfWash.customer.search_store.dto.SearchStoreDto;
import java.util.List;

public interface SearchStoreService {

  /**
   * 주변 500m 내 있는 매장 검색
   */
  List<SearchStoreDto> searchStore(double latitude, double longitude);

}
