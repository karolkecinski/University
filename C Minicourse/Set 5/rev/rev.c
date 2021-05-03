#include<stdio.h>
#include<errno.h>
#include <string.h>

long count_characters(FILE *f)
{
    fseek(f, -1L, 2);
    long last_pos = ftell(f);
    last_pos++;
    return last_pos;
}

int file_length(char s[])
{
   int i = 0;

   while(s[i]!=NULL)
      i++;

   return i;	
}

void string_reverse(char st[])
{
   int i,j,len;
   char ch;

   j = len = file_length(st) - 1;
   i = 0;

   while(i < j)
   {
      ch = st[j];
      st[j] = st[i];
      st[i] = ch;
      i++;
      j--;
   }
}

void main()
{
    int a=0, n=0;
    long cnt=0;
    char ch, ch1, z;
    FILE *fp1, *fp2;

    fp1 = fopen("Fa1.txt", "r");
    fp2 = fopen("F2.txt", "w");


    while((z=fgetc(fp1))!=EOF)
    {
        cnt++;
    }

    rewind(fp1);
    int i, j;
    char temp[100];
    char RES[cnt];
    char R[1000];

    while(a!=cnt)
    {
        RES[a++]=fgetc(fp1);
    }

    n = file_length(RES);
    printf("%d", n);
    RES[a++]='\n';
    for(i = 0; i < n; i++)
    {
        for(j = 0; i < n && RES[i]!=' ' && RES[i]!='\n'; ++i,++j)
        {
            temp[j] = RES[i];
            printf("%c", temp[j]);
        }

        temp[j] = '\0';

        string_reverse(temp);

        strcat(R, temp);
        if(RES[i]==' ')
            strcat(R, " ");
            else
            strcat(R, "\n");
    }

    a=0;

    while (cnt)
    {
        fputc(R[++a], fp2);
        cnt--;
    }

    fclose(fp1);
    fclose(fp2);
}