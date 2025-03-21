package com.zerobase.SelfWash.administer_store.service.impl;

import com.zerobase.SelfWash.administer_store.domain.dto.MachineRedisDto;
import com.zerobase.SelfWash.administer_store.domain.dto.StoreRedisDto;
import com.zerobase.SelfWash.administer_store.domain.entity.Store;
import com.zerobase.SelfWash.administer_store.domain.repository.StoreRepository;
import com.zerobase.SelfWash.administer_store.service.ManageStoreService;
import com.zerobase.SelfWash.administer_store.service.RedisManageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManageStoreServiceImpl implements ManageStoreService {

  private final StoreRepository storeRepository;

  private final RedisManageService redisManageService;

  @Override
  @Transactional
  public void storeOperationChange(Long storeId) {
    Store store = storeRepository.findById(storeId)
        .orElseThrow(() -> new RuntimeException("해당하는 매장 정보가 없습니다."));

    if (!store.isApproved()) {
      throw new RuntimeException("매장 등록 승인 전입니다.");
    }

    store.setOpened(!store.isOpened()); // 현재 설정된 운영현황의 반대로 변경

    // 레디스 실패로 인해 데이터 베이스 작업이 실패하면 안되기 때문에 try catch로 묶어서 레디스 작업에는 실패하더라도 데이터베이스 작업에는 지장이 안가도록 함.
    //TODO 후에 레디스 작업 실패 시 후속조치를 추가해야 함.
    try {
      //운영 중인 경우 해당 매장의 위치 정보와 해당 매장의 기계정보들 레디스에 저장
      if (store.isOpened()) {
        redisManageService.addStoreToRedis(StoreRedisDto.toDto(store));
        store.getMachines().forEach(machine -> redisManageService.addAndUpdateMachineToRedis(storeId, MachineRedisDto.toDto(machine)));
      } else {
        //운영 중단인 경우 해당 매장 정보의 캐시 데이터를 삭제
        redisManageService.removeStoreFromRedis(store.getId());
      }
    } catch (Exception e) {
      log.error("Redis 작업 실패: {}", e.getMessage(), e);
    }
  }


}
