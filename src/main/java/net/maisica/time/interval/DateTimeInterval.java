/*
 * Copyright 2016 Kamil Becmer <kamil.becmer at maisica.pl>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.maisica.time.interval;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import net.maisica.time.span.DateTimeSpan;

public final class DateTimeInterval extends AbstractInterval<LocalDateTime, DateTimeInterval> implements TemporalInterval<LocalDateTime>, Serializable {

    public static DateTimeInterval parse(final CharSequence text) {
        Objects.requireNonNull(text, "text");
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '/') {
                final LocalDateTime start = LocalDateTime.parse(text.subSequence(0, i++).toString());
                final LocalDateTime end = LocalDateTime.parse(text.subSequence(i, text.length()).toString());
                return of(start, end);
            }
        }
        throw new DateTimeParseException("Interval cannot be parsed, no forward slash found", text, 0);
    }
    
    public static DateTimeInterval wholeDay(final LocalDate date) {
        Objects.requireNonNull(date, "date");
        final LocalDateTime start = date.atStartOfDay();
        return new DateTimeInterval(start, start.plusDays(1L));
    }

    public static DateTimeInterval of(final Interval<LocalDateTime> interval) {
        Objects.requireNonNull(interval, "interval");
        if (interval instanceof DateTimeInterval) {
            return (DateTimeInterval) interval;
        }
        return of(interval.getStart(), interval.getEnd());
    }

    public static DateTimeInterval of(final LocalDateTime start, final Duration duration) {
        Objects.requireNonNull(start, "start");
        Objects.requireNonNull(duration, "duration");
        return of(start, start.plus(duration));
    }

    public static DateTimeInterval of(final LocalDateTime start, final Period period) {
        Objects.requireNonNull(start, "start");
        Objects.requireNonNull(period, "period");
        return of(start, start.plus(period));
    }

    public static DateTimeInterval of(final LocalDateTime start, final LocalDateTime end) {
        Objects.requireNonNull(start, "start");
        Objects.requireNonNull(end, "end");
        if (end.compareTo(start) < 0) {
            throw new IllegalArgumentException("end is before start");
        }
        return new DateTimeInterval(start, end);
    }

    private DateTimeInterval(final LocalDateTime start, final LocalDateTime end) {
        super(start, end);
    }

    @Override
    protected IntervalFactory<LocalDateTime, DateTimeInterval> getFactory() {
        return DateTimeInterval::new;
    }

    @Override
    public DateTimeSpan toSpan() {
        return DateTimeSpan.of(getStart(), toDuration());
    }

}
