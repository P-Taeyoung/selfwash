package com.zerobase.SelfWash.administer_store.service;

import com.zerobase.SelfWash.administer_store.domain.dto.MachineRedisDto;
import com.zerobase.SelfWash.administer_store.domain.dto.StoreRedisDto;
import com.zerobase.SelfWash.search_store.dto.SearchMachineDto;
import com.zerobase.SelfWash.search_store.dto.SearchStoreDto;
import java.util.List;

public interface RedisManageService {

  /**
   * 매장 정보 추가
   */
  void addStoreToRedis(StoreRedisDto store);

  /**
   * 매장 정보 삭제
   * 매장 정보 삭제 시 기계 정보도 일괄 삭제 처리
   */
  void removeStoreFromRedis(Long storeId);

  /**
   * 설정위치에서 가까운 매장 탐색
   */
  List<SearchStoreDto> getNearbyStoresWithDetails(double latitude, double longitude);

  /**
   * 기계 정보 추가 또는 수정
   */
  void addAndUpdateMachineToRedis(Long storeId, MachineRedisDto machine);

  /**
   * 매장 기계 조회 기능
   */
  List<SearchMachineDto> searchStoreMachines (Long storeId);



}
