package com.zerobase.SelfWash.use_machine.service.impl;

import static com.zerobase.SelfWash.administer_store.domain.type.UsageStatus.RESERVED;
import static com.zerobase.SelfWash.administer_store.domain.type.UsageStatus.USABLE;

import com.zerobase.SelfWash.administer_store.domain.dto.MachineRedisDto;
import com.zerobase.SelfWash.administer_store.domain.entity.Machine;
import com.zerobase.SelfWash.administer_store.domain.repository.MachineRepository;
import com.zerobase.SelfWash.administer_store.service.RedisManageService;
import com.zerobase.SelfWash.use_machine.domain.dto.MachineReserveDto;
import com.zerobase.SelfWash.use_machine.domain.entity.MachineReserve;
import com.zerobase.SelfWash.use_machine.domain.repository.MachineReserveRepository;
import com.zerobase.SelfWash.use_machine.service.MachineReserveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MachineReserveServiceImpl implements MachineReserveService {

  private final MachineReserveRepository machineReserveRepository;
  private final MachineRepository machineRepository;
  private final RedisManageService redisManageService;


  @Override
  @Transactional
  public MachineReserveDto reserve(Long customerId, Long machineId) {
    Machine machine = machineRepository.findById(machineId)
        .orElseThrow(() -> new RuntimeException("해당하는 기계 정보가 없습니다."));

    if (!machine.getStore().isOpened()) {
      throw new RuntimeException("운영 중인 매장이 아닙니다.");
    }

    if (machine.getUsageStatus() != USABLE) {
      throw new RuntimeException("예약 불가능한 기계입니다.");
    }

    MachineReserve machineReserve = machineReserveRepository.save(MachineReserve.from(customerId, machineId));

    // 기계DB 예약 정보 업데이트
    machine.setEndTime(machineReserve.getEndTime());
    machine.setCustomerId(customerId);
    machine.setUsageStatus(RESERVED);

    //레디스에도 적용
    redisManageService.addAndUpdateMachineToRedis(
        machine.getStore().getId(),
        MachineRedisDto.toDto(machine));

    return MachineReserveDto.from(machineReserve);
  }

  @Override
  @Transactional
  public void reserveCancel(Long customerId, Long reserveId) {
    MachineReserve machineReserve = machineReserveRepository.findById(reserveId)
        .orElseThrow(() -> new RuntimeException("해당하는 예약 정보가 없습니다."));

    if (machineReserve.getCustomerId() != customerId) {
      throw new RuntimeException("예약자가 아닙니다.");
    }

    Machine machine = machineRepository.findById(machineReserve.getMachineId())
        .orElseThrow(() -> new RuntimeException("해당하는 기계 정보가 없습니다."));

    if (machine.getUsageStatus() != RESERVED) {
      throw new RuntimeException("예약상태가 아닙니다.");
    }

    // 기계DB 예약 가능 상태로 업데이트
    machine.setEndTime(null);
    machine.setCustomerId(null);
    machine.setUsageStatus(USABLE);

    //레디스에도 적용
    redisManageService.addAndUpdateMachineToRedis(
        machine.getStore().getId(),
        MachineRedisDto.toDto(machine));

    machineReserveRepository.delete(machineReserve);
  }
}
