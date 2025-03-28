package com.zerobase.SelfWash.use_machine.service;

import static com.zerobase.SelfWash.administer_store.domain.type.UsageStatus.USABLE;

import com.zerobase.SelfWash.administer_store.domain.dto.MachineRedisDto;
import com.zerobase.SelfWash.administer_store.domain.entity.Machine;
import com.zerobase.SelfWash.administer_store.domain.repository.MachineRepository;
import com.zerobase.SelfWash.administer_store.service.RedisManageService;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MachineSchedulerService {

  private final MachineRepository machineRepository;
  private final RedisManageService redisManageService;

  @Scheduled(cron = "0 * * * * *")
  public void updateMachineStatus() {
    LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    int pageSize = 100;
    int pageNumber = 0;
    Page<Machine> machinePage;

    log.info("기계 사용현황 업데이트 스케줄러 시작: {}", LocalDateTime.now());
    try {
      do {

        //페이지 단위로 데이터 조회
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        machinePage = machineRepository.findMachinesEndingBefore(now, pageable);

        log.info("사용가능 상태로 변경된 기계 수 : {}", machinePage.getNumberOfElements());

        //현재 페이지 처리
        changeMachineStatus(machinePage);
        //다음 페이지로 이동
        pageNumber++;

      } while (machinePage.hasNext());
    } catch (Exception e) {
      log.error("기계 사용현황 업데이트 중 오류 발생 : {}",e.getMessage());
    } finally {
      log.info("기계 사용현황 업데이트 스케줄러 종료: {}", LocalDateTime.now());
    }

  }

  private void changeMachineStatus(Page<Machine> machinePage) {
    for (Machine machine : machinePage) {
      machine.setEndTime(null);
      machine.setCustomerId(null);
      machine.setUsageStatus(USABLE);

      // 상태 변경 시 레디스에도 업데이트
      redisManageService.addAndUpdateMachineToRedis(
          machine.getStore().getId(),
          MachineRedisDto.toDto(machine));

      log.info("상태 업데이트 기계 ID  : {}", machine.getId());
    }
    machineRepository.saveAll(machinePage.getContent());
  }

}
