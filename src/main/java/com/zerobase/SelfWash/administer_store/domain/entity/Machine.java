package com.zerobase.SelfWash.administer_store.domain.entity;

import com.zerobase.SelfWash.administer_store.domain.type.MachineCompany;
import com.zerobase.SelfWash.administer_store.domain.type.MachineType;
import com.zerobase.SelfWash.member.domain.entity.BaseEntity;
import com.zerobase.SelfWash.administer_store.domain.form.MachineForm;
import com.zerobase.SelfWash.administer_store.domain.type.UsageStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.Query;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Machine extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "store_id", nullable = false)
  private Store store;

  @Enumerated(EnumType.STRING)
  private MachineCompany machineCompany;

  @Enumerated(EnumType.STRING)
  private MachineType machineType;
  //사용현황
  @Enumerated(EnumType.STRING)
  private UsageStatus usageStatus;

  //기계 사용&에약 종료 시간
  private LocalDateTime endTime;
  //예약자 Id
  private Long customerId;

  //비고
  private String notes;
  //삭제예정데이터
  private boolean withdraw;


  public static Machine from(MachineForm form) {
    return Machine.builder()
        .machineType(form.getMachineType())
        .machineCompany(form.getMachineCompany())
        .notes(form.getNotes())
        .usageStatus(UsageStatus.USABLE)
        .withdraw(false)
        .build();
  }

  public void modify(MachineForm form) {
    this.setMachineType(form.getMachineType());
    this.setMachineCompany(form.getMachineCompany());
    this.setNotes(form.getNotes());
  }

  //분 단위로 저장
  public void setEndTime(LocalDateTime endTime) {
    this.endTime = endTime != null ? endTime.truncatedTo(ChronoUnit.MINUTES) : null;
  }
}
