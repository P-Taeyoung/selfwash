package com.zerobase.SelfWash.owner_inquiry.service.impl;

import com.zerobase.SelfWash.owner_inquiry.domain.entity.OwnerInquiry;
import com.zerobase.SelfWash.owner_inquiry.domain.repository.OwnerInquiryRepository;
import com.zerobase.SelfWash.owner_inquiry.service.AdminInquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminInquiryServiceImpl implements AdminInquiryService {

  private final OwnerInquiryRepository ownerInquiryRepository;

  @Override
  @Transactional
  public void resolveInquiry(Long inquiryId) {
    OwnerInquiry inquiry = ownerInquiryRepository.findById(inquiryId)
        .orElseThrow(() -> new RuntimeException("해당하는 문의 내용이 없습니다."));

    if (inquiry.isResolved()) {
      throw new RuntimeException("이미 처리된 문의입니다.");
    }

    inquiry.setResolved(true);
  }
}
