package com.zerobase.SelfWash.requestStore.domain.dto.machine;

import com.zerobase.SelfWash.requestStore.domain.entity.machine.ReqRegisterMachine;
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
public class ReqRegisterMachineDto {

  private long machineId;
  private MachineType machineType;
  private MachineCompany machineCompany;

  public static ReqRegisterMachineDto from(ReqRegisterMachine registerMachine) {
    return ReqRegisterMachineDto.builder()
        .machineId(registerMachine.getId())
        .machineType(MachineType.valueOf(registerMachine.getMachineType()))
        .machineCompany(MachineCompany.valueOf(registerMachine.getMachineCompany()))
        .build();
  }
}
