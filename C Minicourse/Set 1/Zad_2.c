#include <stdlib.h>
#include <stdio.h>
#include <assert.h>
#define MAX_STACK_HEIGHT 5

enum StackType {String, Integers, Pair, Float};

int inputIndex;
int outputIndex;

typedef struct myNode
{
    enum StackType type;
    char *myString;
    float floatVal;
    int integerVal;
    int pairA;
    int pairB;
    struct myNode *next;
}node;

node newNode(int inputType)
{
    node NodeNewElem;
    NodeNewElem.type = inputType;
    NodeNewElem.floatVal = 0;
    NodeNewElem.integerVal = 0;
    NodeNewElem.myString = NULL;
    NodeNewElem.next = NULL;
    NodeNewElem.pairA = 0;
    NodeNewElem.pairB = 0;
    return NodeNewElem;
}

void pop(node ms[])
{
    if(ms == NULL)
    {
        puts("Invalid argument");
    }else if( inputIndex == outputIndex){
        puts("The stack is empty");
    }else{
        printf("POP: ");
        node nd = ms[outputIndex];
        int ntype = nd.type;
        switch (ntype)
        {
        case 0:
            printf(" %s \n", nd.myString);
            break;
        case 1:
            printf(" %d \n", nd.integerVal);
            break;
        case 2:
            printf(" < %d ,%d > \n", nd.pairA, nd.pairB);
            break;
        case 3:
            printf(" %.6f \n", nd.floatVal);
            break;
        default:
            printf("Error in line %d \n", __LINE__);
            break;
        }
        outputIndex = (outputIndex + 1) % MAX_STACK_HEIGHT;
    }
}

void push(node mainStack[], node val)
{
    if((inputIndex + 1) % MAX_STACK_HEIGHT == outputIndex){
        puts("The Queue is full");
    }else{
        mainStack[inputIndex] = val;
        inputIndex = (inputIndex + 1) % MAX_STACK_HEIGHT;

    }
}

int main()
{
    int condition = 1;
    int getanswer = 0;
    node mainStack[MAX_STACK_HEIGHT];
    inputIndex = 0;
    outputIndex = 0;

    while(condition)
    {
        printf("1. Push New Element. \n");
        printf("2. Pop. \n");
        printf("0. Exit. \n");

        scanf(" %d", &getanswer);

        switch (getanswer)
        {
        case 1:
            printf("0. String \n");
            printf("1. Integer. \n");
            printf("2. Pair. \n");
            printf("3. Float. \n");

            int getanswer = 0;
            scanf(" %d", &getanswer);
            
            node NewND = newNode(getanswer);  
            char newS[30];
            int newInt;
            int Pair_First;
            int Pair_Second;
            float newF;

            switch (getanswer)
            {
            case 0:

                scanf(" %s", newS);
                NewND.myString = newS;
                push(mainStack, NewND);
                break;

            case 1:
                
                scanf(" %d", &newInt);
                NewND.integerVal = newInt;
                push(mainStack, NewND);
                break;

            case 2:

                scanf(" %d", &Pair_First);
                scanf(" %d", &Pair_Second);
                NewND.pairA = Pair_First;
                NewND.pairB = Pair_Second;
                push(mainStack, NewND);
                break;

            case 3:
            
                scanf(" %f", &newF);
                NewND.floatVal = newF;
                push(mainStack, NewND);
                break;

            default:
                break;
            }

            break;

        case 2:

            pop(mainStack);
            break;
        case 0:

            condition = 0;
            break; 

        default:
            break;
        }
    }
    return 0;
}