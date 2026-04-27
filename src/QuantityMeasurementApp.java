public class QuantityMeasurementApp {

    // UC2: Feet and Inches measured separately
    double value;

    public QuantityMeasurementApp(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        QuantityMeasurementApp other = (QuantityMeasurementApp) obj;
        return Double.compare(this.value, other.value) == 0;
    }

    // Static method for Feet comparison
    public static boolean compareFeet(double feet1, double feet2) {
        QuantityMeasurementApp f1 = new QuantityMeasurementApp(feet1);
        QuantityMeasurementApp f2 = new QuantityMeasurementApp(feet2);
        return f1.equals(f2);
    }

    // Static method for Inches comparison
    public static boolean compareInches(double inch1, double inch2) {
        QuantityMeasurementApp i1 = new QuantityMeasurementApp(inch1);
        QuantityMeasurementApp i2 = new QuantityMeasurementApp(inch2);
        return i1.equals(i2);
    }

    public static void main(String[] args) {
        // Feet comparisons
        System.out.println("0 feet == 0 feet: " + compareFeet(0.0, 0.0));   // true
        System.out.println("1 feet == 1 feet: " + compareFeet(1.0, 1.0));   // true
        System.out.println("1 feet == 2 feet: " + compareFeet(1.0, 2.0));   // false

        // Inches comparisons
        System.out.println("0 inch == 0 inch: " + compareInches(0.0, 0.0)); // true
        System.out.println("1 inch == 1 inch: " + compareInches(1.0, 1.0)); // true
        System.out.println("1 inch == 2 inch: " + compareInches(1.0, 2.0)); // false
    }
}