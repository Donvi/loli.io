package io.loli.sc.server.cache;

import io.loli.sc.server.entity.UploadedImage;
import io.loli.sc.server.service.UploadedImageService;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

@Named
@Singleton
public class ImageCache {
    @Autowired
    private UploadedImageService service;

    private static Map<Integer, UploadedImage> imageMap = new ConcurrentHashMap<Integer, UploadedImage>();

    @PostConstruct
    private void init() {
        reloadCache();
    }

    @Scheduled(cron = "0 */30 * * * ?")
    private void reloadCache() {
        List<UploadedImage> imgs = service.findNotVerifiedInDays(3);
        for (UploadedImage img : imgs) {
            imageMap.put(img.getId(), img);
        }
    }

    public void removeFromCache(Integer i) {
        imageMap.remove(i);
    }

    public UploadedImage next() {

        for (Entry<Integer, UploadedImage> e : imageMap.entrySet()) {
            return e.getValue();
        }
        return null;
    }

}
