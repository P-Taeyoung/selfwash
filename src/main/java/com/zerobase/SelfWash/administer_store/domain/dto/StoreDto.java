package com.zerobase.SelfWash.administer_store.domain.dto;

import com.zerobase.SelfWash.administer_store.domain.entity.Store;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreDto {

  private long storeId;
  private long ownerId;
  private String address;
  private float latitude;
  private float longitude;
  private String notes;
  private boolean opened;
  private boolean approved;

  private List<MachineDto> machineDtos;

  public static StoreDto from(Store store) {
    return StoreDto.builder()
        .storeId(store.getId())
        .ownerId(store.getOwnerId())
        .address(store.getAddress())
        .latitude(store.getLatitude())
        .longitude(store.getLongitude())
        .notes(store.getNotes())
        .opened(store.isOpened())
        .approved(store.isApproved())
        .machineDtos(store.getMachines().stream()
            .map(MachineDto::from).collect(Collectors.toList()))
        .build();
  }

}
