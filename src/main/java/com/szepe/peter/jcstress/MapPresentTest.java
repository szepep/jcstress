package com.szepe.peter.jcstress;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.BooleanResult2;

import static org.openjdk.jcstress.annotations.Expect.*;

@JCStressTest
@Outcome(id = "true, true", expect = ACCEPTABLE,  desc = "each loaded")
@Outcome(id = "true, false", expect = FORBIDDEN,  desc = "only one loaded")
@Outcome(id = "false, true", expect = FORBIDDEN,  desc = "only one loaded")
@Outcome(id = "false, false", expect = FORBIDDEN,  desc = "map is not initialized")
@State
public class MapPresentTest {

    private BadDesignLazyLoadMap underTest = new BadDesignLazyLoadMap(10);

    @Actor
    public void actor1(BooleanResult2 r) {
        r.r1 = mapIsPresent();
    }

    @Actor
    public void actor2(BooleanResult2 r) {
        r.r2 = mapIsPresent();
    }

    private boolean mapIsPresent() {
        return underTest.getMap() != null;
    }
}
