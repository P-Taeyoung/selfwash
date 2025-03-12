package com.zerobase.SelfWash.owner_inquiry.domain.dto;

import com.zerobase.SelfWash.owner_inquiry.domain.entity.OwnerInquiry;
import com.zerobase.SelfWash.owner_inquiry.domain.form.OwnerInquiryForm;
import com.zerobase.SelfWash.owner_inquiry.domain.type.InquiryType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

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
  private LocalDateTime resolvedTime;

  public static OwnerInquiryDto from(OwnerInquiry inquiry) {
    return OwnerInquiryDto.builder()
        .inquiryId(inquiry.getId())
        .ownerId(inquiry.getOwnerId())
        .inquiryType(inquiry.getInquiryType())
        .contents(inquiry.getContents())
        .resolvedTime(inquiry.getResolvedTime())
        .build();

//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 문의처리완료");
//    try {
//      inquiryDto.setResolvedTime(inquiry.getResolvedTime().format(formatter));
//    } catch (NullPointerException e) {
//      inquiryDto.setResolvedTime("문의 미처리");
//    } catch (DateTimeParseException e) {
//      throw new RuntimeException(e + " : 유효한 시간이 아닙니다.");
//    }
//    return inquiryDto;
  }


}
