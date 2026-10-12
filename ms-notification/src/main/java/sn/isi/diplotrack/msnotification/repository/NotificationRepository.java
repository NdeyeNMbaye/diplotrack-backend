package sn.isi.diplotrack.msnotification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.isi.diplotrack.msnotification.entity.Notification;
import sn.isi.diplotrack.msnotification.entity.TypeNotification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByDestinataireIdOrderByDateCreationDesc(String destinataireId);

    List<Notification> findByDestinataireIdAndTypeOrderByDateCreationDesc(
            String destinataireId, TypeNotification type);

    long countByDestinataireIdAndLueFalse(String destinataireId);
}