package com.zerobase.SelfWash.search_address.controller;

import com.zerobase.SelfWash.search_address.dto.AddressDto;
import com.zerobase.SelfWash.search_address.service.KakaoAddressService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KakaoAddressController {

  private final KakaoAddressService kakaoAddressService;

  @Operation(
      summary = "주소 키워드 검색",
      tags = {"카카오 주소 조회"}
  )
  @GetMapping
  public ResponseEntity<List<AddressDto>> searchAddress(
      @RequestParam String query,
      @RequestParam Integer page) {

    List<AddressDto> addressList = kakaoAddressService.searchAddress(query, page);

    if (addressList.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(addressList, HttpStatus.OK);
  }

}
