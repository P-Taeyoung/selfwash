package com.zerobase.SelfWash.owner_inquiry.controller;

import com.zerobase.SelfWash.owner_inquiry.domain.dto.OwnerInquiryDto;
import com.zerobase.SelfWash.owner_inquiry.domain.form.OwnerInquiryForm;
import com.zerobase.SelfWash.owner_inquiry.service.OwnerInquiryService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
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
@RequestMapping("/inquiry")
@RequiredArgsConstructor
public class OwnerInquiryController {

  private final OwnerInquiryService ownerInquiryService;

  //TODO 추후에 @RequestParam Long ownerId -> @AuthenticationPrincipal로 변경


  @Operation(
      summary = "문의 신청",
      tags = {"점주 문의"}
  )
  @PostMapping
  public ResponseEntity<OwnerInquiryDto> create(
      @RequestParam Long ownerId,
      @RequestBody OwnerInquiryForm form) {

    return ResponseEntity.ok(ownerInquiryService.create(ownerId, form));
  }

  @Operation(
      summary = "문의 내용 수정",
      tags = {"점주 문의"}
  )
  @PutMapping
  public ResponseEntity<String> modify(
      @RequestParam Long inquiryId,
      @RequestBody OwnerInquiryForm form) {

    ownerInquiryService.modify(inquiryId, form);
    return ResponseEntity.ok("문의 내용이 수정되었습니다.");
  }

  @Operation(
      summary = "문의 삭제",
      tags = {"점주 문의"}
  )
  @DeleteMapping
  public ResponseEntity<String> remove(
      @RequestParam Long inquiryId
  ) {
    ownerInquiryService.remove(inquiryId);
    return ResponseEntity.ok("문의 내용이 삭제되었습니다.");
  }

  @Operation(
      summary = "문의 내용 조회",
      tags = {"점주 문의"}
  )
  @GetMapping
  public ResponseEntity<OwnerInquiryDto> search(
      @RequestParam Long inquiryId
  ) {
    return ResponseEntity.ok(ownerInquiryService.search(inquiryId));
  }

  @Operation(
      summary = "문의 내역 조회",
      tags = {"점주 문의"}
  )
  @GetMapping("/ownerinquiry")
  public ResponseEntity<List<OwnerInquiryDto>> searchOwnerInquiry(
      @RequestParam Long OwnerId
  )
  {
    return ResponseEntity.ok(ownerInquiryService.searchOwnerInquiry(OwnerId));
  }

}
