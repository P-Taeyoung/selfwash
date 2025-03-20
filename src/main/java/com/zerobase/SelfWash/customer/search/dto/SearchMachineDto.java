package com.zerobase.SelfWash.customer.search.dto;


import com.zerobase.SelfWash.administer_store.domain.entity.Machine;
import com.zerobase.SelfWash.administer_store.domain.type.MachineType;
import com.zerobase.SelfWash.administer_store.domain.type.UsageStatus;
import java.time.LocalDateTime;
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
public class SearchMachineDto {
  private long machineId;
  private MachineType machineType;
  private UsageStatus status;
  private LocalDateTime end_time;

  public static SearchMachineDto fromMachine(Machine machine) {
    return SearchMachineDto.builder()
        .machineId(machine.getId())
        .machineType(machine.getMachineType())
        .status(machine.getUsageStatus())
        .end_time(machine.getEndTime())
        .build();
  }
}
