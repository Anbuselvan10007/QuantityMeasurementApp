// UC8: LengthUnit as standalone enum
enum LengthUnit {
    FEET(12.0),
    INCH(1.0),
    YARD(36.0),
    CENTIMETER(0.393701);

    private final double conversionFactor; // base = inches

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double convertToBaseUnit(double value) {
        return value * this.conversionFactor;
    }

    public double convertFromBaseUnit(double baseValue) {
        return Math.round((baseValue / this.conversionFactor) * 100.0) / 100.0;
    }
}

// UC9: WeightUnit as standalone enum (mirrors LengthUnit pattern)
enum WeightUnit {
    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double conversionFactor; // base = kilograms

    WeightUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double convertToBaseUnit(double value) {
        return value * this.conversionFactor;
    }

    public double convertFromBaseUnit(double baseValue) {
        return Math.round((baseValue / this.conversionFactor) * 100.0) / 100.0;
    }
}

// UC9: QuantityWeight class - mirrors QuantityMeasurementApp pattern
class QuantityWeight {

    double value;
    WeightUnit unit;

    public QuantityWeight(double value, WeightUnit unit) {
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Value must be a finite number");
        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");
        this.value = value;
        this.unit = unit;
    }

    public double convertToBaseUnit() {
        return this.unit.convertToBaseUnit(this.value);
    }

    // Convert to target unit
    public QuantityWeight convertTo(WeightUnit targetUnit) {
        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");
        double inBase = this.convertToBaseUnit();
        double resultValue = targetUnit.convertFromBaseUnit(inBase);
        return new QuantityWeight(resultValue, targetUnit);
    }

    // Add two weights - result in unit of first operand
    public static QuantityWeight add(QuantityWeight w1, QuantityWeight w2) {
        if (w1 == null || w2 == null)
            throw new IllegalArgumentException("Weights cannot be null");
        double totalBase = w1.convertToBaseUnit() + w2.convertToBaseUnit();
        double resultValue = w1.unit.convertFromBaseUnit(totalBase);
        return new QuantityWeight(resultValue, w1.unit);
    }

    // Add two weights - result in explicit target unit
    public static QuantityWeight add(QuantityWeight w1, QuantityWeight w2,
                                     WeightUnit targetUnit) {
        if (w1 == null || w2 == null)
            throw new IllegalArgumentException("Weights cannot be null");
        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");
        double totalBase = w1.convertToBaseUnit() + w2.convertToBaseUnit();
        double resultValue = targetUnit.convertFromBaseUnit(totalBase);
        return new QuantityWeight(resultValue, targetUnit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        QuantityWeight other = (QuantityWeight) obj;
        return Double.compare(this.convertToBaseUnit(), other.convertToBaseUnit()) == 0;
    }
}

// Main class - Length (UC1-UC8) + Weight (UC9)
public class QuantityMeasurementApp {

    double value;
    LengthUnit unit;

    public QuantityMeasurementApp(double value, LengthUnit unit) {
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Value must be a finite number");
        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");
        this.value = value;
        this.unit = unit;
    }

    public double convertToBaseUnit() {
        return this.unit.convertToBaseUnit(this.value);
    }

    public static double convert(double value, LengthUnit sourceUnit, LengthUnit targetUnit) {
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Value must be a finite number");
        if (sourceUnit == null || targetUnit == null)
            throw new IllegalArgumentException("Units cannot be null");
        double inBase = sourceUnit.convertToBaseUnit(value);
        return targetUnit.convertFromBaseUnit(inBase);
    }

    public static QuantityMeasurementApp add(QuantityMeasurementApp l1,
                                             QuantityMeasurementApp l2) {
        if (l1 == null || l2 == null)
            throw new IllegalArgumentException("Lengths cannot be null");
        double totalBase = l1.convertToBaseUnit() + l2.convertToBaseUnit();
        double resultValue = l1.unit.convertFromBaseUnit(totalBase);
        return new QuantityMeasurementApp(resultValue, l1.unit);
    }

    public static QuantityMeasurementApp add(QuantityMeasurementApp l1,
                                             QuantityMeasurementApp l2,
                                             LengthUnit targetUnit) {
        if (l1 == null || l2 == null)
            throw new IllegalArgumentException("Lengths cannot be null");
        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");
        double totalBase = l1.convertToBaseUnit() + l2.convertToBaseUnit();
        double resultValue = targetUnit.convertFromBaseUnit(totalBase);
        return new QuantityMeasurementApp(resultValue, targetUnit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        QuantityMeasurementApp other = (QuantityMeasurementApp) obj;
        return Double.compare(this.convertToBaseUnit(), other.convertToBaseUnit()) == 0;
    }

    public static void main(String[] args) {

        System.out.println("===== LENGTH (UC1-UC8) =====");
        QuantityMeasurementApp f1 = new QuantityMeasurementApp(1.0, LengthUnit.FEET);
        QuantityMeasurementApp i12 = new QuantityMeasurementApp(12.0, LengthUnit.INCH);
        QuantityMeasurementApp y1 = new QuantityMeasurementApp(1.0, LengthUnit.YARD);
        QuantityMeasurementApp f3 = new QuantityMeasurementApp(3.0, LengthUnit.FEET);

        System.out.println("1 feet == 12 inch:          " + f1.equals(i12));       // true
        System.out.println("1 yard == 3 feet:           " + y1.equals(f3));        // true
        System.out.println("1 feet -> inches:           " + convert(1.0, LengthUnit.FEET, LengthUnit.INCH)); // 12.0

        QuantityMeasurementApp addResult = add(f1, i12);
        System.out.println("1 feet + 12 inch =          " + addResult.value + " " + addResult.unit); // 2.0 FEET

        QuantityMeasurementApp addResult2 = add(f1, i12, LengthUnit.YARD);
        System.out.println("1 feet + 12 inch in YARDS = " + addResult2.value + " " + addResult2.unit); // 0.67 YARD

        System.out.println("\n===== WEIGHT (UC9) =====");
        QuantityWeight kg1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight g1000 = new QuantityWeight(1000.0, WeightUnit.GRAM);
        QuantityWeight lb1 = new QuantityWeight(1.0, WeightUnit.POUND);

        // Equality
        System.out.println("1 kg == 1000 g:             " + kg1.equals(g1000));    // true
        System.out.println("1 kg == 1 lb:               " + kg1.equals(lb1));      // false

        // Conversion
        QuantityWeight kgToG = kg1.convertTo(WeightUnit.GRAM);
        System.out.println("1 kg -> grams:              " + kgToG.value + " " + kgToG.unit); // 1000.0 GRAM

        QuantityWeight lbToKg = lb1.convertTo(WeightUnit.KILOGRAM);
        System.out.println("1 lb -> kg:                 " + lbToKg.value + " " + lbToKg.unit); // 0.45 KILOGRAM

        // Addition
        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(500.0, WeightUnit.GRAM);
        QuantityWeight wResult = QuantityWeight.add(w1, w2);
        System.out.println("1 kg + 500 g =              " + wResult.value + " " + wResult.unit); // 1.5 KILOGRAM

        QuantityWeight wResult2 = QuantityWeight.add(w1, w2, WeightUnit.GRAM);
        System.out.println("1 kg + 500 g in GRAMS =     " + wResult2.value + " " + wResult2.unit); // 1500.0 GRAM
    }
}