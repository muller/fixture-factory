package br.com.six2six.fixturefactory;

import java.beans.Introspector;
import java.lang.reflect.Method;
import java.util.HashMap;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import br.com.six2six.fixturefactory.function.Function;

public class TypedRule {

    private static final HashMap<Object, Rule> MAP = new HashMap<Object, Rule>();

    @SuppressWarnings("unchecked")
    public static <T> T of(Class<T> beanClass) {
        final Rule rule = new Rule();
        MethodInterceptor invocationHandler = new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

                if (method.getName().startsWith("set")) {
                    Function function = OngoingSet.pop();
                    String property = Introspector.decapitalize(method.getName().substring(3));
                    rule.add(property, function);
                }

                return proxy.invokeSuper(obj, args);
            }
        };

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanClass);
        enhancer.setCallback(invocationHandler);

        Object object = enhancer.create();

        MAP.put(object, rule);

        return (T) object;
    }

    public static Rule get(Object object) {
        return MAP.get(object);
    }
}
