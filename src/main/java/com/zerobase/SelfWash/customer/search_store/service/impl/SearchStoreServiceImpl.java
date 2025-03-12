package com.zerobase.SelfWash.customer.search_store.service.impl;

import com.zerobase.SelfWash.customer.search_store.dto.SearchStoreDto;
import com.zerobase.SelfWash.customer.search_store.mapper.SearchStoreMapper;
import com.zerobase.SelfWash.customer.search_store.service.SearchStoreService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchStoreServiceImpl implements SearchStoreService {

  private final SearchStoreMapper mapper;

  @Override
  public List<SearchStoreDto> searchStore(double latitude, double longitude) {

    return mapper.searchStore(latitude, longitude);
  }

}
