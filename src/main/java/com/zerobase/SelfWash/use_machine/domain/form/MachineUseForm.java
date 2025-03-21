package com.zerobase.SelfWash.use_machine.domain.form;

import com.zerobase.SelfWash.use_machine.domain.type.WashingCourse;
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
public class MachineUseForm {
  private long machineId;
  private WashingCourse washingCourse;
}
