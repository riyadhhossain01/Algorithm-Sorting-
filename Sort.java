import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by riyad on 7/6/2017.
 C

 Implement a new sorting algorithm which is combination of insertion sort and merge sort. Your sorting algorithm should be faster than either one. Do the same test that you did for strassen on this example and plot it.

 * Provide a single file name "Sort.cpp/java" and write three functions/methods: insersionSort, mergeSort, mergeInsertionSort. Each function/method accepts an array and sorts it (In case of cpp the last parameter becomes the size). Your plots should be in a file name SortingAnalysis.docx

 */


public class Sort {
    /* generate */
    private static int[][] generateRandomMatrix(int size) {
        int[][] a = new int[size][size];
        //quad O(n * m)
        for (int i = 0; i < size; i++) {// 1, n+1 , n
            for (int j = 0; j < size; j++) { // 1 , n * m , n
                a[i][j] = (int) (Math.random() * 100);
            }
        }
        return a;
    }


    // insertionSort
    public static int[] insertionsort(int[] array) {
        for (int j = 1; j < array.length; j++) {
            int key = array[j];
            int i = j - 1;
            while (i >= 0 && array[i] > key) {
                array[i + 1] = array[i];
                i = i - 1;

            }
            array[i + 1] = key;
        }
        return array;
    }


    //merge sort

    public static int[] mergeSort(int[] array) {
// if the array has more than 1 element, we need to split it and merge the sorted halves
        if (array.length > 1) {
            int elementsInA1 = array.length / 2;
            int elementsInA2 = elementsInA1;
// declare and initialize the two arrays once we've determined their sizes
            int arr1[] = new int[elementsInA1];
            int arr2[] = new int[elementsInA2];
// copy the first part of 'array' into 'arr1', causing arr1 to become full
            for (int i = 0; i < elementsInA1; i++)
                arr1[i] = array[i];
// copy the remaining elements of 'array' into 'arr2', causing arr2 to become full
            for (int i = elementsInA1; i < elementsInA1 + elementsInA2; i++)
                arr2[i - elementsInA1] = array[i];

            arr1 = mergeSort(arr1);
            arr2 = mergeSort(arr2);

            int i = 0, j = 0, k = 0;
// the below loop will run until one of the sub-arrays becomes empty
// in my implementation, it means until the index equals the length of the sub-array
            while (arr1.length != j && arr2.length != k) {
// if the current element of arr1 is less than current element of arr2
                if (arr1[j] < arr2[k]) {
// copy the current element of arr1 into the final array
                    array[i] = arr1[j];
                    i++;

                    j++;
                } else {
// copy the current element of arr1 into the final array
                    array[i] = arr2[k];
// increase the index of the final array
                    i++;
// increase the index of arr2
                    k++;
                }
            }
            while (arr1.length != j) {
                array[i] = arr1[j];
                i++;
                j++;
            }
            while (arr2.length != k) {
                array[i] = arr2[k];
                i++;
                k++;
            }
        }
// return the sorted array to the caller of the function
        return array;
    }

    // converting 2D array to 1D array
    public static int[] array_1D(int[][] array, int x) {
        int[] m = new int[x * x];
        int y = 0;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < x; j++) {
                m[y++] = array[i][j];
            }
        }
        return m;
    }

    // converting the 1D array to 2D array
    public static int[][] array_2D(int[] array, int n) {
        int[][] array_2D = new int[n][n];
        int x = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                array_2D[i][j] = array[x];
                x++;
            }
        }

        return array_2D;
    }
    public static final int k=10;


    // insert&merge
    public static void mergeInsertionsort(int A[], int p, int r) {
        if (r - p > k) {
            int q = (p + r) / 2;
            mergeInsertionsort(A, p, q);
            mergeInsertionsort(A,q+1,r);
            mergeSort(A);
        } else {
            insertionsort(A);
        }

    }

    public static void main(String[] args) throws IOException {
        Sort array = new Sort();

        PrintWriter outputFile = new PrintWriter("Mat_data.txt"); // output file contains size , complexitysteps average running time

        System.out.println("Enter the sizes for matrix:(ex:10,20,...,100) ");


        for (int x = 0; x < 10; x++) {
            Scanner reader = new Scanner(System.in);  // Reading from System.in
            System.out.println("Please enter a value for dimension of adjacency matrix \n");
            int n = reader.nextInt(); // Scans the next token of the input as an int.
            int[][] Mat_A = generateRandomMatrix(n);
            int[] y = array_1D(Mat_A, n);
            int[][] Mat_B = generateRandomMatrix(n);
            int[]t=array_1D(Mat_B,n);
            int[][] Mat_C = generateRandomMatrix(n);
            int[]q = array_1D(Mat_C,n);




            //insertion
            long start = System.currentTimeMillis();

            int[] o = insertionsort(y);
            //array2.insertSort(y,0,y.length-1);
            long end = System.currentTimeMillis();


            //merge
            int []l = array_1D(Mat_A, n);
            long start_m = System.currentTimeMillis();

            int[] w = mergeSort(l);
            //array1.mergeSort(y,0,y.length-1);
            long end_m = System.currentTimeMillis();


            //merge&insertion
            int [] K = array_1D(Mat_A, n);
            long start_mi = System.currentTimeMillis();

            array.mergeInsertionsort(K,0,K.length-1);

            long end_mi = System.currentTimeMillis();
            int [][] d = array_2D(K,n);
            int[][] z = array_2D(w, n);
            int[][] p = array_2D(o, n);

            System.out.println(" the average  running time for insertion sort: " + (end-start));
            System.out.println(" the average  running time for merge sort: " + (end_m-start_m));
            System.out.println(" the average  running time for insertion/merge sort: " + (end_mi-start_mi));

            outputFile.println("steps:" + n +" the average running time for insertionsort: " + (end-start));
            outputFile.println("steps:" + n +" the average running time for mergesort : " + (end_m-start_m));
            outputFile.println("steps:" + n +" the average running time for insertion/merge sort: " + (end_mi-start_mi));
        }
        outputFile.close();
    }
}





