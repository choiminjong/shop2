package com.shop2.auth;

import com.shop2.entity.Member;
import com.shop2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member principal = memberRepository.findByUsername(username)
                .orElseThrow(()->{
                    return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다." + username);
                });

        return new PrincipalDetail(principal); //시큐리티의 세션에 유저 정보가 저장이됨
    }
}