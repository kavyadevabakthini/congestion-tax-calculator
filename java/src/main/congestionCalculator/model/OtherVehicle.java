package congestion.calculator.src.main.congestionCalculator.model;

public class OtherVehicle implements Vehicle{

    private final String type;

    public OtherVehicle(String type){
        this.type = type;
    }
    @Override
    public String getType() {
        return this.type;
    }


}
