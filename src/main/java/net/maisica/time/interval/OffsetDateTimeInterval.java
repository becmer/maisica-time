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
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import net.maisica.time.span.OffsetDateTimeSpan;

public final class OffsetDateTimeInterval extends AbstractInterval<OffsetDateTime, OffsetDateTimeInterval> implements TemporalInterval<OffsetDateTime>, Serializable {

    public static OffsetDateTimeInterval parse(final CharSequence text) {
        Objects.requireNonNull(text, "text");
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '/') {
                final OffsetDateTime start = OffsetDateTime.parse(text.subSequence(0, i++).toString());
                final OffsetDateTime end = OffsetDateTime.parse(text.subSequence(i, text.length()).toString());
                return of(start, end);
            }
        }
        throw new DateTimeParseException("Interval cannot be parsed, no forward slash found", text, 0);
    }

    public static OffsetDateTimeInterval of(final Interval<OffsetDateTime> interval) {
        Objects.requireNonNull(interval, "interval");
        if (interval instanceof OffsetDateTimeInterval) {
            return (OffsetDateTimeInterval) interval;
        }
        return of(interval.getStart(), interval.getEnd());
    }

    public static OffsetDateTimeInterval of(final OffsetDateTime start, final Duration duration) {
        Objects.requireNonNull(start, "start");
        Objects.requireNonNull(duration, "duration");
        return of(start, start.plus(duration));
    }

    public static OffsetDateTimeInterval of(final OffsetDateTime start, final OffsetDateTime end) {
        Objects.requireNonNull(start, "start");
        Objects.requireNonNull(end, "end");
        if (end.compareTo(start) < 0) {
            throw new IllegalArgumentException("end is before start");
        }
        return new OffsetDateTimeInterval(start, end);
    }

    private OffsetDateTimeInterval(final OffsetDateTime start, final OffsetDateTime end) {
        super(start, end);
    }

    @Override
    protected IntervalFactory<OffsetDateTime, OffsetDateTimeInterval> getFactory() {
        return OffsetDateTimeInterval::new;
    }

    @Override
    public OffsetDateTimeSpan toSpan() {
        return OffsetDateTimeSpan.of(getStart(), toDuration());
    }

}
