package sn.isi.diplotrack.msnotification.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sn.isi.diplotrack.msnotification.entity.Notification;
import sn.isi.diplotrack.msnotification.entity.TypeNotification;
import sn.isi.diplotrack.msnotification.service.NotificationService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @PostMapping
    public Notification creer(@RequestBody Notification n) {
        return service.creer(n);
    }

    @GetMapping("/mes-notifications")
    public List<Notification> mesNotifications(
            @RequestParam(required = false) TypeNotification type,
            @AuthenticationPrincipal Jwt jwt) {
        return service.mesNotifications(jwt.getSubject(), type);
    }

    @GetMapping("/non-lues/count")
    public Map<String, Long> compterNonLues(@AuthenticationPrincipal Jwt jwt) {
        return Map.of("nonLues", service.compterNonLues(jwt.getSubject()));
    }

    @PatchMapping("/{id}/lue")
    public Notification marquerLue(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        return service.marquerLue(id, jwt.getSubject());
    }
}