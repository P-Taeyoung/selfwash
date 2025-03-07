package com.zerobase.SelfWash.requestStore.domain.dto.machine;

import com.zerobase.SelfWash.requestStore.domain.entity.machine.ReqUpdateMachine;
import com.zerobase.SelfWash.requestStore.domain.type.MachineCompany;
import com.zerobase.SelfWash.requestStore.domain.type.MachineType;
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
public class ReqUpdateMachineDto {

  private long reqUpdateId;
  private MachineType machineType;
  private MachineCompany machineCompany;

  public static ReqUpdateMachineDto from(ReqUpdateMachine updateMachine) {
    return ReqUpdateMachineDto.builder()
        .reqUpdateId(updateMachine.getId())
        .machineType(MachineType.valueOf(updateMachine.getMachineType()))
        .machineCompany(MachineCompany.valueOf(updateMachine.getMachineCompany()))
        .build();
  }
}
