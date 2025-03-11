package com.zerobase.SelfWash.administer_store.domain.dto;

import com.zerobase.SelfWash.administer_store.domain.entity.Machine;
import com.zerobase.SelfWash.administer_store.domain.type.MachineCompany;
import com.zerobase.SelfWash.administer_store.domain.type.MachineType;
import com.zerobase.SelfWash.administer_store.domain.type.UsageStatus;
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
public class MachineDto {

  private long machineId;
  private MachineType machineType;
  private MachineCompany machineCompany;
  private UsageStatus usageStatus;
  private String notes;


  public static MachineDto from(Machine registerMachine) {
    return MachineDto.builder()
        .machineId(registerMachine.getId())
        .machineType(registerMachine.getMachineType())
        .machineCompany(registerMachine.getMachineCompany())
        .usageStatus(registerMachine.getUsageStatus())
        .notes(registerMachine.getNotes())
        .build();
  }
}
