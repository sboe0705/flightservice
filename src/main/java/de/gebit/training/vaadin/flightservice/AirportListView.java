package de.gebit.training.vaadin.flightservice;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

public class AirportListView extends CustomComponent implements View {

	private static final long serialVersionUID = 1L;

	public AirportListView() {
		setCompositionRoot(new Label("AirportList"));
	}

	public void enter(ViewChangeEvent event) {

	}

}