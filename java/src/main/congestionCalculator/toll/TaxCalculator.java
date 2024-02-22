package congestion.calculator.src.main.congestionCalculator.toll;

import congestion.calculator.src.main.congestionCalculator.config.TimeslotFees;
import congestion.calculator.src.main.congestionCalculator.config.TaxFeeConfiguration;
import congestion.calculator.src.main.congestionCalculator.model.TaxFreeVehicles;
import congestion.calculator.src.main.congestionCalculator.model.Vehicle;
import congestion.calculator.src.main.congestionCalculator.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class TaxCalculator {

    private final TaxFeeConfiguration taxFeeConfiguration;


    public TaxCalculator(TaxFeeConfiguration taxFeeConfiguration) {
        this.taxFeeConfiguration = taxFeeConfiguration;
    }

    /**
     * Calculate the tax fee
     *
     * @param vehicle    vehicle
     * @param datesInput date
     * @return - total tax
     */
    public int getTollFee(Vehicle vehicle, Date... datesInput) {
        if (vehicle == null) {
            System.out.println("input vehicle is null.");
        }

        if (datesInput == null) {
            System.out.println("input data is null.");
        }

        if (isTollFreeVehicle(vehicle)) {
            log.info("Tax free Vehicle[{}]", vehicle.getType());
            return 0;
        }

        List<Date> dates = Arrays.stream(datesInput).sorted().collect(Collectors.toList());

        Map<LocalDate, List<LocalDateTime>> collect = dates.stream()
                .map(DateUtil::toLocalDateTime)
                .collect(Collectors.groupingBy(LocalDateTime::toLocalDate));

        int totalFees = 0;
        for (Map.Entry<LocalDate, List<LocalDateTime>> entry : collect.entrySet()) {
            totalFees = totalFees + getTollFeesPerDay(vehicle, entry.getValue());
        }
        log.info("Final Tax fees:{}", totalFees);
        return totalFees;

    }

    public int getTollFeesPerDay(Vehicle vehicle, List<LocalDateTime> localDateTimes) {

        LocalDateTime intervalStarLocal = localDateTimes.get(0);
        Date intervalStart = DateUtil.toDate(intervalStarLocal);
        int totalFee = 0;

        if (isTollFreeDate(intervalStarLocal)) return 0;

        int tempFee = getTollFee(intervalStart, vehicle);
        LocalDateTime tempDateTime = intervalStarLocal;
        for (LocalDateTime localDateTime : localDateTimes) {
            int nextFee = getTollFee(DateUtil.toDate(localDateTime), vehicle);
            long minutes = tempDateTime.until(localDateTime, ChronoUnit.MINUTES);
            if (minutes <= 60) {
                if (totalFee > 0) {
                    totalFee = totalFee - tempFee;
                }
                if (nextFee >= tempFee) {
                    tempFee = nextFee;
                }
                totalFee = totalFee + tempFee;
            } else {
                tempDateTime = localDateTime;
                totalFee = totalFee + nextFee;
            }
        }

        int maximumTollFees = taxFeeConfiguration.getMaximumTollFeesPerDay();
        if (totalFee > maximumTollFees) {
            log.info("Tax Fee:{} is more than the maximum tax fees, So the maximum tax fees:{}", totalFee, maximumTollFees);
            totalFee = maximumTollFees;
        }

        log.info("Tax fee:{} for Date:{}", totalFee, intervalStarLocal.toLocalDate());

        return totalFee;
    }

    private boolean isTollFreeVehicle(Vehicle vehicle) {
        if (vehicle == null) return false;
        String vehicleType = vehicle.getType();
        boolean tollFreeVehicle = Arrays.stream(TaxFreeVehicles.values()).anyMatch(tollFreeVehicles -> tollFreeVehicles.getType().equals(vehicleType));
        if (tollFreeVehicle) {
            log.info("Tax free vehicle:{}", vehicle.getType());
        }
        return tollFreeVehicle;
    }

    public int getTollFee(final Date date, Vehicle vehicle) {
        LocalDateTime localDateTime = DateUtil.toLocalDateTime(date);
        if (isTollFreeDate(localDateTime) || isTollFreeVehicle(vehicle)) return 0;

        LocalTime localTime = localDateTime.toLocalTime();

        Optional<TimeslotFees> any = taxFeeConfiguration.getTimeslotFees().stream().filter(
                e -> (localTime.isBefore(e.getEndTime()) && localTime.isAfter(e.getStartTime())) || localTime.equals(e.getStartTime()) || localTime.equals(e.getEndTime())
        ).findAny();

        int tollFees = any.map(TimeslotFees::getFees).orElse(0);
        log.info("Tax fees:{} for the date:{}", tollFees, date);
        return tollFees;
    }

    private boolean isTollFreeDate(LocalDateTime localDateTime) {
        LocalDate localDate = localDateTime.toLocalDate();
        return isWeekend(localDate) || isHoliday(localDate);
    }

    private boolean isHoliday(final LocalDate localDate) {
        boolean holidayFlag = taxFeeConfiguration.getHolidays().contains(MonthDay.from(localDate));
        if (holidayFlag) {
            log.info("Date[{}] It is on Holiday", localDate);
        }
        return holidayFlag;
    }

    private boolean isWeekend(final LocalDate ld) {
        DayOfWeek day = DayOfWeek.of(ld.get(ChronoField.DAY_OF_WEEK));
        boolean isDateOnWeekend = day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY;
        if (isDateOnWeekend) {
            log.info("Date[{}] is on Weekend", ld);
        }
        return isDateOnWeekend;
    }


}
