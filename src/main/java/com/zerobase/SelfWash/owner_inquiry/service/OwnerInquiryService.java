package com.zerobase.SelfWash.owner_inquiry.service;

import com.zerobase.SelfWash.owner_inquiry.domain.dto.OwnerInquiryDto;
import com.zerobase.SelfWash.owner_inquiry.domain.form.OwnerInquiryForm;
import java.util.List;

public interface OwnerInquiryService {

  /**
   * 점주 문의 생성
   */
  OwnerInquiryDto create(Long ownerId, OwnerInquiryForm form);

  /**
   * 문의 내용 수정 (답변 미완료 상태에서만 수정 가능)
   */
  void modify(Long inquiryId, OwnerInquiryForm form);

  /**
   * 문의 삭제 (바로 데이터 삭제됨)
   */
  void remove(Long inquiryId);

  /**
   *  문의 내용 조회
   */
  OwnerInquiryDto search(Long inquiryId);

  /**
   * (로그인 한) 점주의 문의 내용 전체 조회
   */
  List<OwnerInquiryDto> searchOwnerInquiry(Long ownerId);

}
