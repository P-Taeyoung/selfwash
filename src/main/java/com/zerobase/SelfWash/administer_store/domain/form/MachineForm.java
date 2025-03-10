package com.zerobase.SelfWash.administer_store.domain.form;

import com.zerobase.SelfWash.administer_store.domain.type.MachineCompany;
import com.zerobase.SelfWash.administer_store.domain.type.MachineType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MachineForm {

  private MachineType machineType;
  private MachineCompany machineCompany;
  private String notes;

}
