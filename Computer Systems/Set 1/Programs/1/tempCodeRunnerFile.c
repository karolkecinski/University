#include <stdlib.h>
#include <stdio.h>
#include <assert.h>
unsigned long long int pomocnicza(int n, int k) 
{ 
    if (n == 0) 
        return k == 0; 
  
    if (k == 0) 
        return 1; 

    unsigned long long int wynik = 0; 
  
    for (int i=0; i<=9; i++) 
        if (k-i >= 0) 
            wynik += F(n-1, k-i); 
  
    return wynik; 
} 
unsigned long long int F(int n, int sum) 
{ 
    // Initialize final answer 
    unsigned long long int ans = 0; 
  
    // Traverse through every digit from 1 to 
    // 9 and count numbers beginning with it 
    for (int i = 1; i <= 9; i++) 
    if (sum-i >= 0) 
        ans += pomocnicza(n-1, sum-i); 
  
    return ans; 
} 
  
// Driver program 
int main() 
{ 
    int n = 2, sum = 5; 
    int result;
    result = F(n, sum); 
    printf("%i", result);
    return 0; 
}