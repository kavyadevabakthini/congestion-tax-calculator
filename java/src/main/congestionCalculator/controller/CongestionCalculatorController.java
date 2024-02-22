package congestion.calculator.src.main.congestionCalculator.controller;

import congestion.calculator.src.main.congestionCalculator.model.*;
import congestion.calculator.src.main.congestionCalculator.service.CongestionCalculatorService;
import congestion.calculator.src.main.congestionCalculator.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping
public class CongestionCalculatorController {

    @Autowired
    private CongestionCalculatorService congestionCalculatorService;

    @PostMapping("/calculateTax")
    public ResponseEntity<TaxFeeResponse> calculateTax(@RequestBody TaxFeeRequest taxFeeRequest){

        String vehicleStr = taxFeeRequest.getVehicle();
        Vehicle vehicle = null;
        if (vehicleStr.equals("Car")) {
            vehicle = new Car();
        } else {
            vehicle = new OtherVehicle(vehicleStr);
        }
        List<Date> dateList = taxFeeRequest.getDates().stream().map(DateUtil::toDate).collect(Collectors.toList());
        return ResponseEntity.ok(congestionCalculatorService.getTollFees(vehicle, dateList));
    }
}