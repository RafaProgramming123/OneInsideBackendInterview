package org.example;

public class Main
{
    static int product2Nums(int a, int b)
    {
        if (b == 0)
            return 0;
        if (b > 0)
            return (a + product2Nums(a, b - 1));
        return -product2Nums(a, -b);
    }

    public static void main(String args[])
    {
        System.out.println("Two Positive: " + product2Nums(12, 12));
        System.out.println("One Negative: " + product2Nums(-5, 9));
        System.out.println("One Negative: " + product2Nums(2, -2));
        System.out.println("Two Negative: " + product2Nums(-8, -17));

    }
}  