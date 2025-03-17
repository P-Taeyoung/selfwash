package com.zerobase.SelfWash.customer.use_machine.service;

import com.zerobase.SelfWash.customer.use_machine.domain.dto.MachineReserveDto;
import com.zerobase.SelfWash.customer.use_machine.domain.dto.MachineUseDto;
import com.zerobase.SelfWash.customer.use_machine.domain.form.MachineUseForm;

public interface MachineUseService {

  /**
   * 기계 사용
   */
  MachineUseDto use(Long customerId, MachineUseForm form);

  /**
   * 기계사용 종료
   */
  void finish(Long machineId);






}
