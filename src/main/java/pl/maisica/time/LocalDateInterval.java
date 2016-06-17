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
import java.time.LocalDate;

/**
 *
 * @author Kamil Becmer <kamil.becmer at maisica.pl>
 */
public final class LocalDateInterval extends AbstractInterval<LocalDate, LocalDateInterval> implements TemporalInterval<LocalDate>, Serializable {

    public static LocalDateInterval between(final LocalDate start, final LocalDate end) {
        return new LocalDateInterval(start, end);
    }
    
    public static LocalDateInterval of(final Interval<LocalDate> interval) {
        if (interval instanceof LocalDateInterval) {
            return (LocalDateInterval) interval;
        }
        return new LocalDateInterval(interval.getStart(), interval.getEnd());
    }

    private LocalDateInterval(final LocalDate start, final LocalDate end) {
        super(start, end);
    }

    @Override
    protected IntervalFactory<LocalDate, LocalDateInterval> getFactory() {
        return LocalDateInterval::new;
    }

}
