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
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import net.maisica.time.interval.InstantInterval;

/**
 *
 * @author Kamil Becmer <kamil.becmer at maisica.pl>
 */
public final class InstantSpan extends AbstractSpan<Instant, InstantSpan> implements TemporalSpan<Instant> {

    public static InstantSpan parse(final CharSequence text) {
        Objects.requireNonNull(text, "text");
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '/') {
                final Instant start = Instant.parse(text.subSequence(0, i++).toString());
                final Duration duration = Duration.parse(text.subSequence(i, text.length()).toString());
                return of(start, duration);
            }
        }
        throw new DateTimeParseException("Span cannot be parsed, no forward slash found", text, 0);
    }

    public static InstantSpan of(final Span<Instant, Duration> span) {
        Objects.requireNonNull(span, "span");
        if (span instanceof InstantSpan) {
            return (InstantSpan) span;
        }
        return of(span.getStart(), span.getDuration());
    }

    public static InstantSpan of(final Instant start, final Duration duration) {
        Objects.requireNonNull(start, "start");
        Objects.requireNonNull(duration, "duration");
        if (duration.isNegative()) {
            throw new IllegalArgumentException("duration is negative");
        }
        return new InstantSpan(start, duration);
    }

    private InstantSpan(final Instant start, final Duration duration) {
        super(start, duration);
    }

    @Override
    protected SpanFactory<Instant, Duration, InstantSpan> getFactory() {
        return InstantSpan::new;
    }

    @Override
    public InstantInterval toInterval() {
        return InstantInterval.of(getStart(), getDuration());
    }

}
