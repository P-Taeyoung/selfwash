package com.zerobase.SelfWash.administer_store.service;

import com.zerobase.SelfWash.administer_store.domain.dto.StoreDto;
import com.zerobase.SelfWash.administer_store.domain.form.StoreForm;
import com.zerobase.SelfWash.administer_store.domain.form.StoreModifyForm;

public interface StoreService {

  //매장 정보 등록
  StoreDto create(StoreForm form);

  //매장 정보 조회
  StoreDto search(Long storeId);

  //매장 정보 수정
  void modify(Long storeId, StoreModifyForm form);

  //매장 정보 삭제
  void remove(Long reqId);

  //매장 영업 승인
  void approve(Long storeId);


}
