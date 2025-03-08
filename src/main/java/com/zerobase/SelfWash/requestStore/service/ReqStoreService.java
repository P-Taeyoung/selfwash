package com.zerobase.SelfWash.requestStore.service;

import com.zerobase.SelfWash.requestStore.domain.dto.store.ReqRegisterStoreDto;
import com.zerobase.SelfWash.requestStore.domain.form.store.ReqRegisterStoreForm;

public interface ReqStoreService {

  //요청
  Object create(Object form);

  //요청 내용 수정
  Object modify(Object form);

  //요청 조회
  Object read(Long id);

  //요청 삭제
  void remove(Long id);



}
