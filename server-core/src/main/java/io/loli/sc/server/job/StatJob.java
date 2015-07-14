package io.loli.sc.server.job;

import io.loli.sc.server.entity.ImageStatus;
import io.loli.sc.server.service.ImageStatusService;
import io.loli.sc.server.service.UploadedImageService;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import org.springframework.scheduling.annotation.Scheduled;

public class StatJob {
    @Inject
    private UploadedImageService service;

    @Inject
    private ImageStatusService statusService;

    @Scheduled(cron = "0 0 1 * * ?")
    // @Scheduled(cron = "*/20 * * * * ?")
    public void dailyStat() {
        long count = service.countYesterday();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String str = format.format(cal.getTime());
        ImageStatus status = new ImageStatus();
        status.setDays(str);
        status.setTotal((int) count);
        statusService.save(status);
    }
}
