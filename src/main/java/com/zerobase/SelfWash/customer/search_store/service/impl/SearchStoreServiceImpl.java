package com.zerobase.SelfWash.customer.search_store.service.impl;

import com.zerobase.SelfWash.administer_store.service.ManageStoreService;
import com.zerobase.SelfWash.administer_store.service.impl.ManageStoreServiceImpl;
import com.zerobase.SelfWash.customer.search_store.dto.SearchStoreDto;
import com.zerobase.SelfWash.customer.search_store.mapper.SearchStoreMapper;
import com.zerobase.SelfWash.customer.search_store.service.SearchStoreService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchStoreServiceImpl implements SearchStoreService {

  private final SearchStoreMapper mapper;
  private final ManageStoreService manageStoreService;

  @Override
  public List<SearchStoreDto> searchStore(double latitude, double longitude) {
    try {
      return manageStoreService.getNearbyStoresWithDetails(latitude, longitude);
    } catch (Exception e) {
      log.error("레디스 작업 실패 : {}", e.getMessage());
      return mapper.searchStore(latitude, longitude);
    }
  }
}
