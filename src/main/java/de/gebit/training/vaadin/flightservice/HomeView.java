package de.gebit.training.vaadin.flightservice;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

public class HomeView extends CustomComponent implements View {

	private static final long serialVersionUID = 1L;

	public HomeView() {
		setCompositionRoot(new Label("Welcome"));
	}

	public void enter(ViewChangeEvent event) {
		
	}
	
}