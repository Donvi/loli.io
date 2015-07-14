package io.loli.sc.server.action;

import io.loli.sc.server.cache.ImageCache;
import io.loli.sc.server.entity.ImageApproveLog;
import io.loli.sc.server.entity.UploadedImage;
import io.loli.sc.server.entity.User;
import io.loli.sc.server.service.ImageApproveLogService;
import io.loli.sc.server.service.UploadedImageService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("approve")
@Named
public class ImageApproveAction {
    @Autowired
    private ImageCache imgCache;
    @Autowired
    private UploadedImageService imgService;
    @Inject
    private ImageApproveLogService logService;

    @RequestMapping("")
    public String list(Model model, HttpSession session) {
        if (session.getAttribute("user") != null) {
            if (!((User) session.getAttribute("user")).getVip()) {

                throw new RuntimeException("非法访问");
            } else {
            }
        } else {
            throw new RuntimeException("非法访问");
        }
        model.addAttribute("img", imgCache.next());
        return "approve/approve";
    }

    @RequestMapping(value = "type")
    public String type(Integer id, Integer type, Model model,
            HttpSession session) {
        if (session.getAttribute("user") != null) {
            if (!((User) session.getAttribute("user")).getVip()) {
                throw new RuntimeException("非法访问");

            } else {
            }
        } else {
            throw new RuntimeException("非法访问");
        }

        UploadedImage img = imgService.findById(id);

        ImageApproveLog log = new ImageApproveLog();
        log.setImg(img.getId());
        log.setUser(((User) session.getAttribute("user")).getId());
        log.setStatus(type.toString());
        logService.save(log);

        img.getInfo().setStatus(type);
        imgService.update(img);
        imgCache.removeFromCache(id);

        return "redirect:../approve";
    }

    @RequestMapping(value = "type", params = "admin")
    public String type1(Integer id, Integer type, Model model,
            HttpSession session) {
        if (session.getAttribute("user") != null) {
            if (!((User) session.getAttribute("user")).getVip()) {
                throw new RuntimeException("非法访问");

            } else {
            }
        } else {
            throw new RuntimeException("非法访问");
        }

        UploadedImage img = imgService.findById(id);

        ImageApproveLog log = new ImageApproveLog();
        log.setImg(img.getId());
        log.setUser(((User) session.getAttribute("user")).getId());
        log.setStatus(type.toString());
        logService.save(log);

        img.getInfo().setStatus(type);
        imgService.update(img);
        imgCache.removeFromCache(id);
        return "redirect:list";
    }

    @RequestMapping("list")
    public String adminList(Model model, HttpSession session) {
        if (session.getAttribute("user") != null) {
            if (((User) session.getAttribute("user")).getId() != 1) {
                throw new RuntimeException("非法访问");
            } else {
            }
        } else {
            throw new RuntimeException("非法访问");
        }
        List<UploadedImage> list = imgService.findIllegal();
        model.addAttribute("list", list);
        return "approve/list";
    }

    @RequestMapping("delete")
    public String delete(Integer id, Model model, HttpSession session) {
        if (session.getAttribute("user") != null) {
            if (((User) session.getAttribute("user")).getId() != 1) {
                throw new RuntimeException("非法访问");
            } else {
            }
        } else {
            throw new RuntimeException("非法访问");
        }
        ImageApproveLog log = new ImageApproveLog();
        log.setImg(id);
        log.setUser(((User) session.getAttribute("user")).getId());
        log.setStatus("delete");
        logService.save(log);

        imgService.delete(id);
        return this.adminList(model, session);
    }
}
