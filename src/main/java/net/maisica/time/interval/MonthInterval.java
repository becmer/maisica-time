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
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 *
 * @author Kamil Becmer <kamil.becmer at maisica.pl>
 */
public final class MonthInterval extends AbstractInterval<Month, MonthInterval> implements Serializable {

    public static MonthInterval parse(final CharSequence text) {
        Objects.requireNonNull(text, "text");
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '/') {
                final Month start;
                try {
                    start = Month.valueOf(text.subSequence(0, i++).toString());
                } catch (IllegalArgumentException ex) {
                    throw new DateTimeParseException("Interval cannot be parsed, invalid start descriptor", text, 0);
                }
                final Month end;
                try {
                    end = Month.valueOf(text.subSequence(i, text.length()).toString());
                } catch (IllegalArgumentException ex) {
                    throw new DateTimeParseException("Interval cannot be parsed, invalid end descriptor", text, i);
                }
                return of(start, end);
            }
        }
        throw new DateTimeParseException("Interval cannot be parsed, no forward slash found", text, 0);
    }

    public static MonthInterval of(final Interval<Month> interval) {
        Objects.requireNonNull(interval, "interval");
        if (interval instanceof MonthInterval) {
            return (MonthInterval) interval;
        }
        return of(interval.getStart(), interval.getEnd());
    }

    public static MonthInterval of(final Month start, final Month end) {
        Objects.requireNonNull(start, "start");
        Objects.requireNonNull(end, "end");
        if (end.compareTo(start) < 0) {
            throw new IllegalArgumentException("end is before start");
        }
        return new MonthInterval(start, end);
    }

    private MonthInterval(final Month start, final Month end) {
        super(start, end);
    }

    @Override
    protected IntervalFactory<Month, MonthInterval> getFactory() {
        return MonthInterval::new;
    }

    @Override
    public String toString() {
        return getStart().name() + '/' + getEnd().name();
    }

}
