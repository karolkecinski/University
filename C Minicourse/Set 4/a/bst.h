
#ifndef BST_H
#define BST_H

struct node {
int value;
struct node* left;
struct node* right;
};




struct node* search_tree( struct node* tree, int val);
struct node* add_to_tree (struct node* tree, struct node* new_elem);
int size_of_tree (struct node* tree);
void inorder (struct node* tree);

#endif
