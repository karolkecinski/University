#include<stdio.h> 
#include<string.h> 
#include<stdlib.h> 

void nth_line(int n, FILE *f)
{
    char c = getc(f); 
    int line = 0;
    while (c != EOF) 
    { 
        if (c == '\n') 
        { 
            line++; 
        } 
  
        if (line == n) 
        {
            printf("%c", c);
        } 
  
        c = getc(f); 
    }
}

void compareFiles(FILE *fp1, FILE *fp2, FILE *n1, FILE *n2) 
{ 
    char ch1 = getc(fp1); 
    char ch2 = getc(fp2); 
  
    int p = 0, line = 0; 

    while (ch1 != EOF && ch2 != EOF) 
    { 
        p++; 
  
        if (ch1 == '\n' && ch2 == '\n') 
        { 
            line++; 
            p = 0; 
        } 
  
        if (ch1 != ch2) 
        { 
            printf("%d %d", line, p);
            nth_line(line, n1);
            nth_line(line, n2);
        } 
  
        ch1 = getc(fp1); 
        ch2 = getc(fp2); 
    } 
} 
  
int main() 
{ 
    FILE *fp1 = fopen("file1.txt", "r"); 
    FILE *fp2 = fopen("file2.txt", "r"); 
    FILE *n1 = fopen("file1.txt", "r"); 
    FILE *n2 = fopen("file2.txt", "r"); 
  
    compareFiles(fp1, fp2, n1, n2); 
  
    fclose(fp1); 
    fclose(fp2); 

    return 0; 
} 