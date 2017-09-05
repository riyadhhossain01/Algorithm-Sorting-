import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by riyad on 7/2/2017.
 * * A

 Implement matrix multiplication algorithm.

 Implement the Strassen’s algorithm for matrix multiplication.

 Run both algorithm on 100 matrices of size 10x10, 20x20, 30x30, ... 100x100 and get the average running time. Plot the running time.

 Run the same test for size 100x100, 200x200, ... 1000x1000 and plot it. Run both algorithms for size 1000x1000, 2000x2000 ... 10000x10000. Your test may become so slow, also it is possible the program stops with memory error. In the later case explain which algorithm stops the program and why?

 * The name of your single implementation file should be "Strassen.cpp/java". You should have a function name "strassen" that accepts 2 matrices and return the multiplication of them. In case of cpp the third paarameter of the function is "n" (i.e. dimention). Your plots should be in a file name StrassenAnalysis.docx.

 B

 Answer to 4.2-3, 4.2-5, 4.3-8, 4.4-7, 6.4-4, 6.5-8.

 * write the solutions in a docx file and use font Calibri 14. Separate each answer from the others by line. You don't need to write the question, just write its number (e.g. 4.2-3) and from the next line write the solution. The name of the file should be Solutions.docx

 C

 Implement a new sorting algorithm which is combination of insertion sort and merge sort. Your sorting algorithm should be faster than either one. Do the same test that you did for strassen on this example and plot it.

 * Provide a single file name "Sort.cpp/java" and write three functions/methods: insersionSort, mergeSort, mergeInsertionSort. Each function/method accepts an array and sorts it (In case of cpp the last parameter becomes the size). Your plots should be in a file name SortingAnalysis.docx


 ** Compress all files using the specific format that is explained in the class and submit it.
 */
public class Strassen
{
/* generate */
    private static int [][] generateRandomMatrix(int size) {
        int[][] a = new int[size][size];
        //quad O(n * m)
        for (int i = 0; i < size; i++) {// 1, n+1 , n
            for (int j = 0; j < size; j++) { // 1 , n * m , n
                a[i][j] = (int) (Math.random() * 100);
            }
        }
        return a;
    }

    // return c = a * b
    public static int[][] multiply(int[][] a, int[][] b)
    {
        int m1 = a.length;
        int n1 = a[0].length;
        int m2 = b.length;
        int n2 = b[0].length;
        if (n1 != m2) throw new RuntimeException("Illegal matrix dimensions.");
        int[][] c = new int[m1][n2];
        for (int i = 0; i < m1; i++)
            for (int j = 0; j < n2; j++)
                for (int k = 0; k < n1; k++)
                    c[i][j] += a[i][k] * b[k][j];
        return c;
    }

    //Strassen

    public static int [][] add(int [][] A, int [][] B)
    {
        int n = A.length;

        int [][] result = new int[n][n];

        for(int i=0; i<n; i++)
            for(int j=0; j<n; j++)
                result[i][j] = A[i][j] + B[i][j];

        return result;
    }

    public static int [][] sub(int [][] A, int [][] B)
    {
        int n = A.length;

        int [][] result = new int[n][n];

        for(int i=0; i<n; i++)
            for(int j=0; j<n; j++)
                result[i][j] = A[i][j] - B[i][j];

        return result;
    }

    public static void divide(int[][] p1, int[][] c1, int iB, int jB)
    {
        for(int i1 = 0, i2=iB; i1<c1.length; i1++, i2++)
            for(int j1 = 0, j2=jB; j1<c1.length; j1++, j2++)
            {
                c1[i1][j1] = p1[i2][j2];
            }
    }

    public static void copy(int[][] c1, int[][] p1, int iB, int jB)
    {
        for(int i1 = 0, i2=iB; i1<c1.length; i1++, i2++)
            for(int j1 = 0, j2=jB; j1<c1.length; j1++, j2++)
            {
                p1[i2][j2] = c1[i1][j1];
            }
    }

    // Return result C for strassen
    public static int [][] strassen(int [][] a, int [][] b)
    {
        int n = a.length;
        int [][] result = new int[n][n];

        if((n%2 != 0 ) && (n !=1))
        {
            int[][] a1, b1, c1;
            int n1 = n+1;
            a1 = new int[n1][n1];
            b1 = new int[n1][n1];
            c1 = new int[n1][n1];

            for(int i=0; i<n; i++)
                for(int j=0; j<n; j++)
                {
                    a1[i][j] =a[i][j];
                    b1[i][j] =b[i][j];
                }
            c1 = strassen(a1, b1);
            for(int i=0; i<n; i++)
                for(int j=0; j<n; j++)
                    result[i][j] =c1[i][j];
            return result;
        }

        if(n == 1)
        {
            result[0][0] = a[0][0] * b[0][0];
        }
        else
        {
            int [][] A11 = new int[n/2][n/2];
            int [][] A12 = new int[n/2][n/2];
            int [][] A21 = new int[n/2][n/2];
            int [][] A22 = new int[n/2][n/2];

            int [][] B11 = new int[n/2][n/2];
            int [][] B12 = new int[n/2][n/2];
            int [][] B21 = new int[n/2][n/2];
            int [][] B22 = new int[n/2][n/2];

            divide(a, A11, 0 , 0);
            divide(a, A12, 0 , n/2);
            divide(a, A21, n/2, 0);
            divide(a, A22, n/2, n/2);

            divide(b, B11, 0 , 0);
            divide(b, B12, 0 , n/2);
            divide(b, B21, n/2, 0);
            divide(b, B22, n/2, n/2);

            int [][] P1 = strassen(add(A11, A22), add(B11, B22));
            int [][] P2 = strassen(add(A21, A22), B11);
            int [][] P3 = strassen(A11, sub(B12, B22));
            int [][] P4 = strassen(A22, sub(B21, B11));
            int [][] P5 = strassen(add(A11, A12), B22);
            int [][] P6 = strassen(sub(A21, A11), add(B11, B12));
            int [][] P7 = strassen(sub(A12, A22), add(B21, B22));

            int [][] C11 = add(sub(add(P1, P4), P5), P7);
            int [][] C12 = add(P3, P5);
            int [][] C21 = add(P2, P4);
            int [][] C22 = add(sub(add(P1, P3), P2), P6);

            copy(C11, result, 0 , 0);
            copy(C12, result, 0 , n/2);
            copy(C21, result, n/2, 0);
            copy(C22, result, n/2, n/2);
        }
        return result;
    }
    public static void main(String[] args)throws IOException {

        PrintWriter outputFile = new PrintWriter("Mat_data.txt"); // output file contains size , complexitysteps average running time
        System.out.println("Enter the sizes for matrix:(ex:10,20,...,100) ");
        for (int j = 0; j < 10; j++) {
            Scanner reader = new Scanner(System.in);  // Reading from System.in
            System.out.println("Please enter a value for dimension of adjacency matrix \n");
            int n = reader.nextInt(); // Scans the next token of the input as an int.


            int[][] Mat_A = generateRandomMatrix(n);
            int [][] Mat_B = generateRandomMatrix(n);
            long start = System.currentTimeMillis();
            multiply(Mat_A,Mat_B); // normal matrix multiplication
            strassen(Mat_A,Mat_B);
            long end = System.currentTimeMillis();
            System.out.println(" the average  running time: " + (end-start) );
            outputFile.println("steps:" + n + " the average running time: " + (end-start));
        }
        outputFile.close();
    }




}


