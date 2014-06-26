package de.gebit.training.vaadin.flightservice;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

public class FlightBookingView extends CustomComponent implements View {

	public FlightBookingView() {
		setCompositionRoot(new Label("Flight Booking"));
	}

	public void enter(ViewChangeEvent event) {
		
	}

}