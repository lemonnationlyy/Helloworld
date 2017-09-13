package notes.sort;

/**
 * Created by 59685 on 2017/9/11.
 */
public class Sort {
    public static void main(String[] args) {
        int[] arr = {1, 3, 5, 7, 8, 2, 4, 6, 7, 0,12,1,1,23,22,2,22,22};
//        fastSort1(arr, 0, arr.length-1);
//        insertSort(arr);
//        mergeSort(arr, 0, arr.length-1);
//        heapSort(arr);
//        countSort(arr, 23);
        radixSort(arr, 10);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+"->");
        }
    }

    private static void fastSort1(int[] arr, int i, int j) {
        if (i < j) {
            int mid = fastSort(arr, i, j);
            fastSort1(arr, i, mid-1);
            fastSort1(arr, mid + 1, j);
        }
    }

    private static int fastSort(int[] arr, int i, int j) {
        int temp = arr[i];
        while (i<j) {
            while (i<j && arr[j]>temp) {
                j--;
            }
            if (i<j) {
                arr[i++] = arr[j];
                while (i < j && arr[i] < temp) {
                    i++;
                }
                if (i<j) {
                    arr[j--] = arr[i];
                }
            }
        }
        arr[i] = temp;
        return i;
    }

    private static void insertSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            int j = i-1;
            for (; j >= 0 && arr[j] > temp; j--) {
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = temp;
        }
    }

    private static void mergeSort(int[] arr, int left, int right) {
        if (left<right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid+1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;
        while (i <= mid && j <= right) {
            if (arr[i]<arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }
        while (i<=mid) {
            temp[k++] = arr[i++];
        }
        while (j<=right) {
            temp[k++] = arr[j++];
        }
        for (int l = 0; l < temp.length; l++) {
            arr[l + left] = temp[l];
        }
    }

    private static void heapSort(int[] arr) {
        for (int i = arr.length-1; i > 0; i--) {
            makeHeap(arr, i);
            swap(arr, 0, i);
        }
    }

    private static void makeHeap(int[] arr, int len) {
        for (int i = (len-1)/2; i >= 0; i--) {
            int temp;
            if (2*i+2<len && arr[2*i+2]>arr[2*i+1]) {
                temp = 2 * i + 2;
            } else {
                temp = 2 * i + 1;
            }
            if (arr[temp] > arr[i]) {
                swap(arr, i, temp);
            }
        }
    }

    //计数排序 稳定排序
    private static void countSort(int[] array, int range) {
        int[] countArray = new int[range + 1];
        for (int i = 0; i < array.length; i++) {
            int value = array[i];
            countArray[value] += 1;
        }

        for (int i = 1; i < countArray.length; i++) {
            countArray[i] += countArray[i - 1];
        }
        //countArray[i]记载了array中小于i的元素个数 减一就是i所在的正确位置

        int[] temp = new int[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            int value = array[i];
            int position = countArray[value] - 1;

            temp[position] = value;
            countArray[value] -= 1;
        }

        for (int i = 0; i < array.length; i++) {
            array[i] = temp[i];
        }
    }

    //基数排序 稳定排序 O(d*(r+n))
    //d = 1, 10, 100, 1000......
    private static void radixSort(int[] arr, int d) {
        int n = 1;
        int[][] bucket = new int[10][arr.length];
        int[] order = new int[10];
        while (n<=d) {
            for (int i = 0; i < arr.length; i++) {
                int value = arr[i];
                int temp = (value / n) % 10;
                bucket[temp][order[temp]] = value;
                order[temp] += 1;
            }
            int k = 0;
            for (int i = 0; i < 10; i++) {
                if (order[i] != 0) {
                    for (int j = 0; j < order[i]; j++) {
                        arr[k++] = bucket[i][j];
                    }
                }
                order[i] = 0;
            }
            n *= 10;
        }
    }

    private static void swap(int[] arr, int i, int j) {
        arr[i] = arr[i] + arr[j];
        arr[j] = arr[i] - arr[j];
        arr[i] = arr[i] - arr[j];
    }
}
