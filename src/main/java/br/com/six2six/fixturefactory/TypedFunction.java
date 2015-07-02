package br.com.six2six.fixturefactory;

import java.math.BigDecimal;
import java.util.Calendar;

import br.com.six2six.fixturefactory.function.Function;

public interface TypedFunction extends Function {

    String asString();

    Calendar asCalendar();

    BigDecimal asBigDecimal();

    <T> T as(Class<T> type);
}
