package io.loli.sc.server.action;

import io.loli.sc.server.entity.AccessStatus;
import io.loli.sc.server.entity.ImageStatus;
import io.loli.sc.server.service.AccessStatusService;
import io.loli.sc.server.service.ImageStatusService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "stat")
@Named
public class StatisticAction {
    @Inject
    private ImageStatusService is;
    @Inject
    private AccessStatusService as;

    @RequestMapping(value = "")
    public String get(Model model) {
        model.addAttribute("stats", is.findLast30Days());
        return "stat/stat";
    }

    @RequestMapping(value = "image")
    @ResponseBody
    public List<ImageStatus> stat() {
        return is.findLast30Days();
    }

    @RequestMapping(value = "access")
    @ResponseBody
    public List<AccessStatus> access() {
        return as.findLast30Days();
    }

}
