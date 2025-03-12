package com.zerobase.SelfWash.owner_inquiry.service.impl;

import com.zerobase.SelfWash.owner_inquiry.domain.dto.OwnerInquiryDto;
import com.zerobase.SelfWash.owner_inquiry.domain.entity.OwnerInquiry;
import com.zerobase.SelfWash.owner_inquiry.domain.form.OwnerInquiryForm;
import com.zerobase.SelfWash.owner_inquiry.domain.repository.OwnerInquiryRepository;
import com.zerobase.SelfWash.owner_inquiry.service.OwnerInquiryService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OwnerInquiryServiceImpl implements OwnerInquiryService {

  private final OwnerInquiryRepository ownerInquiryRepository;

  @Override
  @Transactional
  public OwnerInquiryDto create(Long ownerId ,OwnerInquiryForm form) {

    return OwnerInquiryDto.from(ownerInquiryRepository.save(OwnerInquiry.from(ownerId, form)));
  }

  @Override
  @Transactional
  public void modify(Long inquiryId, OwnerInquiryForm form) {
    OwnerInquiry inquiry = ownerInquiryRepository.findById(inquiryId)
        .orElseThrow(() -> new RuntimeException("해당하는 문의 내용이 없습니다."));

    if (inquiry.getResolvedTime() != null) {
      throw new RuntimeException("이미 처리 완료된 문의는 수정할 수 없습니다.");
    }

    inquiry.modify(form);
  }

  @Override
  @Transactional
  public void remove(Long inquiryId) {
    OwnerInquiry inquiry = ownerInquiryRepository.findById(inquiryId)
        .orElseThrow(() -> new RuntimeException("해당하는 문의 내용이 없습니다."));

    ownerInquiryRepository.delete(inquiry);
  }

  @Override
  @Transactional
  public OwnerInquiryDto search(Long inquiryId) {

    return OwnerInquiryDto.from(ownerInquiryRepository.findById(inquiryId)
        .orElseThrow(() -> new RuntimeException("해당하는 문의 내용이 없습니다.")));
  }

  @Override
  @Transactional
  public List<OwnerInquiryDto> searchOwnerInquiry(Long ownerId) {
    List<OwnerInquiry> ownerInquiryList = ownerInquiryRepository.findAllByOwnerId(ownerId);

    if (ownerInquiryList.isEmpty()) {
      throw new RuntimeException("요청 문의사항이 존재하지 않습니다.");
    }

    return ownerInquiryList.stream().map(OwnerInquiryDto::from).collect(Collectors.toList());
  }
}
