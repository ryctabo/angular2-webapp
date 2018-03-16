package com.nativapps.arpia.service.validator;

import com.nativapps.arpia.model.ConfigLanguage;
import com.nativapps.arpia.model.dto.DailyRepeatData;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.MonthlyRepeatData;
import com.nativapps.arpia.model.dto.RepeatData;
import com.nativapps.arpia.model.dto.WeeklyRepeatData;
import com.nativapps.arpia.model.dto.YearlyRepeatData;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class RepeatValidator extends Validator {

    /**
     * Don't let anyone instantiate this class.
     */
    private RepeatValidator() {
    }

    /**
     * Evaluate if the repeat object contains errors or missing requeriments to
     * meet.
     * <p>
     * If this is the case, then the method add messages to
     * {@link ErrorMessageData} object using the language configuration.
     *
     * @param repeat repeat to evaluate
     * @param emd error message data
     * @param config language configuration
     *
     * @throws IllegalArgumentException if any of the parameters is missing
     */
    public static void evaluateRepeat(RepeatData repeat, ErrorMessageData emd,
            ConfigLanguage config) {
        missingParameters(config, emd);

        if (repeat.getTime() == null)
            emd.addMessage(config.getString("repeat.time"));
        if (repeat.getStartDate() == null)
            emd.addMessage(config.getString("repeat.start_date"));
        if (repeat.getEndDate() == null)
            emd.addMessage(config.getString("repeat.end_date"));

        if (repeat instanceof DailyRepeatData) {
            DailyRepeatData daily = (DailyRepeatData) repeat;
            if (daily.getEvery() == null)
                emd.addMessage(config.getString("repeat.every"));
        } else if (repeat instanceof WeeklyRepeatData) {
            WeeklyRepeatData weekly = (WeeklyRepeatData) repeat;
            if (weekly.getEvery() == null)
                emd.addMessage(config.getString("repeat.every"));
            if (weekly.getMonday() == null)
                emd.addMessage(config.getString("repeat.monday"));
            if (weekly.getTuesday() == null)
                emd.addMessage(config.getString("repeat.tuesday"));
            if (weekly.getWednesday() == null)
                emd.addMessage(config.getString("repeat.wednesday"));
            if (weekly.getThursday() == null)
                emd.addMessage(config.getString("repeat.thursday"));
            if (weekly.getFriday() == null)
                emd.addMessage(config.getString("repeat.friday"));
            if (weekly.getSaturday() == null)
                emd.addMessage(config.getString("repeat.saturday"));
            if (weekly.getSunday() == null)
                emd.addMessage(config.getString("repeat.sunday"));
        } else if (repeat instanceof MonthlyRepeatData) {
            MonthlyRepeatData monthly = (MonthlyRepeatData) repeat;
            if (monthly.getEvery() == null)
                emd.addMessage(config.getString("repeat.every"));
            if (monthly.getDayOfMonth() == null)
                emd.addMessage(config.getString("repeat.day_of_month"));
        } else if (repeat instanceof YearlyRepeatData) {
            YearlyRepeatData yearly = (YearlyRepeatData) repeat;
            if (yearly.getEvery() == null)
                emd.addMessage(config.getString("repeat.every"));
            if (yearly.getDateOfYear() == null)
                emd.addMessage(config.getString("repeat.date_of_year"));
        }
    }

}
