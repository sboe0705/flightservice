package de.gebit.training.vaadin.flightservice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

public class StringToBigDecimalConverter implements Converter<String, BigDecimal> {

	private static final long serialVersionUID = 1L;

	@Override
	public BigDecimal convertToModel(String value, Class<? extends BigDecimal> targetType, Locale locale) throws ConversionException {
		if (value != null && !value.isEmpty()) {
			try {
				return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
			} catch (NumberFormatException e) {
				throw new ConversionException(e);
			}
		} else {
			return null;
		}
	}

	@Override
	public String convertToPresentation(BigDecimal value, Class<? extends String> targetType, Locale locale) throws ConversionException {
		if (value != null) {
			return value.toString();
		} else {
			return null;
		}
	}

	@Override
	public Class<BigDecimal> getModelType() {
		return BigDecimal.class;
	}

	@Override
	public Class<String> getPresentationType() {
		return String.class;
	}

}