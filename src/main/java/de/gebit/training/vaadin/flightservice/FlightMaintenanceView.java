package de.gebit.training.vaadin.flightservice;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.gebit.training.vaadin.workshop.service.Airport;
import de.gebit.training.vaadin.workshop.service.Flight;
import de.gebit.training.vaadin.workshop.service.TravelService;
import de.gebit.training.vaadin.workshop.service.TravelServiceFactory;

public class FlightMaintenanceView extends CustomComponent implements View {

	private final TravelService travelService = TravelServiceFactory.getInstance();
	
	private final BeanItemContainer<Flight> flightContainer = new BeanItemContainer<>(Flight.class);

	private final BeanItemContainer<Airport> airportContainer = new BeanItemContainer<>(Airport.class);

	private Table table;

	public FlightMaintenanceView() {
		table = createTable();
		FormLayout form = createForm();
		HorizontalLayout buttonBar = createButtonBar();

		VerticalLayout verticalLayout = new VerticalLayout(table, form, buttonBar);
		verticalLayout.setMargin(true);
		verticalLayout.setSpacing(true);
		setCompositionRoot(verticalLayout);
	}

	private Table createTable() {
		Table table = new Table();
		table.setContainerDataSource(flightContainer);
		table.setSizeFull();
		table.setSelectable(true);
		table.setImmediate(true);
		table.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				Flight flight = (Flight) event.getProperty().getValue();
				onFlighSelect(flight);
			}

		});
		table.setVisibleColumns(new Object[] { "number", "airline", "departureAirportCity", "destinationAirportCity", "price", "date" });
		return table;
	}

	private FormLayout createForm() {
		// TODO Create flight form!
		return new FormLayout();
	}

	private HorizontalLayout createButtonBar() {
		Button newButton = new Button("New");
		newButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				onCreateButtonClick();
			}

		});

		Button saveButton = new Button("Save");
		saveButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				onSaveButtonClick();
			}

		});

		Button deleteButton = new Button("Delete");
		deleteButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				onDeleteButtonClick();
			}

		});

		Button cancelButton = new Button("Cancel");
		cancelButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				onCancelButtonClick();
			}
		});

		HorizontalLayout horizontalLayout = new HorizontalLayout(newButton, saveButton, deleteButton, cancelButton);
		horizontalLayout.setSpacing(true);
		return horizontalLayout;
	}

	public void enter(ViewChangeEvent event) {
		airportContainer.removeAllItems();
		airportContainer.addAll(travelService.getAirports());
		refreshFlightTable();
	}
	
	private void refreshFlightTable() {
		flightContainer.removeAllItems();
		flightContainer.addAll(travelService.getFlights());
		table.setValue(null);
	}

	private void onFlighSelect(Flight flight) {
		// TODO Load selected flight for editing!
		if (flight != null) {
			Notification.show("Selected flight: " + flight.getNumber());
		} else {
			Notification.show("Selected flight: " + null);
		}
	}

	private void onDeleteButtonClick() {
		// TODO Delete selected flight!
	}

	private void onCreateButtonClick() {
		// TODO Create new flight!
	}

	private void onSaveButtonClick() {
		// TODO Save current flight!
	}

	private void onCancelButtonClick() {
		// TODO Discard changed on current flight!
	}

}