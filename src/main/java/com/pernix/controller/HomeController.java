package com.pernix.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pernix.service.hacienda.oauth2.OAuth2Service;

@Controller
public class HomeController {

    @Autowired
    OAuth2Service HaciendaOAuth2Service;

    @RequestMapping("/")
    String index() {
      if(StringUtils.isEmpty(HaciendaOAuth2Service.getAccessToken()))
          return "error";
      return "index";
    }
}
