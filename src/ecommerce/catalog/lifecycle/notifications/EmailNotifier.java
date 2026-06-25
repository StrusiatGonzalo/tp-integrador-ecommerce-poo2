package ecommerce.catalog.lifecycle.notifications;

import ecommerce.catalog.lifecycle.Confirmed;
import ecommerce.catalog.lifecycle.Delivered;
import ecommerce.catalog.lifecycle.Order;
import ecommerce.catalog.lifecycle.Sent;
import ecommerce.catalog.lifecycle.State;

public class EmailNotifier implements OrderObserver{ // Un notificar via mail
	private final MailSender mailSender; // interface de libreria que envia los mails.
	
	public EmailNotifier(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	@Override
	public void onStateChanged(Order order, State prev, State next) {
		if (!applies(next)) {
			return ;
		}
		mailSender.enviarMail(order.getEmail(), "Tu pedido cambio de estado", "Ahora esta: " + next.getName(), null);		
	}
	
	private boolean applies(State next) {
		return next instanceof Confirmed
				|| next instanceof Sent
				|| next instanceof Delivered;
	}

}
