package de.gebit.training.vaadin.flightservice;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Component;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.gebit.training.vaadin.workshop.service.Reservation;

public class FlightServiceUI extends UI {

	private Reservation reservation;

	@Override
	protected void init(VaadinRequest request) {

		Panel panel = new Panel();
		panel.setSizeFull();

		Navigator navigator = new Navigator(this, panel);
		navigator.addView(NavigationState.HOME, HomeView.class);
		navigator.addView(NavigationState.AIRPORT_LIST, AirportListView.class);
		navigator.addView(NavigationState.FLIGHT_MAINTENANCE, FlightMaintenanceView.class);
		navigator.addView(NavigationState.FLIGHT_BOOKING, FlightBookingView.class);
		navigator.addView(NavigationState.BOOKING_CANCELLATION, BookingCancellationView.class);
		navigator.addView(NavigationState.PROMPTING_CUSTOMER_DATA, PromptingCustomerData.class);
		navigator.navigateTo(NavigationState.HOME);

		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSizeFull();
		verticalLayout.addComponent(createMenu());
		verticalLayout.addComponent(panel);
		verticalLayout.setExpandRatio(panel, 1);
		setContent(verticalLayout);
	}

	private Component createMenu() {
		MenuBar menuBar = new MenuBar();

		MenuItem administration = menuBar.addItem("Maintenance", null);

		administration.addItem("Airports", new Command() {

			public void menuSelected(MenuItem selectedItem) {
				getNavigator().navigateTo(NavigationState.AIRPORT_LIST);
			}
		});

		administration.addItem("Flights", new Command() {

			public void menuSelected(MenuItem selectedItem) {
				getNavigator().navigateTo(NavigationState.FLIGHT_MAINTENANCE);
			}
		});

		MenuItem booking = menuBar.addItem("Booking", null);

		booking.addItem("Book Flight", new Command() {

			public void menuSelected(MenuItem selectedItem) {
				getNavigator().navigateTo(NavigationState.FLIGHT_BOOKING);
			}
		});

		booking.addItem("Cancel Reservation", new Command() {

			public void menuSelected(MenuItem selectedItem) {
				getNavigator().navigateTo(NavigationState.BOOKING_CANCELLATION);
			}
		});

		return menuBar;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

}