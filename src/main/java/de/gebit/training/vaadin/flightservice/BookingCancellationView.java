package de.gebit.training.vaadin.flightservice;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.gebit.training.vaadin.workshop.service.Reservation;
import de.gebit.training.vaadin.workshop.service.TravelService;
import de.gebit.training.vaadin.workshop.service.TravelServiceFactory;

public class BookingCancellationView extends CustomComponent implements View {
	
	TravelService travelService = TravelServiceFactory.getInstance();

	private Table table = new Table();
	
	public BookingCancellationView() {
		
		// TODO Configure table!
		
		setCompositionRoot(table);
	}

	public void enter(ViewChangeEvent event) {
		updateReservationTable();
	}

	private void updateReservationTable() {
		table.removeAllItems();
		for (final Reservation reservation : travelService.getReservations()) {

			String customerFullName = reservation.getCustomer().getFullName();
			String flightNumber = reservation.getFlight().getNumber();
			String departureCity = reservation.getFlight().getDepartureAirport().getCity();
			String destinationCity = reservation.getFlight().getDestinationAirport().getCity();

			// TODO Create cancel button!
			
			// TODO Fill table!

		}
	}
	
	private void onCancelButtonClick(Long reservationId) {
		travelService.cancelReservation(reservationId);
		updateReservationTable();
	}

}