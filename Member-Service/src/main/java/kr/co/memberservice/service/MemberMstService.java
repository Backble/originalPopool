package kr.co.memberservice.service;

import kr.co.memberservice.domain.dto.MemberDto;
import kr.co.memberservice.domain.dto.MemberMstDto;

public interface MemberMstService {

    //create
    void signUp(MemberMstDto.CREATE create);

    //login
    MemberMstDto.TOKEN login(MemberMstDto.LOGIN login);

    //common
    Boolean checkIdentity(String identity);



}
