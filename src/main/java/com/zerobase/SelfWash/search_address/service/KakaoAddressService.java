package com.zerobase.SelfWash.search_address.service;

import com.zerobase.SelfWash.search_address.dto.AddressDto;
import com.zerobase.SelfWash.search_address.dto.KakaoAddressResponse;
import java.util.List;
import reactor.core.publisher.Mono;

public interface KakaoAddressService {

  /**
   * 키워드로 주소 검색
   */
  List<AddressDto> searchAddress(String query, Integer page);
}
