package com.zerobase.SelfWash.customer.use_machine.service.impl;

import static com.zerobase.SelfWash.administer_store.domain.type.UsageStatus.RESERVED;
import static com.zerobase.SelfWash.administer_store.domain.type.UsageStatus.UNUSABLE;
import static com.zerobase.SelfWash.administer_store.domain.type.UsageStatus.USABLE;
import static com.zerobase.SelfWash.administer_store.domain.type.UsageStatus.USING;

import com.zerobase.SelfWash.administer_store.domain.dto.MachineRedisDto;
import com.zerobase.SelfWash.administer_store.domain.entity.Machine;
import com.zerobase.SelfWash.administer_store.domain.repository.MachineRepository;
import com.zerobase.SelfWash.administer_store.service.RedisManageService;
import com.zerobase.SelfWash.customer.use_machine.domain.dto.MachineUseDto;
import com.zerobase.SelfWash.customer.use_machine.domain.entity.MachineUse;
import com.zerobase.SelfWash.customer.use_machine.domain.form.MachineUseForm;
import com.zerobase.SelfWash.customer.use_machine.domain.repository.MachineUseRepository;
import com.zerobase.SelfWash.customer.use_machine.service.MachineUseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MachineUseServiceImpl implements MachineUseService {

  private final MachineUseRepository machineUseRepository;
  private final MachineRepository machineRepository;
  private final RedisManageService redisManageService;

  @Override
  @Transactional
  public MachineUseDto use(Long customerId, MachineUseForm form) {
    log.info("machine Id : {}", form.getMachineId());
    Machine machine = machineRepository.findById(form.getMachineId())
        .orElseThrow(() ->  new RuntimeException("해당하는 기계 정보가 없습니다."));

    if (!usableMachine(customerId, machine)) {
      throw new RuntimeException("사용 불가능한 기계입니다.");
    }

    MachineUse machineUse = machineUseRepository.save(MachineUse.from(customerId, form));

    // 기계DB 사용정보 업데이트
    machine.setEndTime(machineUse.getEndTime());
    machine.setUsageStatus(USING);

    // 레디스도 업데이트
    redisManageService.addAndUpdateMachineToRedis(
        machine.getStore().getId(),
        MachineRedisDto.toDto(machine));

    return MachineUseDto.from(machineUse);
  }

  @Override
  @Transactional
  public void finish(Long machineId) {
    Machine machine = machineRepository.findById(machineId)
        .orElseThrow(() -> new RuntimeException("해당하는 기계 정보가 없습니다."));

    // 기계DB 사용 가능 상태로 업데이트
    machine.setEndTime(null);
    machine.setUsageStatus(USABLE);

    // 레디스도 업데이트
    redisManageService.addAndUpdateMachineToRedis(
        machine.getStore().getId(),
        MachineRedisDto.toDto(machine));
  }

  private boolean usableMachine(Long customerId, Machine machine) {
    //운영하지 않는 매장인 경우
    if (!machine.getStore().isOpened()) {
      log.warn("운영 중단 매장 : {}", machine.getStore().getId());
      return false;
    }

    // 사용 불가 상태인 경우
    if (machine.getUsageStatus() == UNUSABLE || machine.getUsageStatus() == USING) {
      log.warn("현재 사용 불가 기계 : {}", machine.getId());
      return false;
    }

    // 예약된 상태인 경우
    if (machine.getUsageStatus() == RESERVED) {

      Long bookedCustomerId = machine.getCustomerId();

      // 예약자 정보가 없는 경우 (비정상 처리)
      if (bookedCustomerId == null) {
        log.warn("예약 정보 손상 : {}", customerId);
        return false;
      }

      // 예약자가 아닌 경우
      if (!bookedCustomerId.equals(customerId)) {
        log.warn("예약자 정보 불일치 : {}", customerId);
        return false;
      }
    }
    return true;
  }
}
