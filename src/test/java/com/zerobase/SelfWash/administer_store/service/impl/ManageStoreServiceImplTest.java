package com.zerobase.SelfWash.administer_store.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.zerobase.SelfWash.administer_store.domain.entity.Store;
import com.zerobase.SelfWash.administer_store.domain.repository.StoreRepository;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;


@ExtendWith(MockitoExtension.class)
public class ManageStoreServiceImplTest {

  @Mock
  private StoreRepository storeRepository;

  @Mock
  private RedisTemplate<String, String> redisTemplate;

  @Mock
  private GeoOperations<String, String> geoOperations;

  @Mock
  private HashOperations<String, Object, Object> hashOperations;

  @InjectMocks
  private ManageStoreServiceImpl manageStoreService;

  private Store openedStore;
  private Store closedStore;
  private Store unapprovedStore;

  @BeforeEach
  void setUp() {
    // Redis 템플릿 설정
    // lenient() 모드를 사용하여 불필요한 스터빙 경고 무시
    lenient().when(redisTemplate.opsForGeo()).thenReturn(geoOperations);
    lenient().when(redisTemplate.opsForHash()).thenReturn(hashOperations);

    // 테스트용 매장 데이터 생성
    openedStore = new Store();
    openedStore.setId(1L);
    openedStore.setOpened(true);
    openedStore.setApproved(true);
    openedStore.setLatitude(37.5);
    openedStore.setLongitude(127.0);
    openedStore.setAddress("서울시 강남구");

    closedStore = new Store();
    closedStore.setId(2L);
    closedStore.setOpened(false);
    closedStore.setApproved(true);
    closedStore.setLatitude(37.6);
    closedStore.setLongitude(127.1);
    closedStore.setAddress("서울시 서초구");

    unapprovedStore = new Store();
    unapprovedStore.setId(3L);
    unapprovedStore.setOpened(false);
    unapprovedStore.setApproved(false);
  }

  @Test
  @DisplayName("운영 중인 매장을 운영 중단으로 변경")
  void changeStoreOperationFromOpenedToClosed() {
    // given
    when(storeRepository.findById(openedStore.getId())).thenReturn(Optional.of(openedStore));

    // when
    manageStoreService.storeOperationChange(openedStore.getId());

    // then
    assertFalse(openedStore.isOpened());
    verify(geoOperations).remove("stores:locations", String.valueOf(openedStore.getId()));
    verify(redisTemplate).delete("stores:details:" + openedStore.getId());
  }

  @Test
  @DisplayName("운영 중단인 매장을 운영 중으로 변경")
  void changeStoreOperationFromClosedToOpened() {
    // given
    // DB로부터 반환된 Store객체 설정
    when(storeRepository.findById(closedStore.getId())).thenReturn(Optional.of(closedStore));

    // when
    manageStoreService.storeOperationChange(closedStore.getId());

    // then
    assertTrue(closedStore.isOpened());
    verify(geoOperations).add(eq("stores:locations"), any(RedisGeoCommands.GeoLocation.class));
    verify(hashOperations).putAll(eq("stores:details:" + closedStore.getId()), any(Map.class));
  }

  @Test
  @DisplayName("존재하지 않는 매장 ID로 요청 시 예외 발생")
  void changeStoreOperationWithNonExistingId() {
    // given
    Long nonExistingId = 999L;
    when(storeRepository.findById(nonExistingId)).thenReturn(Optional.empty());

    // when & then
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      manageStoreService.storeOperationChange(nonExistingId);
    });
    assertEquals("해당하는 매장 정보가 없습니다.", exception.getMessage());
  }

  @Test
  @DisplayName("승인되지 않은 매장의 운영 상태 변경 시 예외 발생")
  void changeStoreOperationWithUnapprovedStore() {
    // given
    when(storeRepository.findById(unapprovedStore.getId())).thenReturn(Optional.of(unapprovedStore));

    // when & then
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      manageStoreService.storeOperationChange(unapprovedStore.getId());
    });
    assertEquals("매장 등록 승인 전입니다.", exception.getMessage());
  }

  @Test
  @DisplayName("Redis 작업 실패 시에도 데이터베이스 작업은 성공")
  void changeStoreOperationWithRedisFailure() {
    // given
    when(storeRepository.findById(closedStore.getId())).thenReturn(Optional.of(closedStore));
    doThrow(new RuntimeException("Redis 연결 실패")).when(geoOperations).add(anyString(), any(RedisGeoCommands.GeoLocation.class));

    // when
    manageStoreService.storeOperationChange(closedStore.getId());

    // then
    assertTrue(closedStore.isOpened()); // 데이터베이스 작업은 성공
    // Redis 작업 실패 로그는 테스트하기 어려우므로 생략
  }

  @Test
  @DisplayName("유효하지 않은 위치 정보를 가진 매장 처리")
  void handleStoreWithInvalidGeoCoordinates() {
    // given
    Store invalidStore = new Store();
    invalidStore.setId(4L);
    invalidStore.setOpened(false);
    invalidStore.setApproved(true);
    invalidStore.setLatitude(200.0); // 유효하지 않은 위도
    invalidStore.setLongitude(127.0);
    invalidStore.setAddress("서울시 강남구");

    when(storeRepository.findById(invalidStore.getId())).thenReturn(Optional.of(invalidStore));

    // when
    manageStoreService.storeOperationChange(invalidStore.getId());

    // then
    assertTrue(invalidStore.isOpened()); // 상태는 변경됨
    // Redis 작업 실패 로그는 테스트하기 어려우므로 생략
  }
}
