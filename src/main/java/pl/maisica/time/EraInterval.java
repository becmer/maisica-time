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
import java.time.chrono.IsoEra;

/**
 *
 * @author Kamil Becmer <kamil.becmer at maisica.pl>
 */
public final class EraInterval extends AbstractInterval<IsoEra, EraInterval> implements Serializable {

    public static EraInterval between(final IsoEra start, final IsoEra end) {
        return new EraInterval(start, end);
    }
    
    public static EraInterval of(final Interval<IsoEra> interval) {
        if (interval instanceof EraInterval) {
            return (EraInterval) interval;
        }
        return new EraInterval(interval.getStart(), interval.getEnd());
    }

    public EraInterval(final IsoEra start, final IsoEra end) {
        super(start, end);
    }

    @Override
    protected IntervalFactory<IsoEra, EraInterval> getFactory() {
        return EraInterval::new;
    }

}
