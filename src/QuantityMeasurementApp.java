public class QuantityMeasurementApp {

    // UC1: Feet Measurement Equality
    double value;

    public QuantityMeasurementApp(double value) {
        this.value = value;
    }

    public boolean equals(QuantityMeasurementApp other) {
        return this.value == other.value;
    }

    public static void main(String[] args) {
        QuantityMeasurementApp feet1 = new QuantityMeasurementApp(3.0);
        QuantityMeasurementApp feet2 = new QuantityMeasurementApp(3.0);
        QuantityMeasurementApp feet3 = new QuantityMeasurementApp(5.0);

        System.out.println("3 feet == 3 feet: " + feet1.equals(feet2)); // true
        System.out.println("3 feet == 5 feet: " + feet1.equals(feet3)); // false
    }
}