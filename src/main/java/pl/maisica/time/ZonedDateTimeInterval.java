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
import java.time.ZonedDateTime;

/**
 *
 * @author Kamil Becmer <kamil.becmer at maisica.pl>
 */
public final class ZonedDateTimeInterval extends AbstractInterval<ZonedDateTime, ZonedDateTimeInterval> implements TemporalInterval<ZonedDateTime>, Serializable {

    public static ZonedDateTimeInterval between(final ZonedDateTime start, final ZonedDateTime end) {
        return new ZonedDateTimeInterval(start, end);
    }
    
    public static ZonedDateTimeInterval of(final Interval<ZonedDateTime> interval) {
        if (interval instanceof ZonedDateTimeInterval) {
            return (ZonedDateTimeInterval) interval;
        }
        return new ZonedDateTimeInterval(interval.getStart(), interval.getEnd());
    }

    public ZonedDateTimeInterval(final ZonedDateTime start, final ZonedDateTime end) {
        super(start, end);
    }

    @Override
    protected IntervalFactory<ZonedDateTime, ZonedDateTimeInterval> getFactory() {
        return ZonedDateTimeInterval::new;
    }

}
