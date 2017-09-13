package demo;

/**
 * Created by 59685 on 2017/9/13.
 */
//max son array
public class Solution2 {
    public static void main(String[] args) {
        int[] arr = {31,-41,59,26,-53,58,97,-93,-23,84};
        System.out.print(maxSon(arr));
    }

    public static int maxSon(int[] arr) {
        int[] son = new int[arr.length];
        son[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            son[i] = Math.max(arr[i], arr[i] + son[i - 1]);
        }
        int res = Integer.MIN_VALUE;
        for (int i = 0; i < son.length; i++) {
            res = Math.max(res, son[i]);
        }
        return res;
    }
}
