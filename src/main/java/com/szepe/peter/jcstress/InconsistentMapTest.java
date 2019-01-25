package com.szepe.peter.jcstress;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.BooleanResult2;

import java.util.Map;
import java.util.Random;

import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;
import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE_INTERESTING;
import static org.openjdk.jcstress.annotations.Expect.FORBIDDEN;

@JCStressTest
@Outcome(id = "true, true", expect = ACCEPTABLE,  desc = "each loaded")
@Outcome(id = "true, false", expect = ACCEPTABLE_INTERESTING,  desc = "only one loaded")
@Outcome(id = "false, true", expect = ACCEPTABLE_INTERESTING,  desc = "only one loaded")
@Outcome(id = "false, false", expect = FORBIDDEN,  desc = "map is not initialized")
@State
public class InconsistentMapTest {

    int size = 1000;
    private Random rand = new Random();
    private BadDesignLazyLoadMap underTest = new BadDesignLazyLoadMap(size);

    @Actor
    public void actor1(BooleanResult2 r) {
        r.r1 = isMapConsistent();
    }

    @Actor
    public void actor2(BooleanResult2 r) {
        r.r2 = isMapConsistent();
    }

    private boolean isMapConsistent() {
        int key = rand.nextInt(size);
        Map<Integer, Integer> map = underTest.getMap();
        try {
            Integer value = map.get(key);
            if (value != key) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
