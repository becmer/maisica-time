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
import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;

/**
 *
 * @author Kamil Becmer <kamil.becmer at maisica.pl>
 */
public final class LocalTimeSpan extends AbstractSpan<LocalTime, LocalTimeSpan> implements TemporalSpan<LocalTime>, Serializable {
    
    public static LocalTimeSpan between(final LocalTime start, final Duration duration) {
        Objects.requireNonNull(start, "start");
        Objects.requireNonNull(duration, "duration");
        if (duration.isNegative()) {
            throw new IllegalArgumentException("duration is negative");
        }
        return new LocalTimeSpan(start, duration);
    }
    
    public static LocalTimeSpan of(final Span<LocalTime, Duration> span) {
        Objects.requireNonNull(span, "span");
        if (span instanceof LocalTimeSpan) {
            return (LocalTimeSpan) span;
        }
        return between(span.getStart(), span.getDuration());
    }

    private LocalTimeSpan(final LocalTime start, final Duration duration) {
        super(start, duration);
    }

    @Override
    protected SpanFactory<LocalTime, Duration, LocalTimeSpan> getFactory() {
        return LocalTimeSpan::new;
    }
    
}
