package com.zerobase.SelfWash.customer.search.service.impl;

import com.zerobase.SelfWash.administer_store.domain.repository.MachineRepository;
import com.zerobase.SelfWash.administer_store.domain.repository.StoreRepository;
import com.zerobase.SelfWash.administer_store.service.ManageStoreService;
import com.zerobase.SelfWash.administer_store.service.RedisManageService;
import com.zerobase.SelfWash.customer.search.dto.SearchMachineDto;
import com.zerobase.SelfWash.customer.search.dto.SearchStoreDto;
import com.zerobase.SelfWash.customer.search.mapper.SearchStoreMapper;
import com.zerobase.SelfWash.customer.search.service.SearchStoreService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchStoreServiceImpl implements SearchStoreService {

  private final SearchStoreMapper mapper;
  private final RedisManageService redisManageService;
  private final MachineRepository machineRepository;

  @Override
  public List<SearchStoreDto> searchStore(double latitude, double longitude) {
    try {
      return redisManageService.getNearbyStoresWithDetails(latitude, longitude);
    } catch (Exception e) {
      log.error("레디스 작업 실패 : {}", e.getMessage(), e);
      return mapper.searchStore(latitude, longitude);
    }
  }

  @Override
  public List<SearchMachineDto> searchMachine(Long storeId) {

    try {
      return redisManageService.searchStoreMachines(storeId);
    } catch (Exception e) {
      log.error("레디스 작업 실패 : {}", e.getMessage(), e);
      return machineRepository.findAllByStoreId(storeId)
          .stream().map(SearchMachineDto::fromMachine)
          .collect(Collectors.toList());
    }
  }
}
