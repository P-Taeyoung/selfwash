package com.zerobase.SelfWash.customer.use_machine.domain.dto;

import com.zerobase.SelfWash.customer.use_machine.domain.entity.MachineReserve;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class MachineReserveDto {

  private long reserveId;
  private long machineId;
  private long customerId;

  private LocalDateTime startTime;
  private LocalDateTime endTime;


  public static MachineReserveDto from(MachineReserve machineReserve) {
    return MachineReserveDto.builder()
        .reserveId(machineReserve.getId())
        .machineId(machineReserve.getMachineId())
        .customerId(machineReserve.getCustomerId())
        .startTime(machineReserve.getStartTime())
        .endTime(machineReserve.getEndTime())
        .build();
  }

}
