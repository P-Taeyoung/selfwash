package com.zerobase.SelfWash.requestStore.domain.entity.machine;

import com.zerobase.SelfWash.member.domain.entity.BaseEntity;
import com.zerobase.SelfWash.requestStore.domain.entity.store.ReqUpdateStore;
import com.zerobase.SelfWash.requestStore.domain.form.machine.ReqUpdateMachineForm;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReqUpdateMachine extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private long machineId;
  private String machineCompany;
  private String machineType;


  @ManyToOne
  @JoinColumn(name = "store_id", nullable = false)
  private ReqUpdateStore reqUpdateStore;

  public static ReqUpdateMachine requestFrom(ReqUpdateMachineForm form) {
    return ReqUpdateMachine.builder()
        .machineId(form.getMachineId())
        .machineType(form.getMachineType().name())
        .machineCompany(form.getMachineCompany().name())
        .build();
  }

}
