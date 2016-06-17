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
import java.time.Year;

/**
 *
 * @author Kamil Becmer <kamil.becmer at maisica.pl>
 */
public final class YearInterval extends AbstractInterval<Year, YearInterval> implements TemporalInterval<Year>, Serializable {

    public static YearInterval between(final Year start, final Year end) {
        return new YearInterval(start, end);
    }
    
    public static YearInterval of(final Interval<Year> interval) {
        if (interval instanceof YearInterval) {
            return (YearInterval) interval;
        }
        return new YearInterval(interval.getStart(), interval.getEnd());
    }

    public YearInterval(final Year start, final Year end) {
        super(start, end);
    }

    @Override
    protected IntervalFactory<Year, YearInterval> getFactory() {
        return YearInterval::new;
    }

}
