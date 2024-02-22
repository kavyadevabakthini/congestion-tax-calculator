package congestion.calculator.src.main.congestionCalculator.config;

import congestion.calculator.src.main.congestionCalculator.toll.TaxCalculator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.MonthDay;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static congestion.calculator.src.main.congestionCalculator.constants.Constants.*;

@Configuration
public class ApplicationConfig {


    @Bean
    TaxCalculator tollCalculator(){
        TaxFeeConfiguration taxFeeConfiguration = new TaxFeeConfiguration();
        taxFeeConfiguration.setMaximumTollFeesPerDay(MAXFEE);
        List<MonthDay> holidayList = Stream.of(HOLIDAYS).map(e -> {
            String[] d = e.split("-");
            return MonthDay.of(Integer.parseInt(d[0]), Integer.parseInt(d[1]));
        }).collect(Collectors.toList());
        taxFeeConfiguration.setHolidays(holidayList);

        List<TimeslotFees> timeslotFeeList = Stream.of(TIMESLOTFEES).map(e -> {
            String[] t = e.split("-");
            return new TimeslotFees(t[0],t[1],Integer.parseInt(t[2]));
        }).collect(Collectors.toList());
        taxFeeConfiguration.setTimeslotFees(timeslotFeeList);
        return  new TaxCalculator(taxFeeConfiguration);
    }
}