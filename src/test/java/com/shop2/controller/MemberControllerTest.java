package com.shop2.controller;

import com.shop2.dto.MemberFormDto;
import com.shop2.entity.Member;
import com.shop2.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class MemberControllerTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember(String username, String password) {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setUsername(username);
        memberFormDto.setPassword(password);
        Member member = Member.createMember(memberFormDto, passwordEncoder);
        return memberService.saveMember(member);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() throws Exception {
        String username = "test";
        String password = "1234";
        this.createMember(username, password);

        mockMvc.perform(formLogin().userParameter("username")
                .loginProcessingUrl("/login_proc")
                .user(username).password(password))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    public void loginFailTest() throws Exception{
        String username = "test";
        String password = "1234";
        this.createMember(username, password);
        mockMvc.perform(formLogin().userParameter("username")
                .loginProcessingUrl("/login_proc")
                .user(username).password("12345"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }

}