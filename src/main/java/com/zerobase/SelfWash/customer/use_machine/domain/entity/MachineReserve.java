package com.zerobase.SelfWash.customer.use_machine.domain.entity;

import com.zerobase.SelfWash.customer.use_machine.domain.form.MachineUseForm;
import com.zerobase.SelfWash.customer.use_machine.domain.type.WashingCourse;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
