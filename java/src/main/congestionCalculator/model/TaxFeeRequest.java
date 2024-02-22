package congestion.calculator.src.main.congestionCalculator.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaxFeeRequest {
    private String vehicle;
    private List<LocalDateTime> dates;

}
