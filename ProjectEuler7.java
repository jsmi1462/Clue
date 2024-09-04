public class ProjectEuler7
{
    public static void  main(String[] args)
    {
        int count = 0;
        int currNum = 2;
        while (count!=10001)
        {
            if (isPrime(currNum))
            {
                count++;\
                System.out.println(count);
            }
            System.out.println(currNum);
            currNum++;
        }
        System.out.print(currNum);
    }
    
    public static boolean isPrime(int number)
    {
        //System.out.println(number);
        for (int i = 2; i < number; i++)
        {
            if (number%i == 0)
            {
                return false;
            }
        }
        return true;
    }
}