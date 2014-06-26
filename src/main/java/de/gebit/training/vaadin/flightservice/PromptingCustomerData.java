package de.gebit.training.vaadin.flightservice;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.gebit.training.vaadin.workshop.service.Customer;
import de.gebit.training.vaadin.workshop.service.Reservation;
import de.gebit.training.vaadin.workshop.service.TravelServiceFactory;

public class PromptingCustomerData extends CustomComponent implements View {

	private static final long serialVersionUID = 1L;
	
	private BeanFieldGroup<Customer> customerFieldGroup = new BeanFieldGroup<>(Customer.class);
	private Reservation reservation;
	
	public PromptingCustomerData() {
		
		FormLayout formLayout = createCustomerDataForm();
		
		Button previousButton = createBackButton();
		
		Button nextButton = createBookFlightButton();
		
		VerticalLayout verticalLayout = new VerticalLayout(formLayout, new HorizontalLayout(previousButton, nextButton));
		verticalLayout.setMargin(true);
		verticalLayout.setSpacing(true);
		setCompositionRoot(verticalLayout);
	}

	private FormLayout createCustomerDataForm() {
		ComboBox titleBox = new ComboBox("Title");
		titleBox.addItem("Mr.");
		titleBox.addItem("Mrs.");
		titleBox.addItem("Dr.");
		
		TextField firstNameField = new TextField("First Name");
		firstNameField.setRequired(true);
		firstNameField.setNullRepresentation("");
		
		TextField lastNameField = new TextField("Last Name");
		lastNameField.setRequired(true);
		lastNameField.setNullRepresentation("");
		
		TextField phoneNumberField = new TextField("Phone Number");
		phoneNumberField.setRequired(true);
		phoneNumberField.setNullRepresentation("");
		
		customerFieldGroup.bind(titleBox, "title");
		customerFieldGroup.bind(firstNameField, "firstName");
		customerFieldGroup.bind(lastNameField, "lastName");
		customerFieldGroup.bind(phoneNumberField, "phoneNumber");
		
		FormLayout formLayout = new FormLayout(titleBox, firstNameField, lastNameField, phoneNumberField);
		return formLayout;
	}
	
	private Button createBackButton() {
		Button nextButton = new Button("Back");
		nextButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(NavigationState.FLIGHT_BOOKING);
			}
		});
		return nextButton;
	}
	
	private Button createBookFlightButton() {
		Button nextButton = new Button("Book Flight");
		nextButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (customerFieldGroup.isValid()) {
					
					try {
						customerFieldGroup.commit();
					} catch (CommitException e) {
						Notification.show(e.getLocalizedMessage(), Type.ERROR_MESSAGE);
						return;
					}
					
					Customer customer = customerFieldGroup.getItemDataSource().getBean();
					reservation.setCustomer(customer);
					TravelServiceFactory.getInstance().bookFlight(reservation);
					
					// TODO Remove reservation from session context!
					
					getUI().getNavigator().navigateTo(NavigationState.HOME);
					
				} else {
					Notification.show("Check the customer information!");
				}
			}
		});
		return nextButton;
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		
		// TODO Get reservation from session context!
		
		if (reservation == null) {
			getUI().getNavigator().navigateTo(NavigationState.FLIGHT_BOOKING);
			return;
		}
		
		customerFieldGroup.setItemDataSource(new Customer());
	}

}
