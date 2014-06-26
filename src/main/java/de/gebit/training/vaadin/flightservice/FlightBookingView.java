package de.gebit.training.vaadin.flightservice;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.gebit.training.vaadin.workshop.service.Flight;
import de.gebit.training.vaadin.workshop.service.Reservation;
import de.gebit.training.vaadin.workshop.service.TravelServiceFactory;

public class FlightBookingView extends CustomComponent implements View {

	private static final long serialVersionUID = 1L;

	private BeanItemContainer<Flight> flightContainer = new BeanItemContainer<>(Flight.class);

	private BeanFieldGroup<Reservation> reservationFieldGroup = new BeanFieldGroup<>(Reservation.class);

	private BeanFieldGroup<Flight> flightFieldGroup = new BeanFieldGroup<>(Flight.class);

	private FormLayout flightInformationPanel;

	public FlightBookingView() {
		
		flightFieldGroup.setReadOnly(true);
		
		ListSelect flightSelect = createFlightSelect();

		flightInformationPanel = createFlightInformationPanel();
		
		Button nextButton = createNextButton();

		Button cancelButton = createCancelButton();

		VerticalLayout verticalLayout = new VerticalLayout(flightSelect, flightInformationPanel, new HorizontalLayout(cancelButton, nextButton));
		verticalLayout.setMargin(true);
		verticalLayout.setSpacing(true);
		setCompositionRoot(verticalLayout);
	}

	private ListSelect createFlightSelect() {
		ListSelect flightSelect = new ListSelect();
		flightSelect.setCaption("Select a flight");
		flightSelect.setContainerDataSource(flightContainer);
		flightSelect.setNullSelectionAllowed(false);
		flightSelect.setRequired(true);
		flightSelect.setItemCaptionPropertyId("number");

		reservationFieldGroup.bind(flightSelect, "flight");
		
		flightSelect.setImmediate(true);
		flightSelect.addValueChangeListener(new ValueChangeListener() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				updateFlightInformationPanel((Flight) event.getProperty().getValue());
			}
			
		});

		return flightSelect;
	}

	private FormLayout createFlightInformationPanel() {
		TextField departureField = new TextField("Departure");
		departureField.setReadOnly(true);
		
		TextField destinationField = new TextField("Destination");
		destinationField.setReadOnly(true);
		
		TextField dateField	= new TextField("Date");
		dateField.setReadOnly(true);
		dateField.setNullRepresentation("");
		
		flightFieldGroup.bind(departureField, "departureAirportCity");
		flightFieldGroup.bind(destinationField, "destinationAirportCity");
		flightFieldGroup.bind(dateField, "date");
		
		FormLayout formLayout = new FormLayout(departureField, destinationField, dateField);
		formLayout.setCaption("Flight Information");
		return formLayout;
	}
	
	private void updateFlightInformationPanel(Flight flight) {
		if (flight == null) {
			flightFieldGroup.setItemDataSource(new Flight());
		} else {
			flightFieldGroup.setItemDataSource(flight);
		}
	}

	private Button createCancelButton() {
		Button nextButton = new Button("Cancel");
		nextButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				FlightServiceUI flightServiceUI = (FlightServiceUI) getUI();
				flightServiceUI.setReservation(null);
				Notification.show("Booking cancelled!");
				getUI().getNavigator().navigateTo(NavigationState.HOME);
			}
			
		});
		return nextButton;
	}

	private Button createNextButton() {
		Button nextButton = new Button("Next");
		nextButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (reservationFieldGroup.isValid()) {

					try {
						reservationFieldGroup.commit();
					} catch (CommitException e) {
						Notification.show(e.getLocalizedMessage(), Type.ERROR_MESSAGE);
						return;
					}

					getUI().getNavigator().navigateTo(NavigationState.PROMPTING_CUSTOMER_DATA);

				} else {
					Notification.show("Please select a fligt!", Type.HUMANIZED_MESSAGE);
				}

			}
			
		});
		return nextButton;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		flightContainer.removeAllItems();
		flightContainer.addAll(TravelServiceFactory.getInstance().getFlights());

		Reservation reservation;
		
		FlightServiceUI flightServiceUI = (FlightServiceUI) getUI();
		reservation = flightServiceUI.getReservation();
		if (reservation == null) {
			reservation = new Reservation();
			flightServiceUI.setReservation(reservation);
		}
		
		reservationFieldGroup.setItemDataSource(reservation);
	}

}