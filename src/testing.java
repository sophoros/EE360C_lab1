import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
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

    @Test
    public void testSwap() {
        Program1 program = new Program1();
        int location_count = 2;
        int employ_count = 2;
        ArrayList<ArrayList<Integer>> loc_pref = new ArrayList<>();
        for (int i = 0; i < location_count; i++) {
            loc_pref.add(new ArrayList<Integer>());
        }
        loc_pref.get(0).add(0);
        loc_pref.get(0).add(1);
        loc_pref.get(1).add(1);
        loc_pref.get(1).add(0);
        ArrayList<ArrayList<Integer>> emp_pref = new ArrayList<>();
        for (int i = 0; i < employ_count; i++) {
            emp_pref.add(new ArrayList<>());
        }
        emp_pref.get(0).add(0);
        emp_pref.get(0).add(1);
        emp_pref.get(1).add(1);
        emp_pref.get(1).add(0);
        ArrayList<Integer> slots = new ArrayList<>();
        slots.add(1);
        slots.add(1);
        ArrayList<Integer> match = new ArrayList<>();
        match.add(1);
        match.add(0);

        Matching stable = new Matching(2,2,loc_pref,emp_pref,slots,match);
        assertFalse(program.isStableMatching(stable));
    }

    @Test
    public void stableMarriageGaleShapley_employeeoptimal_Test_LG_160() throws Exception {
        Program1 program1 = new Program1();
        Matching problem = Driver.parseMatchingProblem("src/large_inputs/320.in");
        System.out.printf("problem: %s\n", problem);
        Matching solution = program1.stableMarriageGaleShapley_employeeoptimal(problem);
        System.out.println(solution);
        assert(program1.isStableMatching(solution));
    }
}
