package io.loli.sc.server.action;

import io.loli.sc.server.service.MailService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Named
@RequestMapping(value = "mail")
public class MailAction {

    @Inject
    private MailService mailService;

    @RequestMapping(value = "send", method = RequestMethod.POST)
    @ResponseBody
    public String sendEmail(@RequestParam("email") String email,
            HttpServletRequest request) {
        request.getSession().setAttribute("token", mailService.save(email));
        return String.valueOf(true);
    }

    @RequestMapping(value = "validate", method = RequestMethod.POST)
    @ResponseBody
    public String verify(@RequestParam("token") String token,
            HttpServletRequest request) {
        Object tokenInSession = request.getSession().getAttribute("token");
        boolean result = tokenInSession != null
                && ((String) tokenInSession).equals(token);
        return String.valueOf(result);
    }

}
