#ifndef LIFO_H
#define LIFO_H

typedef struct node{
int data_type;
size_t allocated_size;
void* data;
struct node* next_node;
}mynode;

#endif