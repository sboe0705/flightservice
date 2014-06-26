package de.gebit.training.vaadin.flightservice;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

public class FlightMaintenanceView extends CustomComponent implements View {

	public FlightMaintenanceView() {
		setCompositionRoot(new Label("Flight Maintenance"));
	}

	public void enter(ViewChangeEvent event) {

	}

}