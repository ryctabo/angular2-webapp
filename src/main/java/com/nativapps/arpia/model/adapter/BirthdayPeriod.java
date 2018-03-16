package com.nativapps.arpia.model.adapter;

import java.util.Calendar;

/**
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0-SNAPSHOT
 */
public class BirthdayPeriod {

    /**
     * Type of birthday period.
     */
    public enum Type {

        /**
         * Represent today's settings.
         * <p>
         * <strong>E.g.,</strong>
         * the {@code from} and {@code to} properties are today.
         */
        TODAY,

        /**
         * Represent tomorrow's settings.
         * <p>
         * <strong>E.g.,</strong>
         * the {@code from} and {@code to} properties are tomorrow.
         */
        TOMORROW,

        /**
         * Represent the next week settings.
         * <p>
         * <strong>E.g.,</strong>
         * the {@code from} property is tomorrow and {@code to} property
         * is 7 days in the future.
         */
        NEXT_WEEK,

        /**
         * Represent this month settings.
         * <p>
         * <strong>E.g.,</strong>
         * the {@code from} property is the first day of this month and
         * the {@code to} property is the last day of this month.
         */
        THIS_MONTH
    }

    /**
     * Type of period.
     */
    private Type type;

    /**
     * The from calendar.
     */
    private Calendar from;

    /**
     * The to calendar.
     */
    private Calendar to;

    /**
     * Construct a instance of the {@link BirthdayPeriod}
     * with default values.
     */
    public BirthdayPeriod() {
        this.from = Calendar.getInstance();
        this.to = Calendar.getInstance();
        this.type = Type.TODAY;
    }

    /**
     * Construct a instance of the {@link BirthdayPeriod}
     * with the given parameters.
     *
     * @param type type of birthday period
     * @param from range date, start date
     * @param to   range date, end date
     */
    public BirthdayPeriod(Type type, Calendar from, Calendar to) {
        this.type = type;
        this.from = from;
        this.to = to;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Calendar getFrom() {
        return from;
    }

    public void setFrom(Calendar from) {
        this.from = from;
    }

    public Calendar getTo() {
        return to;
    }

    public void setTo(Calendar to) {
        this.to = to;
    }
}
