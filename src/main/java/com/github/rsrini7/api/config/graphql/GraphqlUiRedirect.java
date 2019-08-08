package com.github.rsrini7.api.config.graphql;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/graphql")
public class GraphqlUiRedirect {

  @RequestMapping(method = RequestMethod.GET)
  public RedirectView redirectToGraphqlUi(RedirectAttributes attributes) {
    return new RedirectView("graphql-ui.html");
  }
}
