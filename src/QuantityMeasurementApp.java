public class QuantityMeasurementApp {

    double feet;

    // Constructor
    public QuantityMeasurementApp(double feet) {
        this.feet = feet;
    }

    // Compare equality of two feet values
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        QuantityMeasurementApp other = (QuantityMeasurementApp) obj;
        return Double.compare(this.feet, other.feet) == 0;
    }

    public static void main(String[] args) {
        QuantityMeasurementApp feet1 = new QuantityMeasurementApp(3.0);
        QuantityMeasurementApp feet2 = new QuantityMeasurementApp(3.0);
        QuantityMeasurementApp feet3 = new QuantityMeasurementApp(5.0);

        System.out.println("3 feet == 3 feet: " + feet1.equals(feet2)); // true
        System.out.println("3 feet == 5 feet: " + feet1.equals(feet3)); // false
        System.out.println("null check: " + feet1.equals(null));        // false
    }
}