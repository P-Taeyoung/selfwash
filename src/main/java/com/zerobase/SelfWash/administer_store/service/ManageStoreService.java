package com.zerobase.SelfWash.administer_store.service;

import com.zerobase.SelfWash.customer.search_store.dto.SearchStoreDto;
import java.util.List;

public interface ManageStoreService {

  /**
   * 매장 운영 현황 변경 (현재 운영 현황 반대로 변경)
   */
  void storeOperationChange(Long storeId);

  /**
   * 설정 위치 주변 500M 내 매장 조회 (레디스에서 정보 조회)
   */
  List<SearchStoreDto> getNearbyStoresWithDetails(double latitude, double longitude);

}
