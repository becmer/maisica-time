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
package pl.maisica.time;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 *
 * @author Kamil Becmer <kamil.becmer at maisica.pl>
 */
public final class DayOfWeekInterval extends AbstractInterval<DayOfWeek, DayOfWeekInterval> implements Serializable {

    public static DayOfWeekInterval parse(final CharSequence text) {
        Objects.requireNonNull(text, "text");
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '/') {
                final DayOfWeek start;
                try {
                    start = DayOfWeek.valueOf(text.subSequence(0, i++).toString());
                } catch (IllegalArgumentException ex) {
                    throw new DateTimeParseException("Interval cannot be parsed, invalid start descriptor", text, 0);
                }
                final DayOfWeek end;
                try {
                    end = DayOfWeek.valueOf(text.subSequence(i, text.length()).toString());
                } catch (IllegalArgumentException ex) {
                    throw new DateTimeParseException("Interval cannot be parsed, invalid end descriptor", text, i);
                }
                return between(start, end);
            }
        }
        throw new DateTimeParseException("Interval cannot be parsed, no forward slash found", text, 0);
    }

    public static DayOfWeekInterval between(final DayOfWeek start, final DayOfWeek end) {
        Objects.requireNonNull(start, "start");
        Objects.requireNonNull(end, "end");
        if (end.compareTo(start) < 0) {
            throw new IllegalArgumentException("end is before start");
        }
        return new DayOfWeekInterval(start, end);
    }

    public static DayOfWeekInterval of(final Interval<DayOfWeek> interval) {
        Objects.requireNonNull(interval, "interval");
        if (interval instanceof DayOfWeekInterval) {
            return (DayOfWeekInterval) interval;
        }
        return new DayOfWeekInterval(interval.getStart(), interval.getEnd());
    }

    private DayOfWeekInterval(final DayOfWeek start, final DayOfWeek end) {
        super(start, end);
    }

    @Override
    protected IntervalFactory<DayOfWeek, DayOfWeekInterval> getFactory() {
        return DayOfWeekInterval::new;
    }

    @Override
    public String toString() {
        return getStart().name() + '/' + getEnd().name();
    }

}
