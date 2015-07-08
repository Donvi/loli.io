package io.loli.sc.server.action;

import io.loli.sc.server.cache.ImageCache;
import io.loli.sc.server.entity.UploadedImage;
import io.loli.sc.server.entity.User;
import io.loli.sc.server.service.UploadedImageService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("approve")
@Named
public class ImageApproveAction {
    @Inject
    private ImageCache imgCache;
    @Autowired
    private UploadedImageService imgService;

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

    @RequestMapping("type")
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
        img.getInfo().setStatus(type);
        imgService.update(img);
        imgCache.removeFromCache(id);
        return "redirect:../approve";
    }
}
