package br.com.six2six.fixturefactory;

import br.com.six2six.fixturefactory.function.Function;

public class OngoingSet {

    private static Function function;

    public static void set(Function function) {
        OngoingSet.function = function;
    }

    public static Function pop() {
        return function;
    }
}
