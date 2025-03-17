package com.zerobase.SelfWash.customer.use_machine.domain.entity;

import com.zerobase.SelfWash.administer_store.domain.form.MachineForm;
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
public class MachineUse {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private long machineId;
  private long customerId;

  @Enumerated(EnumType.STRING)
  private WashingCourse washingCourse;
  private LocalDateTime startTime;
  private LocalDateTime endTime;

  public static MachineUse from(long customerId, MachineUseForm form) {
    return MachineUse.builder()
        .machineId(form.getMachineId())
        .customerId(customerId)
        .washingCourse(form.getWashingCourse())
        .startTime(LocalDateTime.now())
        .endTime(usingTime(form.getWashingCourse()))
        .build();
  }

  private static LocalDateTime usingTime(WashingCourse washingCourse) {
    if (washingCourse == WashingCourse.NORMAL) {
      return LocalDateTime.now().plusMinutes(30);
    } else if (washingCourse == WashingCourse.FAST) {
      return LocalDateTime.now().plusMinutes(20);
    } else if (washingCourse == WashingCourse.BEDDING) {
      return LocalDateTime.now().plusMinutes(60);
    } else if (washingCourse == WashingCourse.WOOL) {
      return LocalDateTime.now().plusMinutes(40);
    }
    throw new RuntimeException("코스 설정이 되어있지 않습니다.");
  }

}
