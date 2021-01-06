package uz.pdp.appg4duonotaryserver.components;

public class test {


   public static void kvadratTenglama(int a, int b, int c)
    {
         if (((b * b) - (4 * a * c)) > 0)
            System.out.println("2 ta ildizga ega");


        else if (((b * b) - (4 * a * c)) == 0)
            System.out.println("1 ta ildizga ega");


        else
            System.out.println("Tenglama yechimga ega emas");
    }


    public static void main(String[] args)
    {
        int a =1, b = 6, c = 8;
        kvadratTenglama(a, b, c);
    }




}
