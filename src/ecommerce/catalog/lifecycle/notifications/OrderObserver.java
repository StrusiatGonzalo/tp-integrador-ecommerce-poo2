package ecommerce.catalog.lifecycle.notifications;

import ecommerce.catalog.lifecycle.Order;
import ecommerce.catalog.lifecycle.State;

// todo subsistema que quiera enterarse de los cambios de estado, debe implementar esta interfaz
public interface OrderObserver { 
	
	public default void notifySuccessfulProgress(Order order) {}
	public default void notifyFinal(Order order) {}
	public default void notifyCanceled(Order order) {}
}
