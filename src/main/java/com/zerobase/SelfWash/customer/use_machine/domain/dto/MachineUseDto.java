package com.zerobase.SelfWash.customer.use_machine.domain.dto;

import com.zerobase.SelfWash.customer.use_machine.domain.entity.MachineUse;
import com.zerobase.SelfWash.customer.use_machine.domain.type.WashingCourse;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MachineUseDto {
  private long useId;
  private long machineId;
  private WashingCourse washingCourse;
  private LocalDateTime startTime;
  private LocalDateTime endTime;

  public static MachineUseDto from(MachineUse machineUse) {
   return  MachineUseDto.builder()
        .useId(machineUse.getId())
        .machineId(machineUse.getMachineId())
        .washingCourse(machineUse.getWashingCourse())
        .startTime(machineUse.getStartTime())
        .endTime(machineUse.getEndTime())
        .build();
  }

}
