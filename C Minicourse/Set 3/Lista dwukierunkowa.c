#include <stdio.h>
#include <stdlib.h>
 
typedef struct ListElement {
    int data;
    struct ListElement * previous;
    struct ListElement * next;
} ListElement_type;
 
void push_front(ListElement_type **First, int n)
{
    if(*First==NULL) {
    	*First = (ListElement_type *)malloc(sizeof(ListElement_type));
   		(*First)->data = n;
   		(*First)->previous=NULL;
    	(*First)->next = NULL;
	} else {
		ListElement_type *Move;
    	        Move=(ListElement_type *)malloc(sizeof(ListElement_type));
    	        Move->data=n;
    	        Move->previous=NULL;
    	        Move->next=(*First);
    	        (*First)->previous=Move;
    	        *First=Move;
	}
}
 
void push_back(ListElement_type **First, int n)
{
	if(*First==NULL)
	{
		*First = (ListElement_type *)malloc(sizeof(ListElement_type));
   		(*First)->data = n;
   		(*First)->previous = NULL;
    	(*First)->next = NULL;
	} else {
        
		ListElement_type *Move=*First;
	
	    while (Move->next != NULL) 
        {
	        Move = Move->next;
	    }
	
	    Move->next = (ListElement_type *)malloc(sizeof(ListElement_type));
	    Move->next->data = n;
	    Move->next->previous=Move;
	    Move->next->next = NULL;	
	}
}
 
void pop_front(ListElement_type **First)
{
    if (*First!=NULL) 
    {
        printf("%i\n", (*First)->data); 

    	if((*First)->next==NULL) 
        {
    		*First=NULL;
		} else {
			ListElement_type *Move;
			Move=(*First)->next;
   			free(*First);
   			*First=Move;
   	 		(*First)->previous=NULL;
		}
	}
    else
    {
        printf("LISTA PUSTA\n");
    }
    
}
 
void pop_back(ListElement_type **First)
{
    if(*First!=NULL)
    {
        if((*First)->next==NULL)
        {	
            printf("%i\n", (*First)->data); 
            *First=NULL;	
        } else {
            ListElement_type *Move=*First;
            while (Move->next->next!= NULL) {
            Move = Move->next;
            }
            printf("%i\n", Move->next->data);
            free(Move->next);
            Move->next=NULL;
        }
    }
    else
    {
        printf("LISTA PUSTA\n");
    }
}
 
int main()
{
    ListElement_type *First;
    First = (ListElement_type *)malloc(sizeof(ListElement_type));
    First = NULL;
 
    int option=-1;
    int n=0;
    int a;
    scanf("%i", &a);
 
    for(int x=0; x<a; x++)
    {
        scanf("%i", &option);
    
        switch (option)
        {
            case 0:
                return 0;
                break;
            case 1:
                scanf("%i", &n);
                push_front(&First, n);
                break;
            case 2:
                scanf("%i", &n);
                push_back(&First, n);
                break;
            case 3:
                pop_front(&First);
                break;
            case 4:
                pop_back(&First);
                break;    
            default:
                return 0;
                break;
        }
    }
    return 0;
}