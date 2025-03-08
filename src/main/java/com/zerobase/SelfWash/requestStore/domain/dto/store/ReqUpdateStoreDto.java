package com.zerobase.SelfWash.requestStore.domain.dto.store;

import com.zerobase.SelfWash.requestStore.domain.dto.machine.ReqRegisterMachineDto;
import com.zerobase.SelfWash.requestStore.domain.dto.machine.ReqUpdateMachineDto;
import com.zerobase.SelfWash.requestStore.domain.entity.store.ReqRegisterStore;
import com.zerobase.SelfWash.requestStore.domain.entity.store.ReqUpdateStore;
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
public class ReqUpdateStoreDto {

  private long reqUpdateId;
  private long storeId;
  private long ownerId;
  private String address;
  private float latitude;
  private float longitude;

  private List<ReqUpdateMachineDto> reqUpdateMachineDtos;

  public static ReqUpdateStoreDto from(ReqUpdateStore reqUpdateStore) {
    return ReqUpdateStoreDto.builder()
        .reqUpdateId(reqUpdateStore.getId())
        .ownerId(reqUpdateStore.getOwnerId())
        .address(reqUpdateStore.getAddress())
        .latitude(reqUpdateStore.getLatitude())
        .longitude(reqUpdateStore.getLongitude())
        .reqUpdateMachineDtos(reqUpdateStore.getReqUpdateMachines().stream()
            .map(ReqUpdateMachineDto::from).collect(Collectors.toList()))
        .build();
  }

}
