package com.zerobase.SelfWash.customer.use_machine.controller;

import com.zerobase.SelfWash.customer.use_machine.domain.dto.MachineUseDto;
import com.zerobase.SelfWash.customer.use_machine.domain.form.MachineUseForm;
import com.zerobase.SelfWash.customer.use_machine.service.MachineUseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/use")
@RequiredArgsConstructor
public class MachineUseController {

  private final MachineUseService machineUseService;

  @Operation(
      summary = "기계 사용 시작",
      tags = {"기계 사용"}
  )
  @PostMapping
  public ResponseEntity<MachineUseDto> use(
      @RequestParam Long customerId,
      @RequestBody MachineUseForm form) {
    return ResponseEntity.ok(machineUseService.use(customerId, form));
  }

  @Operation(
      summary = "기계 사용 종료",
      tags = {"기계 사용"}
  )
  @PutMapping
  public ResponseEntity<String> finish(
      @RequestParam Long machineId
  ) {
    machineUseService.finish(machineId);
    return ResponseEntity.ok("기계 사용이 종료되었습니다.");
  }


}
