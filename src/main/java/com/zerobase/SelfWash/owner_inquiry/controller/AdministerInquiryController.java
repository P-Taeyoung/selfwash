package com.zerobase.SelfWash.owner_inquiry.controller;

import com.zerobase.SelfWash.owner_inquiry.domain.dto.OwnerInquiryDto;
import com.zerobase.SelfWash.owner_inquiry.service.AdminInquiryService;
import com.zerobase.SelfWash.owner_inquiry.service.OwnerInquiryService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inquiry/admin")
@RequiredArgsConstructor
//TODO 관리자 권한 실행
public class AdministerInquiryController {

  private final AdminInquiryService adminInquiryService;
  private final OwnerInquiryService ownerInquiryService;

  @Operation(
      summary = "문의 처리",
      tags = {"점주 문의 관리"}
  )
  @PutMapping
  public ResponseEntity<String> resolveInquiry(@RequestParam Long inquiryId) {
    adminInquiryService.resolveInquiry(inquiryId);
    return ResponseEntity.ok("문의 처리 완료되었습니다.");
  }

  @Operation(
      summary = "점주 문의 내역 조회",
      tags = {"점주 문의 관리"}
  )
  @GetMapping
  public ResponseEntity<List<OwnerInquiryDto>> searchOwnerInquiry(@RequestParam Long ownerId) {

    return ResponseEntity.ok(ownerInquiryService.searchOwnerInquiry(ownerId));
  }

}
