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
package net.maisica.time.span;

import java.time.Duration;
import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import net.maisica.time.interval.YearInterval;

/**
 *
 * @author Kamil Becmer <kamil.becmer at maisica.pl>
 */
public final class YearSpan extends AbstractSpan<Year, YearSpan> implements TemporalSpan<Year> {

    public static YearSpan parse(final CharSequence text) {
        Objects.requireNonNull(text, "text");
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '/') {
                final Year start = Year.parse(text.subSequence(0, i++).toString());
                final Duration duration = Duration.parse(text.subSequence(i, text.length()).toString());
                return of(start, duration);
            }
        }
        throw new DateTimeParseException("Span cannot be parsed, no forward slash found", text, 0);
    }

    public static YearSpan of(final Span<Year, Duration> span) {
        Objects.requireNonNull(span, "span");
        if (span instanceof YearSpan) {
            return (YearSpan) span;
        }
        return of(span.getStart(), span.getDuration());
    }

    public static YearSpan of(final Year start, final Duration duration) {
        Objects.requireNonNull(start, "start");
        Objects.requireNonNull(duration, "duration");
        if (duration.isNegative()) {
            throw new IllegalArgumentException("duration is negative");
        }
        return new YearSpan(start, duration);
    }

    private YearSpan(final Year start, final Duration duration) {
        super(start, duration);
    }

    @Override
    protected SpanFactory<Year, Duration, YearSpan> getFactory() {
        return YearSpan::new;
    }

    @Override
    public YearInterval toInterval() {
        return YearInterval.of(getStart(), getDuration());
    }

}
