package br.com.six2six.fixturefactory;

import static br.com.six2six.fixturefactory.function.impl.DateTimeFunction.DateType.BEFORE;
import static br.com.six2six.fixturefactory.util.DateTimeUtils.toCalendar;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import br.com.six2six.fixturefactory.function.Function;
import br.com.six2six.fixturefactory.function.impl.DateTimeFunction;
import br.com.six2six.fixturefactory.function.impl.RandomFunction;
import br.com.six2six.fixturefactory.function.impl.RegexFunction;

public class TypedRules {

    public static TypedFunction regex(String regex) {
        return wrap(new RegexFunction(regex));
    }

    public static TypedFunction random(Class<? extends BigDecimal> clazz, MathContext mc) {
        return wrap(new RandomFunction(clazz, mc));
    }

    public static TypedFunction beforeDate(String source, SimpleDateFormat format) {
        return wrap(new DateTimeFunction(toCalendar(source, format), BEFORE));
    }

    static TypedFunction wrap(final Function delegate) {
        Enhancer enhancer = new Enhancer();
        enhancer.setInterfaces(new Class[] { TypedFunction.class });
        enhancer.setCallback(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                OngoingSet.set(delegate);
                return null;
            }
        });
        return (TypedFunction) enhancer.create();
    }
}
