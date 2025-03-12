package com.zerobase.SelfWash.owner_inquiry.domain.entity;

import com.zerobase.SelfWash.owner_inquiry.domain.form.OwnerInquiryForm;
import com.zerobase.SelfWash.owner_inquiry.domain.type.InquiryType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OwnerInquiry {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private Long ownerId;

  @Enumerated(EnumType.STRING)
  private InquiryType inquiryType;
  private String contents;
  private LocalDateTime resolvedTime;

  public static OwnerInquiry from(Long ownerId, OwnerInquiryForm form) {
    return OwnerInquiry.builder()
        .ownerId(ownerId)
        .inquiryType(form.getInquiryType())
        .contents(form.getContents())
        .resolvedTime(null)
        .build();
  }

  public void modify(OwnerInquiryForm form) {
    this.setInquiryType(form.getInquiryType());
    this.setContents(form.getContents());
  }

}
