package com.github.rsrini7.api.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
public class IndexPageRedirect {

  @RequestMapping(method = RequestMethod.GET)
  public RedirectView redirectToIndexPage(RedirectAttributes attributes) {
    return new RedirectView("index.html");
  }
}
