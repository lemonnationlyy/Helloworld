package demo;

import java.util.Arrays;

/**
 * Created by 59685 on 2017/9/5.
 */
public class RoundRobin {
    public static void main(String[] args) {
        int i=-1, n=4, gcd = 1, cw = 0;
        int[] W = {1,2,4,8};
        while (true) {
            i = (i + 1) % n;
            if (i == 0) {
                cw = cw - gcd;
                if (cw <= 0) {
                    cw = 8;
                    if (cw == 0)
                        System.out.println(-1);
                }
            }
            if (W[i] >= cw)
                System.out.println(i);
        }
    }
}
