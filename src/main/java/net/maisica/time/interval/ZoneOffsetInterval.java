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
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public final class ZoneOffsetInterval extends AbstractInterval<ZoneOffset, ZoneOffsetInterval> implements Serializable {

    public static ZoneOffsetInterval parse(final CharSequence text) {
        Objects.requireNonNull(text, "text");
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '/') {
                final ZoneOffset start = ZoneOffset.of(text.subSequence(0, i++).toString());
                final ZoneOffset end = ZoneOffset.of(text.subSequence(i, text.length()).toString());
                return of(start, end);
            }
        }
        throw new DateTimeParseException("Interval cannot be parsed, no forward slash found", text, 0);
    }

    public static ZoneOffsetInterval of(final Interval<ZoneOffset> interval) {
        Objects.requireNonNull(interval, "interval");
        if (interval instanceof ZoneOffsetInterval) {
            return (ZoneOffsetInterval) interval;
        }
        return of(interval.getStart(), interval.getEnd());
    }

    public static ZoneOffsetInterval of(final ZoneOffset start, final ZoneOffset end) {
        Objects.requireNonNull(start, "start");
        Objects.requireNonNull(end, "end");
        if (end.compareTo(start) < 0) {
            throw new IllegalArgumentException("end is before start");
        }
        return new ZoneOffsetInterval(start, end);
    }

    private ZoneOffsetInterval(final ZoneOffset start, final ZoneOffset end) {
        super(start, end);
    }

    @Override
    protected IntervalFactory<ZoneOffset, ZoneOffsetInterval> getFactory() {
        return ZoneOffsetInterval::new;
    }

}
