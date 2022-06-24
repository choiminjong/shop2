package com.shop2.controller;

import com.shop2.auth.PrincipalDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class PrincipalController {

    @GetMapping("/session")
    public PrincipalDetail index( ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetail detail = (PrincipalDetail)auth.getPrincipal();

        return detail;
    }
}
