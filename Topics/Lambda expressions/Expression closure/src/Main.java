import java.util.function.DoubleUnaryOperator;

class Operator {

    final public static int a = 10;
    final public static int b = 20;
    final public static int c = 30;

    public static DoubleUnaryOperator unaryOperator = x -> {double temp = x * x; return a * temp + b * x + c; };
}