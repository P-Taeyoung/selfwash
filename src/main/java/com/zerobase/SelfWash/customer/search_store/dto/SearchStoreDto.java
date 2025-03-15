package com.zerobase.SelfWash.customer.search_store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchStoreDto {

  private String storeId;
  private String storeAddress;
  private double latitude;
  private double longitude;
  //설정 위치와 가게 사이 거리 (m 단위 기준)
  private double distance;

}
