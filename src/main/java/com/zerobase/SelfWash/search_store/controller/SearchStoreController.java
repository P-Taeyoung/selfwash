package com.zerobase.SelfWash.search_store.controller;

import com.zerobase.SelfWash.search_store.dto.SearchMachineDto;
import com.zerobase.SelfWash.search_store.dto.SearchStoreDto;
import com.zerobase.SelfWash.search_store.service.SearchStoreService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nearbystore")
@RequiredArgsConstructor
public class SearchStoreController {

  private final SearchStoreService searchStoreService;

  @Operation(
      summary = "주변 매장 조회",
      tags = {"매장 조회"}
  )
  @GetMapping
  public ResponseEntity<?> findStore(
      @RequestParam double latitude,
      @RequestParam double longitude) {

      List<SearchStoreDto> searchStoreDtos = searchStoreService.searchStore(latitude, longitude);

      if (searchStoreDtos.isEmpty()) {
        return ResponseEntity.ok("주변 매장이 존재하지 않습니다.");
      }

      return ResponseEntity.ok(searchStoreDtos);
  }

  @Operation(
      summary = "매장 기계 조회",
      tags = {"매장 조회"}
  )
  @GetMapping("/machine")
  public ResponseEntity<?> findStore(
      @RequestParam Long storeId) {

    List<SearchMachineDto> machines = searchStoreService.searchMachine(storeId);

    if (machines.isEmpty()) {
      return ResponseEntity.ok("주변 매장이 존재하지 않습니다.");
    }

    return ResponseEntity.ok(machines);
  }
}
