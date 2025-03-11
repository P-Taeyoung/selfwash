package com.zerobase.SelfWash.owner_inquiry.domain.form;

import com.zerobase.SelfWash.owner_inquiry.domain.type.InquiryType;
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
public class OwnerInquiryForm {

  private InquiryType inquiryType;
  private String contents;

}
