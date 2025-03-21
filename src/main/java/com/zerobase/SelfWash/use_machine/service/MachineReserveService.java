package com.zerobase.SelfWash.use_machine.service;

import com.zerobase.SelfWash.use_machine.domain.dto.MachineReserveDto;

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
