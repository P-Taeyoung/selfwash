package com.zerobase.SelfWash.use_machine.controller;

import com.zerobase.SelfWash.use_machine.domain.dto.MachineReserveDto;
import com.zerobase.SelfWash.use_machine.service.MachineReserveService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reserve")
public class MachineReserveController {

  private final MachineReserveService machineReserveService;

  @Operation(
      summary = "기계 예약",
      tags = {"기계 예약"}
  )
  @PostMapping
  public ResponseEntity<MachineReserveDto> reserve(
      @RequestParam Long customerId,
      @RequestParam Long machineId) {

    return ResponseEntity.ok(machineReserveService.reserve(customerId, machineId));
  }

  @Operation(
      summary = "기계 예약 취소",
      tags = {"기계 예약"}
  )
  @DeleteMapping
  public ResponseEntity<String> reserveCancel(
      @RequestParam Long customerId,
      @RequestParam Long machineId) {
    machineReserveService.reserveCancel(customerId, machineId);
    return ResponseEntity.ok("예약이 취소되었습니다.");
  }
}
