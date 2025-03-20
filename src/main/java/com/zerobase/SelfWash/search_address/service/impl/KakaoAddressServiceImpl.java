package com.zerobase.SelfWash.search_address.service.impl;

import com.zerobase.SelfWash.search_address.dto.AddressDto;
import com.zerobase.SelfWash.search_address.dto.KakaoAddressResponse;
import com.zerobase.SelfWash.search_address.service.KakaoAddressService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoAddressServiceImpl implements KakaoAddressService {

  private final int pageSize = 5;
  private final WebClient webClient;

  @Override
  public List<AddressDto> searchAddress(String query, Integer page) {
    try {
      return getAddressFromKakao(query, page)
          .map(KakaoAddressResponse::getDocuments)
          .map(documents -> documents.stream().map(AddressDto::from).collect(
              Collectors.toList()))
          .block();
    } catch (Exception e) {
      log.error(e.getMessage());
      return new ArrayList<>();
    }
  }

  private Mono<KakaoAddressResponse> getAddressFromKakao(String query, Integer page) {
    log.info("주소 검색: query={}, page={}, size={}", query, page, pageSize);

    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/keyword.json")
            .queryParam("query", query)
            .queryParamIfPresent("page", java.util.Optional.ofNullable(page))
            .queryParamIfPresent("size", java.util.Optional.of(pageSize))
            .build())
        .retrieve()
        .bodyToMono(KakaoAddressResponse.class)
        .doOnSuccess(response -> log.info("주소 검색 성공: 총 {}건 {}", response.getMeta().getTotalCount(), response.getDocuments()))
        .doOnError(error -> log.error("주소 검색 실패: {}", error.getMessage()));
  }
}
