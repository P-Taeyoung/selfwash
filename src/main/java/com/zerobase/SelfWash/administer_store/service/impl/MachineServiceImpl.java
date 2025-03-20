package com.zerobase.SelfWash.administer_store.service.impl;

import com.zerobase.SelfWash.administer_store.domain.dto.MachineDto;
import com.zerobase.SelfWash.administer_store.domain.dto.MachineRedisDto;
import com.zerobase.SelfWash.administer_store.domain.entity.Machine;
import com.zerobase.SelfWash.administer_store.domain.entity.Store;
import com.zerobase.SelfWash.administer_store.domain.form.MachineForm;
import com.zerobase.SelfWash.administer_store.domain.repository.MachineRepository;
import com.zerobase.SelfWash.administer_store.domain.repository.StoreRepository;
import com.zerobase.SelfWash.administer_store.domain.type.UsageStatus;
import com.zerobase.SelfWash.administer_store.service.MachineService;
import com.zerobase.SelfWash.administer_store.service.RedisManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MachineServiceImpl implements MachineService {

  private final StoreRepository storeRepository;
  private final MachineRepository machineRepository;

  private final RedisManageService redisManageService;

  @Override
  @Transactional
  public MachineDto create(Long storeId, MachineForm form) {
    Store store = storeRepository.findById(storeId)
        .orElseThrow(() -> new RuntimeException("해당하는 매장 정보가 없습니다."));

    //폐점되었거나 이미 가게 등록 승인이 된 경우에는 기계 추가 불가능
    if (store.isWithdraw() || store.isApproved()) {
      throw new RuntimeException("기계 추가가 불가능한 매장입니다.");
    }

    //매장데이터와 동기화
    Machine machine = Machine.from(form);
    store.addMachine(machine);

    return MachineDto.from(machineRepository.save(machine));
  }

  @Override
  @Transactional
  public void remove(Long storeId, Long machineId) {
    Store store = storeRepository.findById(storeId)
        .orElseThrow(() -> new RuntimeException("해당하는 매장 정보가 없습니다."));

    if (store.isWithdraw() || store.isApproved()) {
      throw new RuntimeException("기계 추가가 불가능한 매장입니다.");
    }

    Machine machine = machineRepository.findById(machineId)
        .orElseThrow(() -> new RuntimeException("해당하는 기계 정보가 없습니다."));

    //매장데이터와 동기화
    store.removeMachine(machine);
    machineRepository.delete(machine);
  }

  @Override
  @Transactional
  public MachineDto search(Long machineId) {

    return MachineDto.from(machineRepository.findById(machineId)
        .orElseThrow(() -> new RuntimeException("해당하는 기계 정보가 없습니다.")));
  }

  @Override
  @Transactional
  public void modify(Long machineId, MachineForm form) {
    Machine machine = machineRepository.findById(machineId)
        .orElseThrow(() -> new RuntimeException("해당하는 기계 정보가 없습니다."));

    machine.modify(form);
  }

  @Override
  @Transactional
  public void change(Long machineId, UsageStatus status) {
    Machine machine = machineRepository.findById(machineId)
        .orElseThrow(() -> new RuntimeException("해당하는 기계 정보가 없습니다."));

    if (machine.getUsageStatus() == UsageStatus.USING) {
      throw new RuntimeException("현재 사용중이기 때문에 상태 변경이 불가능한 기계 입니다.");
    }

    machine.setUsageStatus(status);

    redisManageService.addAndUpdateMachineToRedis(
        machine.getStore().getId(),
        MachineRedisDto.toDto(machine));
  }


}
