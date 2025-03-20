package com.zerobase.SelfWash.administer_store.service.impl;

import com.zerobase.SelfWash.administer_store.domain.dto.MachineRedisDto;
import com.zerobase.SelfWash.administer_store.domain.dto.StoreRedisDto;
import com.zerobase.SelfWash.administer_store.domain.type.MachineType;
import com.zerobase.SelfWash.administer_store.domain.type.UsageStatus;
import com.zerobase.SelfWash.administer_store.service.RedisManageService;
import com.zerobase.SelfWash.customer.search.dto.SearchMachineDto;
import com.zerobase.SelfWash.customer.search.dto.SearchStoreDto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
public class RedisManageServiceImpl implements RedisManageService {

  private final RedisTemplate<String, String> redisTemplate;

  private static final String GEO_KEY = "stores:locations";
  private static final String STORE_KEY = "stores:details:";
  private static final String MACHINE_KEY = ":machine:";

  @Override
  public void addStoreToRedis(StoreRedisDto store) {

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

  @Override
  public void removeStoreFromRedis(Long storeId) {
    log.info("매장 정보 삭제 ID: {}", storeId);
    // GEO 데이터 삭제
    redisTemplate.opsForGeo().remove(GEO_KEY, storeId.toString());
    // 매장 상세 정보 삭제
    redisTemplate.delete(STORE_KEY + storeId);
    // 매장 정보가 삭제될 때 기계 정보도 같이 삭제됨.
    String machinePattern = STORE_KEY + storeId + MACHINE_KEY + "*";
    Set<String> machineKeys = redisTemplate.keys(machinePattern);
    if (!machineKeys.isEmpty()) {
      redisTemplate.delete(machineKeys);
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

  @Override
  public void addAndUpdateMachineToRedis(Long storeId, MachineRedisDto machine) {
    String machineKey = STORE_KEY + storeId + MACHINE_KEY;
    redisTemplate.opsForHash().putAll(machineKey + machine.getMachineId(), machine.toHashMap());
  }

  @Override
  public List<SearchMachineDto> searchStoreMachines(Long storeId) {
    String machinePattern = STORE_KEY + storeId + MACHINE_KEY + "*";
    Set<String> machineKeys = redisTemplate.keys(machinePattern);

    log.info("조회된 머신 아이디 : {}", machineKeys);

    if (machineKeys == null || machineKeys.isEmpty()) {
      log.info("매장 ID {}에 등록된 머신이 없습니다.", storeId);
      return new ArrayList<>();
    }

    List<SearchMachineDto> machines = new ArrayList<>();

    // endTime 해쉬 데이터를 가져온 후에 문자열을 LocalDateTime으로 변경하기 위한 포맷터
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    for (String key : machineKeys) {
      Map<Object, Object> machineData = redisTemplate.opsForHash().entries(key);
      if (!machineData.isEmpty()) {
        // 머신 ID 추출 (키 패턴에서 추출)
        String machineIdStr = key.substring(key.lastIndexOf(':') + 1);
        long machineId = Long.parseLong(machineIdStr);

        machines.add(SearchMachineDto.builder()
            .machineId(machineId)
            .machineType(MachineType.valueOf(machineData.get("machineType").toString()))
            .status(UsageStatus.valueOf(machineData.get("status").toString()))
            .end_time(machineData.get("endTime") != null && !machineData.get("endTime").toString().isEmpty()?
                LocalDateTime.parse(machineData.get("endTime").toString(), formatter) : null)
            .build());
        log.info("매장 ID {}에서 머신 ID {} 조회됨", storeId, machineId);
      }
    }

    return machines;
  }


  // 위도 경도 유효성 검사
  public static boolean isValidGeoCoordinates(double longitude, double latitude) {
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
