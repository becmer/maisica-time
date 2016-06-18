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
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 *
 * @author Kamil Becmer <kamil.becmer at maisica.pl>
 */
public final class LocalDateTimeInterval extends AbstractInterval<LocalDateTime, LocalDateTimeInterval> implements TemporalInterval<LocalDateTime>, Serializable {
    
    public static LocalDateTimeInterval parse(final CharSequence text) {
        Objects.requireNonNull(text, "text");
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '/') {
                final LocalDateTime start = LocalDateTime.parse(text.subSequence(0, i++).toString());
                final LocalDateTime end = LocalDateTime.parse(text.subSequence(i, text.length()).toString());
                return between(start, end);
            }
        }
        throw new DateTimeParseException("Interval cannot be parsed, no forward slash found", text, 0);
    }

    public static LocalDateTimeInterval between(final LocalDateTime start, final LocalDateTime end) {
        Objects.requireNonNull(start, "start");
        Objects.requireNonNull(end, "end");
        if (end.compareTo(start) < 0) {
            throw new IllegalArgumentException("end is before start");
        }
        return new LocalDateTimeInterval(start, end);
    }
    
    public static LocalDateTimeInterval of(final Interval<LocalDateTime> interval) {
        Objects.requireNonNull(interval, "interval");
        if (interval instanceof LocalDateTimeInterval) {
            return (LocalDateTimeInterval) interval;
        }
        return between(interval.getStart(), interval.getEnd());
    }

    private LocalDateTimeInterval(final LocalDateTime start, final LocalDateTime end) {
        super(start, end);
    }

    @Override
    protected IntervalFactory<LocalDateTime, LocalDateTimeInterval> getFactory() {
        return LocalDateTimeInterval::new;
    }

}
