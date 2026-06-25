package ecommerce.catalog.lifecycle.notifications;

public interface MailSender {
	void enviarMail(String direcciónDestino, String título, String mensaje, String adjunto);
}
