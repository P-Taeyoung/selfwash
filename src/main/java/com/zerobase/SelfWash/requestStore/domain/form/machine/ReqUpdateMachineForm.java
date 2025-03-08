package com.zerobase.SelfWash.requestStore.domain.form.machine;

import com.zerobase.SelfWash.requestStore.domain.type.MachineCompany;
import com.zerobase.SelfWash.requestStore.domain.type.MachineType;
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
public class ReqUpdateMachineForm {
  private long machineId;
  private MachineType machineType;
  private MachineCompany machineCompany;

}
