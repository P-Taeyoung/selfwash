package com.zerobase.SelfWash.owner_inquiry.domain.entity;

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
  private InquiryType inquiryType;
  private String contents;
  private boolean resolved;

  public static OwnerInquiry from(Long ownerId, OwnerInquiryForm form) {
    return OwnerInquiry.builder()
        .ownerId(ownerId)
        .inquiryType(form.getInquiryType())
        .contents(form.getContents())
        .resolved(false)
        .build();
  }

  public void modify(OwnerInquiryForm form) {
    this.setInquiryType(form.getInquiryType());
    this.setContents(form.getContents());
  }

}
