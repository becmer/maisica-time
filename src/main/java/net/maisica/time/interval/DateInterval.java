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
import java.time.format.DateTimeParseException;
import java.util.Objects;
import net.maisica.time.span.DateSpan;

/**
 *
 * @author Kamil Becmer <kamil.becmer at maisica.pl>
 */
public final class DateInterval extends AbstractInterval<LocalDate, DateInterval> implements TemporalInterval<LocalDate>, Serializable {

    public static DateInterval parse(final CharSequence text) {
        Objects.requireNonNull(text, "text");
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '/') {
                final LocalDate start = LocalDate.parse(text.subSequence(0, i++).toString());
                final LocalDate end = LocalDate.parse(text.subSequence(i, text.length()).toString());
                return of(start, end);
            }
        }
        throw new DateTimeParseException("Interval cannot be parsed, no forward slash found", text, 0);
    }

    public static DateInterval of(final Interval<LocalDate> interval) {
        Objects.requireNonNull(interval, "interval");
        if (interval instanceof DateInterval) {
            return (DateInterval) interval;
        }
        return of(interval.getStart(), interval.getEnd());
    }
    
    public static DateInterval of(final LocalDate start, final Duration duration) {
        Objects.requireNonNull(start, "start");
        Objects.requireNonNull(duration, "duration");
        return of(start, start.plus(duration));
    }

    public static DateInterval of(final LocalDate start, final LocalDate end) {
        Objects.requireNonNull(start, "start");
        Objects.requireNonNull(end, "end");
        if (end.compareTo(start) < 0) {
            throw new IllegalArgumentException("end is before start");
        }
        return new DateInterval(start, end);
    }

    private DateInterval(final LocalDate start, final LocalDate end) {
        super(start, end);
    }

    @Override
    protected IntervalFactory<LocalDate, DateInterval> getFactory() {
        return DateInterval::new;
    }

    @Override
    public DateSpan toSpan() {
        return DateSpan.of(getStart(), toDuration());
    }

}
