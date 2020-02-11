import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class testing {

    @Test
    public void test1() {
        Program1 program = new Program1();
        int location_count = 1;
        int employ_count = 3;
        ArrayList<ArrayList<Integer>> loc_pref = new ArrayList<>();
        for (int i = 0; i < location_count; i++) {
            loc_pref.add(new ArrayList<Integer>());
        }
        loc_pref.get(0).add(0);
        loc_pref.get(0).add(1);
        loc_pref.get(0).add(2);
        ArrayList<ArrayList<Integer>> emp_pref = new ArrayList<>();
        for (int i = 0; i < employ_count; i++) {
            emp_pref.add(new ArrayList<Integer>());
        }
        emp_pref.get(0).add(0);
        emp_pref.get(1).add(0);
        emp_pref.get(2).add(0);
        ArrayList<Integer> slots = new ArrayList<>();
        slots.add(2);
        ArrayList<Integer> match = new ArrayList<>();
        match.add(0);
        match.add(0);
        match.add(-1);

        Matching stable = new Matching(1,3,loc_pref,emp_pref,slots,match);
        assert(program.isStableMatching(stable));
    }
}
