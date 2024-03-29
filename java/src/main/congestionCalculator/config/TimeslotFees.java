package congestion.calculator.src.main.congestionCalculator.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor
@Configuration

public class TimeslotFees {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    @Getter
    LocalTime startTime;

    @Getter
    LocalTime endTime;

    @Getter
    int fees;

    public TimeslotFees(@Value("${startTime}") String startTime, @Value("${endTime}") String endTime, int fees) {
        this.startTime = LocalTime.parse(startTime, formatter);
        this.endTime = LocalTime.parse(endTime, formatter);
        this.fees = fees;
    }

}

