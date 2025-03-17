package com.zerobase.SelfWash.administer_store.domain.dto;

import com.zerobase.SelfWash.administer_store.domain.entity.Store;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreRedisDto {
  private long storeId;
  private String address;
  private Point location;


  public static StoreRedisDto toDto(Store store) {
    return StoreRedisDto.builder()
        .storeId(store.getId())
        .address(store.getAddress())
        .location(store.getLocation())
        .build();
  }

  public Map<String, String> toHashMap() {
    Map<String, String> map = new HashMap<>();
    map.put("storeId", String.valueOf(this.getStoreId()));
    map.put("address", this.getAddress());
    map.put("longitude", String.valueOf(this.getLocation().getX()));
    map.put("latitude", String.valueOf(this.getLocation().getY()));
    return map;
  }
}
