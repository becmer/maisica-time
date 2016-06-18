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
import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 *
 * @author Kamil Becmer <kamil.becmer at maisica.pl>
 */
public final class YearInterval extends AbstractInterval<Year, YearInterval> implements TemporalInterval<Year>, Serializable {

    public static YearInterval parse(final CharSequence text) {
        Objects.requireNonNull(text, "text");
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '/') {
                final Year start = Year.parse(text.subSequence(0, i++).toString());
                final Year end = Year.parse(text.subSequence(i, text.length()).toString());
                return between(start, end);
            }
        }
        throw new DateTimeParseException("Interval cannot be parsed, no forward slash found", text, 0);
    }

    public static YearInterval between(final Year start, final Year end) {
        Objects.requireNonNull(start, "start");
        Objects.requireNonNull(end, "end");
        if (end.compareTo(start) < 0) {
            throw new IllegalArgumentException("end is before start");
        }
        return new YearInterval(start, end);
    }

    public static YearInterval of(final Interval<Year> interval) {
        Objects.requireNonNull(interval, "interval");
        if (interval instanceof YearInterval) {
            return (YearInterval) interval;
        }
        return between(interval.getStart(), interval.getEnd());
    }

    private YearInterval(final Year start, final Year end) {
        super(start, end);
    }

    @Override
    protected IntervalFactory<Year, YearInterval> getFactory() {
        return YearInterval::new;
    }

}
