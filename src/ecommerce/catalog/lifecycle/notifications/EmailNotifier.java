package ecommerce.catalog.lifecycle.notifications;

import ecommerce.catalog.lifecycle.Confirmed;
import ecommerce.catalog.lifecycle.Delivered;
import ecommerce.catalog.lifecycle.Order;
import ecommerce.catalog.lifecycle.Sent;
import ecommerce.catalog.lifecycle.State;

public class EmailNotifier implements OrderObserver{
	private final MailSender mailSender;
	
	public EmailNotifier(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	@Override
	public void onStateChanged(Order order, State prev, State next) {
		if (!applies(next)) {
			return ;
		}
		mailSender.enviarMail(order.getEmail(), "Tu pedidio cambio de estado", "Ahora esta: " + next, null);		
		
	}
	
	public boolean applies(State next) {
		return next instanceof Confirmed
				|| next instanceof Sent
				|| next instanceof Delivered;
	}

}
