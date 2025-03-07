package com.zerobase.SelfWash.requestStore.domain.dto.store;

import com.zerobase.SelfWash.requestStore.domain.dto.machine.ReqRegisterMachineDto;
import com.zerobase.SelfWash.requestStore.domain.entity.store.ReqRegisterStore;
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
public class ReqRegisterStoreDto {

  private long reqRegisterId;
  private long ownerId;
  private String address;
  private float latitude;
  private float longitude;

  private List<ReqRegisterMachineDto> reqRegisterMachineDtos;

  public static ReqRegisterStoreDto from(ReqRegisterStore reqRegisterStore) {
    return ReqRegisterStoreDto.builder()
        .reqRegisterId(reqRegisterStore.getId())
        .ownerId(reqRegisterStore.getOwnerId())
        .address(reqRegisterStore.getAddress())
        .latitude(reqRegisterStore.getLatitude())
        .longitude(reqRegisterStore.getLongitude())
        .reqRegisterMachineDtos(reqRegisterStore.getReqRegisterMachines().stream()
            .map(ReqRegisterMachineDto::from).collect(Collectors.toList()))
        .build();
  }

}
