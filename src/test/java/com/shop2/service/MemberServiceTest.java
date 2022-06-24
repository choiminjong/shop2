package com.shop2.service;

import static org.junit.jupiter.api.Assertions.*;

import com.shop2.dto.MemberFormDto;
import com.shop2.entity.Member;
import com.shop2.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberRepository memberRepository;

    public Member createMember(){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setUsername("홍길동");
        memberFormDto.setPassword("1234");
        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest(){
        Member member = createMember();
        Member savedMember = memberService.saveMember(member);
        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getUsername(), savedMember.getUsername());
        assertEquals(member.getPassword(), savedMember.getPassword());
        assertEquals(member.getRoles(), savedMember.getRoles());
    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    public void saveDuplicateMemberTest(){
        Member member1 = createMember();
        Member member2 = createMember();
        memberService.saveMember(member1);

        Throwable e = assertThrows(IllegalStateException.class, () -> {
                        memberService.saveMember(member2);});
        assertEquals("이미 가입된 회원입니다.", e.getMessage());

    }

}