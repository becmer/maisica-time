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
import java.time.Month;

/**
 *
 * @author Kamil Becmer <kamil.becmer at maisica.pl>
 */
public final class MonthInterval extends AbstractInterval<Month, MonthInterval> implements Serializable {
    
    public static MonthInterval between(final Month start, final Month end) {
        return new MonthInterval(start, end);
    }

    public MonthInterval(final Month start, final Month end) {
        super(start, end);
    }

    @Override
    protected IntervalFactory<Month, MonthInterval> getFactory() {
        return MonthInterval::new;
    }
    
}
