package dev.zach473.thegrid.logic;

import dev.zach473.thegrid.types.IVec2;

public class OffsetLogic {
    /**
     * Compute an integer offset in a spiraling pattern using the given index.
     *
     * @param index The index of the step on the spiral.
     * @return The computed X and Z offsets.
     */
    public static IVec2 computeOffset(int index) {
        // // I'm not smart enough to understand the math behind it, but it works.
        //
        // The spiraling pattern looks something like this:
        //
        // 5 --- 4 --- 3
        // |           |
        // 6     1 --- 2
        // |
        // 7 --- 8 --- 9
        //
        // https://upload.wikimedia.org/wikipedia/commons/1/1d/Ulam_spiral_howto_all_numbers.svg
        //
        // Full credit to this comment: https://math.stackexchange.com/a/163101

        int k = (int) Math.ceil((Math.sqrt(index) - 1) / 2);
        int t = (2 * k) + 1;
        int m = (int) Math.pow(t, 2);
        t -= 1;

        if (index >= m - t) {
            return new IVec2(k - (m - index), -k);
        } else {
            m -= t;
        }

        if (index >= m - t) {
            return new IVec2(-k, -k + (m - index));
        } else {
            m -= t;
        }

        if (index >= m - t) {
            return new IVec2(-k + (m - index), k);
        } else {
            return new IVec2(k, k - (m - index - t));
        }
    }
}
