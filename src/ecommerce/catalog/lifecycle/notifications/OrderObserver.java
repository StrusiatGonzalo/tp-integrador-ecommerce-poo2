package ecommerce.catalog.lifecycle.notifications;

import ecommerce.catalog.lifecycle.Order;
import ecommerce.catalog.lifecycle.State;

// todo subsistema que quiera enterarse de los cambios de estado, debe implementar esta interfaz
public interface OrderObserver { 
	// esta se llama automaticamente cada vez que el pedido cambie el estado
	// el observador decide por su cuenta si le interesa o no este cambio de estado
	void onStateChanged(Order order, State prev, State next);
}
