package congestion.calculator.src.main.congestionCalculator.config;

import lombok.Data;

import java.time.MonthDay;
import java.util.List;

@Data
public class TaxFeeConfiguration {
    private List<TimeslotFees> timeslotFees;
    private List<MonthDay> holidays;
    private int maximumTollFeesPerDay;
}
