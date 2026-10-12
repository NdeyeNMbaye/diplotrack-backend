package sn.isi.diplotrack.msnotification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.isi.diplotrack.msnotification.entity.Notification;
import sn.isi.diplotrack.msnotification.entity.TypeNotification;
import sn.isi.diplotrack.msnotification.repository.NotificationRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;

    public Notification creer(Notification n) {
        n.setId(null);
        n.setLue(false);
        return repository.save(n);
    }

    public List<Notification> mesNotifications(String destinataireId, TypeNotification type) {
        if (type == null) {
            return repository.findByDestinataireIdOrderByDateCreationDesc(destinataireId);
        }
        return repository.findByDestinataireIdAndTypeOrderByDateCreationDesc(destinataireId, type);
    }

    public long compterNonLues(String destinataireId) {
        return repository.countByDestinataireIdAndLueFalse(destinataireId);
    }

    public Notification marquerLue(Long id, String destinataireId) {
        Notification n = repository.findById(id)
                .filter(x -> x.getDestinataireId().equals(destinataireId))
                .orElseThrow(() -> new NoSuchElementException("Notification introuvable"));
        n.setLue(true);
        return repository.save(n);
    }
}