package com.zerobase.SelfWash.administer_store.controller;

import com.zerobase.SelfWash.administer_store.domain.dto.MachineDto;
import com.zerobase.SelfWash.administer_store.domain.form.MachineForm;
import com.zerobase.SelfWash.administer_store.service.MachineService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/administer/machine")
public class AdministerMachineController {

  private final MachineService machineService;

  @Operation(
      summary = "기계 정보 등록",
      tags = {"기계 관리"}
  )
  @PostMapping
  public ResponseEntity<MachineDto> create(@RequestParam Long storeId, @RequestBody MachineForm form) {
    return ResponseEntity.ok(machineService.create(storeId, form));
  }

  @Operation(
      summary = "기계 정보 삭제",
      tags = {"기계 관리"}
  )
  @DeleteMapping
  public ResponseEntity<String> delete(@RequestParam Long storeId, @RequestParam Long machineId) {
    machineService.remove(storeId, machineId);
    return ResponseEntity.ok("정상적으로 기계 등록이 삭제되었습니다.");
  }

  @Operation(
      summary = "기계 정보 조회",
      tags = {"기계 관리"}
  )
  @GetMapping
  public ResponseEntity<MachineDto> search(@RequestParam Long machineId) {
    return ResponseEntity.ok(machineService.search(machineId));
  }

  @Operation(
      summary = "기계 정보 수정",
      tags = {"기계 관리"}
  )
  @PutMapping
  public ResponseEntity<MachineDto> modify(@RequestParam Long machineId, @RequestBody MachineForm form) {
    return ResponseEntity.ok(machineService.modify(machineId, form));
  }




}
