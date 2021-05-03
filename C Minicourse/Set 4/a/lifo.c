#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "lifo.h"

struct node* new_node()
{
    struct node* newNode = (struct node*)malloc(sizeof(struct node));
    int data_type = 0;
    size_t allocated_size = 0;
    void* data = NULL;
    struct node* next_node = NULL;

    return newNode;
}

bool empty(struct node* stack)
{
    return stack == NULL;
}

int size(struct node* stack)
{
    int counter=0;

    while (!empty(stack)){
        counter++;
        stack=stack->next_node;
    }
    return counter;
}

struct node* push(struct node* next)
{
    printf("Podaj typ elementu: \n 1 int \n 2 tablica int \n 3 float \n 4 string\n");
    int typ;
    scanf("%d",&typ);
    struct node* nowy = new_node();
    nowy->data_type = typ;
    nowy->next_node = next;

    switch (typ)
    {
    case 1:
        {
            nowy->allocated_size = sizeof(int);
            nowy->data = malloc(nowy->allocated_size);
            int data_next;
            printf("Podaj liczbe:\n");
            scanf("%d",&data_next);
            *((int*)nowy->data) = data_next;
            break;
        }

    case 2:
        {
            int Tsize;
            printf("Podaj rozmiar: \n");
            scanf("%d",&Tsize);

            nowy->allocated_size = Tsize * sizeof(int)* Tsize;
            nowy->data = malloc(nowy->allocated_size);
            printf("Wpisz elementy:\n");
            int* array = nowy->data;

            for (int i=0; i<Tsize;i++)
            { scanf("%d",&array[i]); }

            break;
        }

    case 3:
        {
            nowy->allocated_size = sizeof(float);
            nowy->data = malloc(nowy->allocated_size);
            float data_next;
            printf("Podaj liczbe:\n");
            scanf("%f",&data_next);
            *((float*)nowy->data) = data_next;

            break;
        }

    case 4:
        {
            int Tsize;
            printf("Podaj dlugosc: \n");
            scanf("%d",&Tsize);
            nowy->allocated_size = sizeof(char)*(Tsize+1);
            nowy->data =  malloc(nowy->allocated_size);
            printf("Podaj ciag znakow:\n");
            scanf("%s",nowy->data);

            break;
        }
    }
    return nowy;
}

struct node* pop(struct node* stack)
{
    if (empty(stack))
    {
        printf("STOS PUSTY\n");
        return stack;
    }

    struct node* reszta = stack->next_node;
    free(stack->data);
    free(stack);
    printf("element usuniety\n");

    return reszta;
}

int MAX = 10000;

int main(void)
{
    struct node* stack;
    int choice=1;
    int getanswer=0;

    while(choice)
    {
        printf("1. Push New Element. \n");
        printf("2. Pop. \n");
        printf("0. Exit. \n");

        scanf(" %d", &getanswer);

        switch(getanswer)
        {
            case 0:
            return 0;

            case 1:
            stack = push(stack);
            
            case 2:
            stack = pop(stack);
        }
    }
}