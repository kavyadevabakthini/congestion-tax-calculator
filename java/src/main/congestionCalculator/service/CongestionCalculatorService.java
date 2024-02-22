package congestion.calculator.src.main.congestionCalculator.service;

import congestion.calculator.src.main.congestionCalculator.model.TaxFeeResponse;
import congestion.calculator.src.main.congestionCalculator.model.Vehicle;
import congestion.calculator.src.main.congestionCalculator.toll.TaxCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CongestionCalculatorService {

    @Autowired
    private TaxCalculator taxCalculator;

    public TaxFeeResponse getTollFees(Vehicle vehicle, List<Date> dateList){
        Date[] dates = dateList.toArray(new Date[dateList.size()]);
        int fees =  taxCalculator.getTollFee(vehicle, dates);
        return TaxFeeResponse.builder().totalFees(fees).build();
    }
}
