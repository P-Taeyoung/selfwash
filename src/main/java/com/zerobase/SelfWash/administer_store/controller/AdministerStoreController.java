package com.zerobase.SelfWash.administer_store.controller;

import com.zerobase.SelfWash.administer_store.domain.dto.StoreDto;
import com.zerobase.SelfWash.administer_store.domain.form.StoreForm;
import com.zerobase.SelfWash.administer_store.service.StoreService;
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
@RequestMapping("/administer/store")
//TODO 관리인 권한 설정
public class AdministerStoreController {

  private final StoreService storeService;

  @Operation(
      summary = "매장 등록",
      tags = {"매장 관리"}
  )
  @PostMapping
  public ResponseEntity<StoreDto> create(@RequestBody StoreForm form) {
    return ResponseEntity.ok(storeService.create(form));
  }

  @Operation(
      summary = "매장 조회",
      tags = {"매장 관리"}
  )
  @GetMapping
  public ResponseEntity<StoreDto> search(@RequestParam long storeId) {
    return ResponseEntity.ok(storeService.search(storeId));
  }


  @Operation(
      summary = "매장 수정",
      tags = {"매장 관리"}
  )
  @PutMapping
  public ResponseEntity<StoreDto> modify(@RequestParam long storeId, @RequestBody StoreForm form) {
    return ResponseEntity.ok(storeService.modify(storeId, form));
  }


  @Operation(
      summary = "매장 삭제",
      tags = {"매장 관리"}
  )
  @DeleteMapping
  public ResponseEntity<String> remove(@RequestParam long storeId) {
    storeService.remove(storeId);
    return ResponseEntity.ok("매장이 폐점 처리되었습니다.");
  }

  @Operation(
      summary = "매장 등록 승인",
      tags = {"매장 관리"}
  )
  @PutMapping("/approve")
  public ResponseEntity<String> approve(@RequestParam long storeId) {
    storeService.approve(storeId);
    return ResponseEntity.ok("매장 등록이 승인되었습니다.");
  }

}
