package io.loli.sc.server.action;

import io.loli.sc.server.cache.ImageCache;
import io.loli.sc.server.entity.UploadedImage;
import io.loli.sc.server.service.UploadedImageService;

import javax.inject.Inject;
import javax.inject.Named;

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
    public String list(Model model) {
        model.addAttribute("img", imgCache.next());
        return "approve/approve";
    }

    @RequestMapping("type")
    public String type(Integer id, Integer type, Model model) {
        UploadedImage img = imgService.findById(id);
        img.getInfo().setStatus(type);
        imgService.update(img);
        imgCache.removeFromCache(id);
        return "redirect:../approve";
    }
}
