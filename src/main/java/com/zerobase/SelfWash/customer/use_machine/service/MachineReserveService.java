package com.zerobase.SelfWash.customer.use_machine.service;

import com.zerobase.SelfWash.customer.use_machine.domain.dto.MachineReserveDto;
import com.zerobase.SelfWash.customer.use_machine.domain.dto.MachineUseDto;
import com.zerobase.SelfWash.customer.use_machine.domain.form.MachineUseForm;

public interface MachineReserveService {

  /**
   * 기계 사용 예약
   */
  MachineReserveDto reserve(Long customerId, Long machineId);

  /**
   * 기계 사용 예약 취소
   */
  void reserveCancel(Long customerId, Long machineId);







}
