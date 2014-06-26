package de.gebit.training.vaadin.flightservice;

import java.math.BigDecimal;

import com.vaadin.data.validator.RangeValidator;

public class BigDecimalRangeValidator extends RangeValidator<BigDecimal> {

	public BigDecimalRangeValidator(String errorMessage, BigDecimal minValue, BigDecimal maxValue) {
		super(errorMessage, BigDecimal.class, minValue, maxValue);
	}

}