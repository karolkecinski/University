#include<cstdio>
int diff[1000000];
int help[1000000];

void klocki(int t[], int n)
{
    int smaller, bigger;
    
    for(int i=0; i<1000000; i++)
    {
        diff[i] = 0;
        help[i] = 0;
    }
    
    for(int i=0; i<n; i++)
    {
        if(t[i]>diff[t[i]])
            help[t[i]]=t[i];

        for(int j=1; j<1000000; j++)
        {
            if(diff[j] != 0)
            {
                bigger = diff[j];
                smaller = bigger - j;

                if(diff[j+t[i]] < bigger + t[i] && help[j+t[i]] < bigger + t[i])
                    help[j+t[i]] = bigger + t[i];
                

                if(j-t[i] > 0)
                {
                    if(diff[j-t[i]] < bigger && help[j-t[i]] < bigger)
                        help[j-t[i]] = bigger;
                } 
                else if(j-t[i] == 0) 
                {
                    if(diff[0] < bigger && help[0] < bigger)
                        help[0] = bigger;
                } 
                else 
                {
                    if(diff[(-1)*(j-t[i])] < smaller + t[i] && help[(-1)*(j-t[i])] < smaller + t[i])
                        help[(-1)*(j-t[i])] = smaller + t[i];
                }
            }
        }

        for(int j=0; j<1000000; j++)
        {
            diff[j] = help[j];
        }
    }
    

    if(diff[0] != 0)
        printf("TAK\n%d", diff[0]);
    else
    {
        printf("NIE\n");

        int i=1;

        while(diff[i] == 0 || diff[i] == i)
        {
            i++;
        }

        printf("%d", i);
    }
}

int main()
{
    int n;
    scanf("%d", &n);

    int t[n];

    for(int i=0; i<n; i++)
    {
        scanf("%d", &t[i]);
    }
    

    klocki(t, n);
}