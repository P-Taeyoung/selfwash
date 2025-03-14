package com.zerobase.SelfWash.administer_store.service.impl;

import com.zerobase.SelfWash.administer_store.domain.dto.StoreRedisDto;
import com.zerobase.SelfWash.administer_store.domain.entity.Store;
import com.zerobase.SelfWash.administer_store.domain.repository.StoreRepository;
import com.zerobase.SelfWash.administer_store.service.ManageStoreService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManageStoreServiceImpl implements ManageStoreService {

  private final StoreRepository storeRepository;

  private final RedisTemplate<String, String> redisTemplate;

  private static final String GEO_KEY = "stores:locations";
  private static final String STORE_KEY = "stores:details";

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
      //운영 중인 경우 해당 매장의 위치정보를 캐싱
      if (store.isOpened()) {
        addStoreToRedis(StoreRedisDto.toDto(store));
      } else {
        //운영 중단인 경우 해당 매장 정보의 캐시 데이터를 삭제
        removeStoreFromRedis(store.getId());
      }
    } catch (Exception e) {
      log.error("Redis 작업 실패: {}", e.getMessage(), e);
    }
  }


  private void addStoreToRedis(StoreRedisDto store) {

    if (!isValidGeoCoordinates(store.getLatitude(), store.getLongitude())) {
      log.error("매장 ID {}의 위치 정보가 유효하지 않습니다. 위도: {}, 경도: {}",
          store.getStoreId(), store.getLatitude(), store.getLongitude());
      throw new IllegalArgumentException("유효하지 않은 위치 정보입니다.");
    }

      log.info("매장 정보 저장 ID: {}", store.getStoreId());
      // Redis에 GEO 데이터 추가
      redisTemplate.opsForGeo().add(GEO_KEY, new GeoLocation<>(
          String.valueOf(store.getStoreId()),
          new Point(store.getLongitude(), store.getLatitude())));

      // Redis에 매장 상세 정보 추가
      Map<String, String> storeDetails = new HashMap<>();
      storeDetails.put("storeId", String.valueOf(store.getStoreId()));
      storeDetails.put("address", store.getAddress());
      storeDetails.put("longitude", String.valueOf(store.getLongitude()));
      storeDetails.put("latitude", String.valueOf(store.getLatitude()));
      redisTemplate.opsForHash().putAll(STORE_KEY + ":" + store.getStoreId(), storeDetails);

  }

  private void removeStoreFromRedis(Long storeId) {

      log.info("매장 정보 삭제 ID: {}", storeId);
      // GEO 데이터 삭제
      redisTemplate.opsForGeo().remove(GEO_KEY, storeId.toString());
      // 매장 상세 정보 삭제
      redisTemplate.delete(STORE_KEY + ":" + storeId);

  }

  // 위도 경도 유효성 검사
  private boolean isValidGeoCoordinates(double latitude, double longitude) {
    if (latitude < -90 || latitude > 90) {
      log.warn("유효하지 않은 위도 값: {}", latitude);
      return false;
    }

    if (longitude < -180 || longitude > 180) {
      log.warn("유효하지 않은 경도 값: {}", longitude);
      return false;
    }
    return true;
  }
}
