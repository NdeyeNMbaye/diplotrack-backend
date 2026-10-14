package sn.isi.diplotrack.diplome.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import sn.isi.diplotrack.diplome.entity.DemandeVerification;

import java.time.format.DateTimeFormatter;

/**
 * Envoie par email la réponse à une demande de vérification de diplôme.
 *
 * Le dépôt d'une demande se fait sur la plateforme : aucun email n'est envoyé à ce moment.
 * Seul le traitement par l'agent de scolarité (F08) déclenche la réponse, adressée au
 * demandeur, qu'il s'agisse d'une entreprise partenaire ou non.
 */
@Slf4j
@Service
public class EmailService {

    private static final DateTimeFormatter FORMAT_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy 'à' HH:mm");

    private final JavaMailSender mailSender;
    private final String expediteur;

    public EmailService(JavaMailSender mailSender, @Value("${app.mail.from}") String expediteur) {
        this.mailSender = mailSender;
        this.expediteur = expediteur;
    }

    /**
     * Envoie au demandeur le résultat de sa demande, une fois celle-ci traitée.
     *
     * @param demande la demande traitée (résultat et date de traitement renseignés)
     */
    public void envoyerReponse(DemandeVerification demande) {
        String date = demande.getDateTraitement() != null
                ? demande.getDateTraitement().format(FORMAT_DATE)
                : "";

        String corps = "Bonjour,\n\n"
                + "Votre demande de vérification (référence n° " + demande.getId() + ") "
                + "a été traitée le " + date + ".\n\n"
                + "Numéro de diplôme : " + demande.getNumeroDiplome() + "\n"
                + "Résultat : " + demande.getResultat() + "\n\n"
                + "Cordialement,\n"
                + "ISI - DiploTrack";

        envoyer(demande.getContactDemandeur(),
                "DiploTrack - Réponse à votre demande n° " + demande.getId(),
                corps);
    }

    /**
     * Envoie un message texte simple. Un échec d'envoi est journalisé mais ne remonte pas :
     * la demande reste traitée même si le serveur de messagerie est indisponible.
     */
    private void envoyer(String destinataire, String sujet, String corps) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(expediteur);
            message.setTo(destinataire);
            message.setSubject(sujet);
            message.setText(corps);
            mailSender.send(message);
            log.info("Email envoyé à {} : {}", destinataire, sujet);
        } catch (Exception e) {
            log.error("Échec d'envoi de l'email à {} : {}", destinataire, e.getMessage());
        }
    }
}