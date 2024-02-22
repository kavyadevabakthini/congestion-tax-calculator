package congestion.calculator.src.main.congestionCalculator.model;

public enum TaxFreeVehicles {
    MOTORCYCLE("Motorcycles"),
    BUS("Busses"),
    EMERGENCY("Emergency vehicles"),
    DIPLOMAT("Diplomat vehicles"),
    FOREIGN("Foreign vehicles"),
    MILITARY("MMilitary vehicles");
    private final String type;

    TaxFreeVehicles(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}