package com.zerobase.SelfWash.administer_store.controller.owner;

import com.zerobase.SelfWash.administer_store.service.ManageStoreService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manage/store")
public class ManageStoreController {

  private final ManageStoreService manageStoreService;

  @Operation(
      summary = "매장 운영 상태 변경",
      tags = {"매장 관리"}
  )
  @PutMapping
  public ResponseEntity<String> storeOperationChange(@RequestParam Long storeId) {
    manageStoreService.storeOperationChange(storeId);
    return ResponseEntity.ok("매장 운영 상태를 변경했습니다.");
  }
}
