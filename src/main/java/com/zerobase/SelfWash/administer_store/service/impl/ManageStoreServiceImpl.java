package com.zerobase.SelfWash.administer_store.service.impl;

import com.zerobase.SelfWash.administer_store.domain.dto.StoreRedisDto;
import com.zerobase.SelfWash.administer_store.domain.entity.Store;
import com.zerobase.SelfWash.administer_store.domain.repository.StoreRepository;
import com.zerobase.SelfWash.administer_store.service.ManageStoreService;
import com.zerobase.SelfWash.customer.search_store.dto.SearchStoreDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands.DistanceUnit;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoRadiusCommandArgs;
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
  private static final String STORE_KEY = "stores:details:";

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

  @Override
  @Transactional
  // 500m 내 매장 조회 (GEO 정보 + 상세 정보 포함)
  public List<SearchStoreDto> getNearbyStoresWithDetails(double latitude, double longitude) {
    // 반경 500m 내 검색
    GeoResults<GeoLocation<String>> results = redisTemplate.opsForGeo()
        .radius(GEO_KEY,
            new Circle(new Point(longitude, latitude), new Distance(500, DistanceUnit.METERS)),
            GeoRadiusCommandArgs
                .newGeoRadiusArgs()
                .includeDistance() // 거리 정보 포함
                .includeCoordinates() // 위도, 경도 정보 포함
                .sortAscending()
                .limit(5));

    //조회되는 매장 위치 정보가 없는 경우 빈 리스트 반환
    if (results == null) {
      return new ArrayList<>();
    }

    List<SearchStoreDto> dtos = new ArrayList<>();

    // 결과 처리
    for (GeoResult<GeoLocation<String>> result : results.getContent()) {
      String storeName = result.getContent().getName(); // 매장 ID

      log.info(storeName);
      log.info("매장 위치 정보 {}, {}", result.getDistance().getValue(), result.getContent().toString());

      // Hash에서 상세 정보 가져오기
      String hashKey = STORE_KEY + storeName;
      Map<Object, Object> details = redisTemplate.opsForHash().entries(hashKey);

      // (GEO 정보 + 상세 정보) -> SearchStoreDto로 변환
      dtos.add(SearchStoreDto.builder()
          .storeId(storeName)
          .storeAddress(details.get("address").toString())
          .distance(result.getDistance().getValue())
          .latitude(result.getContent().getPoint().getX())
          .longitude(result.getContent().getPoint().getY())
          .build());

      log.info("레디스에서 조회된 매장 : {}", hashKey);

    }
    return dtos;
  }


  private void addStoreToRedis(StoreRedisDto store) {

    if (!isValidGeoCoordinates(store.getLocation().getX(), store.getLocation().getY())) {
      log.error("매장 ID {}의 위치 정보가 유효하지 않습니다. 경도: {}, 위도: {}",
          store.getStoreId(), store.getLocation().getX(), store.getLocation().getY());
      throw new IllegalArgumentException("유효하지 않은 위치 정보입니다.");
    }

    log.info("매장 정보 저장 ID: {}", store.getStoreId());
    // Redis에 GEO 데이터 추가
    redisTemplate.opsForGeo().add(GEO_KEY,
        new Point(store.getLocation().getX(), store.getLocation().getY()),
        String.valueOf(store.getStoreId()));

    // Redis에 매장 상세 정보 추가

    redisTemplate.opsForHash().putAll(STORE_KEY + store.getStoreId(), store.toHashMap());

  }

  private void removeStoreFromRedis(Long storeId) {

    log.info("매장 정보 삭제 ID: {}", storeId);
    // GEO 데이터 삭제
    redisTemplate.opsForGeo().remove(GEO_KEY, storeId.toString());
    // 매장 상세 정보 삭제
    redisTemplate.delete(STORE_KEY + storeId);

  }

  // 위도 경도 유효성 검사
  private boolean isValidGeoCoordinates(double longitude, double latitude) {
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
