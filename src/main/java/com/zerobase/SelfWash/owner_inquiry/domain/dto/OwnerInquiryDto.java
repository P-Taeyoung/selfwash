package com.zerobase.SelfWash.owner_inquiry.domain.dto;

import com.zerobase.SelfWash.owner_inquiry.domain.entity.OwnerInquiry;
import com.zerobase.SelfWash.owner_inquiry.domain.form.OwnerInquiryForm;
import com.zerobase.SelfWash.owner_inquiry.domain.type.InquiryType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OwnerInquiryDto {

  private long inquiryId;

  private Long ownerId;
  private InquiryType inquiryType;
  private String contents;
  private boolean resolved;

  public static OwnerInquiryDto from(OwnerInquiry inquiry) {
    return OwnerInquiryDto.builder()
        .inquiryId(inquiry.getId())
        .ownerId(inquiry.getOwnerId())
        .inquiryType(inquiry.getInquiryType())
        .contents(inquiry.getContents())
        .resolved(inquiry.isResolved())
        .build();
  }

}
