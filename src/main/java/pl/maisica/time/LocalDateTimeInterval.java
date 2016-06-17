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
import java.time.LocalDateTime;

/**
 *
 * @author Kamil Becmer <kamil.becmer at maisica.pl>
 */
public final class LocalDateTimeInterval extends AbstractInterval<LocalDateTime, LocalDateTimeInterval> implements TemporalInterval<LocalDateTime>, Serializable {

    public static LocalDateTimeInterval between(final LocalDateTime start, final LocalDateTime end) {
        return new LocalDateTimeInterval(start, end);
    }
    
    public static LocalDateTimeInterval of(final Interval<LocalDateTime> interval) {
        if (interval instanceof LocalDateTimeInterval) {
            return (LocalDateTimeInterval) interval;
        }
        return new LocalDateTimeInterval(interval.getStart(), interval.getEnd());
    }

    private LocalDateTimeInterval(final LocalDateTime start, final LocalDateTime end) {
        super(start, end);
    }

    @Override
    protected IntervalFactory<LocalDateTime, LocalDateTimeInterval> getFactory() {
        return LocalDateTimeInterval::new;
    }

}
