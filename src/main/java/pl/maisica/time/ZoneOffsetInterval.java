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
import java.time.ZoneOffset;

/**
 *
 * @author Kamil Becmer <kamil.becmer at maisica.pl>
 */
public final class ZoneOffsetInterval extends AbstractInterval<ZoneOffset, ZoneOffsetInterval> implements Serializable {
    
    public static ZoneOffsetInterval between(final ZoneOffset start, final ZoneOffset end) {
        return new ZoneOffsetInterval(start, end);
    }
    
    public static ZoneOffsetInterval of(final Interval<ZoneOffset> interval) {
        if (interval instanceof ZoneOffsetInterval) {
            return (ZoneOffsetInterval) interval;
        }
        return new ZoneOffsetInterval(interval.getStart(), interval.getEnd());
    }

    public ZoneOffsetInterval(final ZoneOffset start, final ZoneOffset end) {
        super(start, end);
    }

    @Override
    protected IntervalFactory<ZoneOffset, ZoneOffsetInterval> getFactory() {
        return ZoneOffsetInterval::new;
    }
    
}
