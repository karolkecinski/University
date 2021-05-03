import java.util.Scanner;

public class Main
{

	public static void main(String[] args)
	{
		int Tab_size = 0;
		Scanner s = new Scanner(System.in);

		System.out.println("Podaj ilość elementów do posortowania: ");
        Tab_size = s.nextInt();
        
        int[] tab = new int[Tab_size];
        int val;

		System.out.println("Podaj liczby do posortowania: ");
		for(int i = 0; i < Tab_size; i++)
		{
			val = s.nextInt();
			tab[i] = val;
        }
        
        MergeSort set = new MergeSort(tab);
		set.start();

		try
		{
			set.join();
		}
        catch(Exception e)
		{
			System.out.print("Error");
		}

        System.out.print("\n");
        
        for(int i = 0; i < Tab_size; i++) 
        {
            System.out.println(tab[i]);
        }
    
	}
}