package de.gebit.training.vaadin.flightservice;

import java.math.BigDecimal;
import java.util.Date;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.data.validator.DoubleRangeValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;

import de.gebit.training.vaadin.workshop.service.Airport;
import de.gebit.training.vaadin.workshop.service.Flight;
import de.gebit.training.vaadin.workshop.service.TravelService;
import de.gebit.training.vaadin.workshop.service.TravelServiceException;
import de.gebit.training.vaadin.workshop.service.TravelServiceFactory;

public class FlightMaintenanceView extends CustomComponent implements View {

	private final TravelService travelService = TravelServiceFactory.getInstance();

	private final BeanItemContainer<Flight> flightContainer = new BeanItemContainer<>(Flight.class);

	private final BeanItemContainer<Airport> airportContainer = new BeanItemContainer<>(Airport.class);

	private Table table;

	private final BeanFieldGroup<Flight> flightFieldGroup = new BeanFieldGroup<Flight>(Flight.class);

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
		TextField numberTextField = new TextField("Number");
		numberTextField.setRequired(true);
		numberTextField.setRequiredError("Please enter a flight number!");
		numberTextField.setNullRepresentation("");
		numberTextField.addValidator(new RegexpValidator("\\w\\w\\d\\d\\d", "Please enter a valid flight number!"));

		TextField airlineTextField = new TextField("Airline");
		airlineTextField.setRequired(true);
		airlineTextField.setRequiredError("Please enter an airline!");
		airlineTextField.setNullRepresentation("");

		ComboBox departureAirportField = new ComboBox("Departure Airport");
		departureAirportField.setTextInputAllowed(false);
		departureAirportField.setRequired(true);
		departureAirportField.setRequiredError("Please select a depature airport!");
		departureAirportField.setItemCaptionPropertyId("name");
		departureAirportField.setContainerDataSource(airportContainer);

		ComboBox destinationAirportField = new ComboBox("Destination Airport");
		destinationAirportField.setTextInputAllowed(false);
		destinationAirportField.setRequired(true);
		destinationAirportField.setRequiredError("Please select a destination airport!");
		destinationAirportField.setItemCaptionPropertyId("name");
		destinationAirportField.setContainerDataSource(airportContainer);

		TextField priceField = new TextField("Price");
		priceField.setRequired(true);
		priceField.setRequiredError("Please enter a price!");
		priceField.setNullRepresentation("");
		priceField.setConverter(new StringToBigDecimalConverter());
		priceField.addValidator(new BigDecimalRangeValidator("Please enter a valid price!", new BigDecimal("0.01"), null));

		DateField departureField = new DateField("Departure Date");
		departureField.setDateFormat("dd.MM.yyyy");
		departureField.setRequired(true);
		departureField.setRequiredError("Please enter a departure date!");
		departureField.addValidator(new DateRangeValidator("Please enter departure date in the future!", new Date(), null, Resolution.DAY));

		flightFieldGroup.bind(numberTextField, "number");
		flightFieldGroup.bind(airlineTextField, "airline");
		flightFieldGroup.bind(departureAirportField, "departureAirport");
		flightFieldGroup.bind(destinationAirportField, "destinationAirport");
		flightFieldGroup.bind(priceField, "price");
		flightFieldGroup.bind(departureField, "date");

		return new FormLayout(numberTextField, airlineTextField, departureAirportField, destinationAirportField, priceField, departureField);
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

		Button resetButton = new Button("Reset");
		resetButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				onResetButtonClick();
			}
		});

		HorizontalLayout horizontalLayout = new HorizontalLayout(newButton, saveButton, deleteButton, resetButton);
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

		flightFieldGroup.setItemDataSource(new Flight());
	}

	private void onFlighSelect(Flight flight) {
		if (flight != null) {
			flightFieldGroup.setItemDataSource(flight);
		} else {
			flightFieldGroup.setItemDataSource(new Flight());
		}
	}

	private void onDeleteButtonClick() {
		Flight flight = flightFieldGroup.getItemDataSource().getBean();
		if (flight.getNumber() != null) {
			try {
				travelService.deleteFlight(flight.getNumber());
			} catch (TravelServiceException e) {
				Notification.show(e.getMessage(), Type.ERROR_MESSAGE);
			}
		}
		refreshFlightTable();
	}

	private void onCreateButtonClick() {
		flightFieldGroup.setItemDataSource(new Flight());
	}

	private void onSaveButtonClick() {
		if (flightFieldGroup.isValid()) {
			try {
				flightFieldGroup.commit();
			} catch (CommitException e) {
				Notification.show(e.getMessage(), Type.ERROR_MESSAGE);
			}
			Flight flight = flightFieldGroup.getItemDataSource().getBean();
			travelService.saveFlight(flight);
			flightFieldGroup.setItemDataSource(new Flight());
			refreshFlightTable();
		}
	}

	private void onResetButtonClick() {
		flightFieldGroup.discard();
	}

}