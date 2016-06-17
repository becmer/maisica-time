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
import java.time.Instant;

/**
 *
 * @author Kamil Becmer <kamil.becmer at maisica.pl>
 */
public final class InstantInterval extends AbstractInterval<Instant, InstantInterval> implements TemporalInterval<Instant>, Serializable {
    
    public static InstantInterval between(final Instant start, final Instant end) {
        return new InstantInterval(start, end);
    }

    private InstantInterval(final Instant start, final Instant end) {
        super(start, end);
    }

    @Override
    protected IntervalFactory<Instant, InstantInterval> getFactory() {
        return InstantInterval::new;
    }
    
}
