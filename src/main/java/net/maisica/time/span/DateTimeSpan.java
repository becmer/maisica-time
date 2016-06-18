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
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import net.maisica.time.interval.DateTimeInterval;

public final class DateTimeSpan extends AbstractSpan<LocalDateTime, DateTimeSpan> implements TemporalSpan<LocalDateTime> {

    public static DateTimeSpan parse(final CharSequence text) {
        Objects.requireNonNull(text, "text");
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '/') {
                final LocalDateTime start = LocalDateTime.parse(text.subSequence(0, i++).toString());
                final Duration duration = Duration.parse(text.subSequence(i, text.length()).toString());
                return of(start, duration);
            }
        }
        throw new DateTimeParseException("Span cannot be parsed, no forward slash found", text, 0);
    }

    public static DateTimeSpan of(final Span<LocalDateTime, Duration> span) {
        Objects.requireNonNull(span, "span");
        if (span instanceof DateTimeSpan) {
            return (DateTimeSpan) span;
        }
        return of(span.getStart(), span.getDuration());
    }

    public static DateTimeSpan of(final LocalDateTime start, final Duration duration) {
        Objects.requireNonNull(start, "start");
        Objects.requireNonNull(duration, "duration");
        if (duration.isNegative()) {
            throw new IllegalArgumentException("duration is negative");
        }
        return new DateTimeSpan(start, duration);
    }

    private DateTimeSpan(final LocalDateTime start, final Duration duration) {
        super(start, duration);
    }

    @Override
    protected SpanFactory<LocalDateTime, Duration, DateTimeSpan> getFactory() {
        return DateTimeSpan::new;
    }

    @Override
    public DateTimeInterval toInterval() {
        return DateTimeInterval.of(getStart(), getDuration());
    }

}
