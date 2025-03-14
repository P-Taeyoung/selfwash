package com.zerobase.SelfWash.administer_store.domain.dto;

import com.zerobase.SelfWash.administer_store.domain.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreRedisDto {
  private long storeId;
  private String address;
  private double longitude;
  private double latitude;

  public static StoreRedisDto toDto(Store store) {
    return StoreRedisDto.builder()
        .storeId(store.getId())
        .address(store.getAddress())
        .longitude(store.getLongitude())
        .latitude(store.getLatitude())
        .build();
  }
}
