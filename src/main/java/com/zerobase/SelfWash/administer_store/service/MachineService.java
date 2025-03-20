package com.zerobase.SelfWash.administer_store.service;

import com.zerobase.SelfWash.administer_store.domain.dto.MachineDto;
import com.zerobase.SelfWash.administer_store.domain.form.MachineForm;
import com.zerobase.SelfWash.administer_store.domain.type.UsageStatus;

public interface MachineService {

  /**
   * 이미 매장 등록 승인이 된 경우,
   * 매장 구조 설계가 이미 확정되었기 떄문에
   * 기계 추가와 삭제 불가
   */

  //기계 추가
  MachineDto create(Long storeId, MachineForm form);

  //기계 삭제
  void remove(Long storeId, Long machineId);

  //기계 조회
  MachineDto search(Long machineId);

  //기계 정보 수정
  void modify(Long machineId, MachineForm form);

  //기계 상태 변경
  void change(Long machineId, UsageStatus status);




}
