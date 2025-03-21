package com.zerobase.SelfWash.use_machine.domain.entity;

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

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MachineReserve {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private long machineId;
  private long customerId;

  private LocalDateTime startTime;
  private LocalDateTime endTime;


  public static MachineReserve from(Long customerId, Long machineId) {
    return MachineReserve.builder()
        .machineId(machineId)
        .customerId(customerId)
        .startTime(LocalDateTime.now())
        .endTime(LocalDateTime.now().plusMinutes(15))
        .build();
  }

}
