package com.zerobase.SelfWash.administer_store.service;

import com.zerobase.SelfWash.customer.search.dto.SearchStoreDto;
import java.util.List;

public interface ManageStoreService {

  /**
   * 매장 운영 현황 변경 (현재 운영 현황 반대로 변경)
   */
  void storeOperationChange(Long storeId);


}
