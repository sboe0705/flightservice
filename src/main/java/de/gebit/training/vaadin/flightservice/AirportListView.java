package de.gebit.training.vaadin.flightservice;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;

import de.gebit.training.vaadin.workshop.service.Airport;
import de.gebit.training.vaadin.workshop.service.TravelService;
import de.gebit.training.vaadin.workshop.service.TravelServiceFactory;

public class AirportListView extends CustomComponent implements View {

	private static final long serialVersionUID = 1L;

	private final BeanItemContainer<Airport> container = new BeanItemContainer<>(Airport.class);
	
	public AirportListView() {
		Table table = new Table();
		table.setContainerDataSource(container);
		table.setVisibleColumns("id", "name", "city");
		table.setSizeFull();
		setCompositionRoot(table);
	}

	public void enter(ViewChangeEvent event) {
		TravelService travelService = TravelServiceFactory.getInstance();
		container.removeAllItems();
		container.addAll(travelService.getAirports());
	}

}