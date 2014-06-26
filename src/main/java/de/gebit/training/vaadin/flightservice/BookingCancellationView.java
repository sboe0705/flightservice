package de.gebit.training.vaadin.flightservice;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

public class BookingCancellationView extends CustomComponent implements View {

	public BookingCancellationView() {
		setCompositionRoot(new Label("Booking Cancellation"));
	}

	public void enter(ViewChangeEvent event) {
		
	}


}