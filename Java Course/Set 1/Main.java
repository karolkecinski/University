import java.util.Arrays;
import java.util.Scanner;
 
public class Main
{
    enum Kierunek
    {
		U, D, L, R;
	}
 
    private static int[][] GenerujSpirale(final int size)
    {
        int[][] content = new int[size][size];

        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                content[j][i] = -1;         // Ustawiam domyślne wartości na -1
            }
        }
        
        Kierunek dir = Kierunek.R;          // Kierunek początkowy, od "1" do "2"
        
        int y = size / 2;
        int x;

        if(size % 2 == 0)                   // Znajduję środek
            x = y - 1;
            else
            x = y;

        for(int i=1; i <= (size * size); i++)
        {
            content[y][x] = i;  // Wypełnianie spirali
 
            switch(dir)         // "Poruszanie się" po spirali
            {
			case R:
                if(x <= (size - 1) && content[y - 1][x] == -1 && i > 1)
                { 
                    dir = Kierunek.U; 
                    y--;
                } else x++; 

                break;

			case U:
                if(content[y][x - 1] == -1) 
                {
                    dir = Kierunek.L; 
                    x--;
                } else y--; 

                break;

			case L:
                if(x == 0 || content[y + 1][x] == -1) 
                {
                    dir = Kierunek.D; 
                    y++;
                } else x--; 

                break;

			case D:
                if(content[y][x + 1] == -1) 
                {
                    dir = Kierunek.R; 
                    x++;
                } else y++; 
                
                break;
			}
        }

        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                if(!isPrime(content[j][i]))  // Zerowanie liczb, które nie są pierwsze
                content[j][i] = 0;
            }
        }
        
		return content;
	}
 
    public static boolean isPrime(final int number)
    {  
        if(number == 2)
            return true;
        if(number < 2 || number % 2 == 0)
            return false;

        int limit = (int)Math.sqrt(number);
        for(long n = 3; n <= limit; n += 2)
        {
            if(number % n == 0)
                return false;
		}
        
        return true;
	}
 
    public static void main(String[] args)
    {
        int size = Integer.parseInt(args[0]);

        if(size < 2 || size > 200)
            throw new IllegalArgumentException("Parametr musi mimeścić się w zakresie od 2 do 200");

        final int[][] Spirala = GenerujSpirale(size);             
        
        for(int i=0; i<size; i++)
        {
            for(int j=0; j<size; j++)
            {
                if(Spirala[i][j]==0)
                    System.out.print("   "); // Jeżeli pole jest wyzerowane, to wypisuję spację
                else
                    System.out.print(" * "); // Jeżeli pole jest liczbą, to wypisuję gwiazdkę
            }
			System.out.println("\n");
        }
	}
}