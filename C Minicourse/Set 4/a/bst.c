#include <stdlib.h>
#include <stdio.h>
#include "bst.h"



struct node* search_tree( struct node* tree, int val)
{
    while (tree != NULL && tree->value != val)
    {

        if (tree->value < val)
        {

             tree = tree->right;
        }
        else
        {

            tree = tree->left;
        }

    }
    return tree;

}

struct node* add_to_tree (struct node* tree, struct node* new_elem)
{

    if(tree == NULL)
    {
        return new_elem;
    }

    if (new_elem->value <= tree->value)
    {
        tree->left = add_to_tree(tree->left, new_elem);
    }
    else if (new_elem->value > tree->value)
    {
       tree->right = add_to_tree(tree->right, new_elem);
    }

    return tree;
}

int size_of_tree (struct node* tree)
{

    if(tree==NULL)
    {
       return 0;
    }


    int l = size_of_tree(tree->left);
    int r = size_of_tree(tree->right);
    if (l<r)
    {
        return r+1;
    }
    return l+1;


}

void inorder (struct node* tree)
{

    if (tree == NULL)
    {
       return;
    }
    else
    {
        inorder(tree->left);
        printf("%d\n" ,tree->value);
        inorder(tree->right);
    }


}

































