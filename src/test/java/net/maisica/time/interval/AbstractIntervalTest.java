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
package net.maisica.time.interval;

import static org.junit.Assert.*;
import org.junit.Test;

public class AbstractIntervalTest {
    
    private final AbstractInterval interval;
    
    public AbstractIntervalTest() {
        interval = new AbstractIntervalImpl(0, 10);
    }

    @Test
    public void testGetStart() {
        System.out.println("getStart");
        assertEquals("validate start value", 0, interval.getStart());
    }

    @Test
    public void testGetEnd() {
        System.out.println("getEnd");
        assertEquals("validate end value", 10, interval.getEnd());
    }

    @Test
    public void testWithStart() {
        System.out.println("withStart");
        final AbstractInterval result = interval.withStart(10);
        assertNotSame("same instance", interval, result);
        assertEquals("invalid start", 10, result.getStart());
        assertEquals("invalid end", 10, result.getEnd());
        
        try {
            interval.withStart(null);
            fail("null permitted");
        } catch (NullPointerException ex) {
            // pass
        }
        
        try {
            interval.withStart(11);
            fail("unchecked bounds");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    @Test
    public void testWithEnd() {
        System.out.println("withEnd");
        final AbstractInterval result = interval.withEnd(0);
        assertNotSame("same instance", interval, result);
        assertEquals("invalid start", 0, result.getStart());
        assertEquals("invalid end", 0, result.getEnd());
        
        try {
            interval.withEnd(null);
            fail("null permitted");
        } catch (NullPointerException ex) {
            // pass
        }
        
        try {
            interval.withEnd(-1);
            fail("unchecked bounds");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        assertFalse("must be empty", interval.isEmpty());
        assertTrue("can't be empty", interval.withEnd(0).isEmpty());
    }

    @Test
    public void testContains() {
        System.out.println("contains");
        assertFalse("lower bound exceeded", interval.contains(-1));
        assertTrue("lower bound mismatch", interval.contains(0));
        assertTrue("upper bound mismatch", interval.contains(9));
        assertFalse("upper bound exceeded", interval.contains(10));
    }

    @Test
    public void testEncloses() {
        System.out.println("encloses");
        assertTrue("non-empty not encloses itself", interval.encloses(interval));
        assertFalse("encloses even greater lower bound", interval.withStart(1).encloses(interval));
        assertFalse("encloses even less upper bound", interval.withEnd(9).encloses(interval));
        final AbstractInterval sample = interval.withEnd(0);
        assertTrue("empty not encloses itself", sample.encloses(sample));
        assertFalse("unequal empties encloses", interval.withStart(10).encloses(sample));
    }

    @Test
    public void testAbuts() {
        System.out.println("abuts");
        AbstractInterval sample;
        
        sample = new AbstractIntervalImpl(-10, -1); // -10 / -1
        assertFalse("case 1 abuts", interval.abuts(sample));
        
        sample = sample.withEnd(0); // -10 / 0
        assertTrue("case 2 not abuts", interval.abuts(sample));
        
        sample = sample.withStart(0); // 0 / 0
        assertTrue("case 3 not abuts", interval.abuts(sample));
        
        sample = sample.withEnd(1); // 0 / 1
        assertFalse("case 4 abuts", interval.abuts(sample));
        
        sample = sample.withEnd(10); // 0 / 10
        assertFalse("case 5 abuts", interval.abuts(sample));
        
        sample = sample.withStart(10); // 10 / 10
        assertTrue("case 6 not abuts", interval.abuts(sample));
        
        sample = sample.withEnd(20); // 10 / 20
        assertTrue("case 7 not abuts", interval.abuts(sample));
        
        sample = sample.withStart(11); // 11 / 20
        assertFalse("case 8 abuts", interval.abuts(sample));
    }

    @Test
    public void testOverlaps() {
        System.out.println("overlaps");
        AbstractInterval sample;
        
        sample = new AbstractIntervalImpl(-1, -1); // -10 / -1
        assertFalse("case 1 overlaps", interval.overlaps(sample));
        
        sample = sample.withEnd(0); // -10 / 0
        assertFalse("case 2 overlaps", interval.overlaps(sample));
        
        sample = sample.withStart(0); // 0 / 0
        assertFalse("case 3 overlaps", interval.overlaps(sample));
        
        sample = sample.withEnd(1); // 0 / 1
        assertTrue("case 4 not overlaps", interval.overlaps(sample));
        
        sample = sample.withStart(-1); // -1 / 1
        assertTrue("case 5 not overlaps", interval.overlaps(sample));
        
        sample = sample.withEnd(20); // -1 / 20
        assertTrue("case 6 not overlaps", interval.overlaps(sample));
        
        sample = sample.withStart(9); // 9 / 20
        assertTrue("case 7 not overlaps", interval.overlaps(sample));
        
        sample = sample.withStart(10); // 10 / 20
        assertFalse("case 8 overlaps", interval.overlaps(sample));
        
        sample = sample.withStart(11); // 11 / 20
        assertFalse("case 9 overlaps", interval.overlaps(sample));
    }

    @Test
    public void testOverlap() {
        System.out.println("overlap");
        AbstractInterval result, sample;
        
        sample = new AbstractIntervalImpl(-1, -1); // -10 / -1
        assertNull("case 1 overlaps", interval.overlap(sample));
        
        sample = sample.withEnd(0); // -10 / 0
        assertNull("case 2 overlaps", interval.overlap(sample));
        
        sample = sample.withStart(0); // 0 / 0
        assertNull("case 3 overlaps", interval.overlap(sample));
        
        sample = sample.withEnd(1); // 0 / 1
        result = interval.overlap(sample);
        assertNotNull("case 4 not overlaps", result);
        assertEquals("case 4 mismatch", new AbstractIntervalImpl(0, 1), result);
        
        sample = sample.withStart(-1); // -1 / 1
        result = interval.overlap(sample);
        assertNotNull("case 5 not overlaps", result);
        assertEquals("case 5 mismatch", new AbstractIntervalImpl(0, 1), result);
        
        sample = sample.withEnd(20); // -1 / 20
        result = interval.overlap(sample);
        assertNotNull("case 6 not overlaps", result);
        assertEquals("case 6 mismatch", new AbstractIntervalImpl(0, 10), result);
        
        sample = sample.withStart(9); // 9 / 20
        result = interval.overlap(sample);
        assertNotNull("case 7 not overlaps", result);
        assertEquals("case 7 mismatch", new AbstractIntervalImpl(9, 10), result);
        
        sample = sample.withStart(10); // 10 / 20
        assertNull("case 8 overlaps", interval.overlap(sample));
        
        sample = sample.withStart(11); // 11 / 20
        assertNull("case 9 overlaps", interval.overlap(sample));
    }

    @Test
    public void testGap() {
        System.out.println("gap");
        AbstractInterval result, sample;
        
        sample = new AbstractIntervalImpl(-10, -2);
        result = interval.gap(sample);
        assertNotNull("case 1 gap expected", result);
        assertEquals("case 1 gap mismatch", new AbstractIntervalImpl(-2, 0), result);
        
        sample = sample.withEnd(0);
        result = interval.gap(sample);
        assertNull("case 2 gap unexpected", result);
        
        sample = new AbstractIntervalImpl(1, 9);
        result = interval.gap(sample);
        assertNull("case 3 gap unexpected", result);
        
        sample = new AbstractIntervalImpl(10, 10);
        result = interval.gap(sample);
        assertNull("case 4 gap unexpected", result);
        
        sample = sample.withEnd(20);
        result = interval.gap(sample);
        assertNull("case 5 gap unexpected", result);
        
        sample = sample.withStart(12);
        result = interval.gap(sample);
        assertNotNull("case 6 gap expected", result);
        assertEquals("case 6 gap mismatch", new AbstractIntervalImpl(10, 12), result);
    }

    @Test
    public void testJoin() {
        System.out.println("join");
        AbstractInterval result, sample;
        
        sample = new AbstractIntervalImpl(-10, -2);
        result = interval.join(sample);
        assertEquals("case 1 join mismatch", new AbstractIntervalImpl(-10, 10), result);
        
        sample = sample.withEnd(0);
        result = interval.join(sample);
        assertEquals("case 2 join mismatch", new AbstractIntervalImpl(-10, 10), result);
        
        sample = new AbstractIntervalImpl(1, 9);
        result = interval.join(sample);
        assertEquals("case 3 join mismatch", new AbstractIntervalImpl(0, 10), result);
        
        sample = new AbstractIntervalImpl(10, 10);
        result = interval.join(sample);
        assertEquals("case 4 join mismatch", new AbstractIntervalImpl(0, 10), result);
        
        sample = sample.withEnd(20);
        result = interval.join(sample);
        assertEquals("case 5 join mismatch", new AbstractIntervalImpl(0, 20), result);
        
        sample = sample.withStart(12);
        result = interval.join(sample);
        assertEquals("case 6 join mismatch", new AbstractIntervalImpl(0, 20), result);
    }

    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        AbstractInterval sample;
        
        final int hashCode = interval.hashCode();
        assertEquals("case 1 not equals", hashCode, interval.hashCode());
        
        sample = new AbstractIntervalImpl(0, 9);
        assertNotEquals("case 2 equals", hashCode, sample.hashCode());
        
        sample = sample.withEnd(10);
        assertEquals("case 3 not equals", hashCode, sample.hashCode());
        
        sample = sample.withStart(1);
        assertNotEquals("case 4 equals", hashCode, sample.hashCode());
    }

    @Test
    public void testEquals() {
        System.out.println("equals");
        AbstractInterval sample;
        
        sample = new AbstractIntervalImpl(-10, -2);
        assertNotEquals("case 1 equals", interval, sample);
        
        sample = sample.withEnd(0);
        assertNotEquals("case 2 equals", interval, sample);
        
        sample = new AbstractIntervalImpl(0, 10);
        assertEquals("case 3 not equals", interval, sample);
        
        sample = sample.withStart(10);
        assertNotEquals("case 4 equals", interval, sample);
        
        sample = sample.withEnd(20);
        assertNotEquals("case 5 equals", interval, sample);
        
        sample = sample.withStart(12);
        assertNotEquals("case 6 equals", interval, sample);
    }
    
    public static final class AbstractIntervalImpl extends AbstractInterval<Integer, AbstractIntervalImpl> {

        public AbstractIntervalImpl(final Integer start, final Integer end) {
            super(start, end);
        }

        @Override
        protected IntervalFactory<Integer, AbstractIntervalImpl> getFactory() {
            return AbstractIntervalImpl::new;
        }
        
    }
    
}
