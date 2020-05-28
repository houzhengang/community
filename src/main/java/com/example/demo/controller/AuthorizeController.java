package com.example.demo.controller;

import com.example.demo.DTO.AccessTokenDTO;
import com.example.demo.DTO.GithubUser;
import com.example.demo.provideer.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secrate}")
    private String clientSecrate;
    @Value("${github.redict.uri}")
    private String redictUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state){ //接收callback的参数 code 和state
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redictUri);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_secret(clientSecrate);
        String accesstoken = githubProvider.getAccesstoken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accesstoken);
        System.out.println(user.getName());


        return "index";
    }
}
