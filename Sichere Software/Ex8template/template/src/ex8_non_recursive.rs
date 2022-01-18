#[derive(Eq, PartialEq, Debug)]
struct Node<K: Ord, V> {
    key: K,
    value: V,
    left: BST<K, V>,
    right: BST<K, V>
}

#[derive(Eq, PartialEq, Debug)]
pub struct BST<K: Ord, V> {
    node: Option<Box<Node<K, V>>>
}

impl <K: Ord, V> BST<K, V> {
    pub fn empty() -> BST<K, V> {
        BST {node: None}
    }

    pub fn new(key: K, value: V, left: BST<K, V>, right: BST<K, V>) -> BST<K, V> {
        BST {
            node: Some(Box::new(Node {
                key: key,
                value: value,
                left: left,
                right: right
            }))
        }
    }

    pub fn singleton(key: K, value: V) -> BST<K, V> {
        BST::new(key, value, BST::empty(), BST::empty())
    }

    pub fn contains(&self, key: K) -> bool {
        self.lookup(key).is_some()
    }

    pub fn is_empty(&self) -> bool {
        self.node.is_none()
    }

    pub fn insert(&mut self, key: K, value: V) {
        let mut current = self;
        loop {
            if current.is_empty() {
                *current = BST::new(key, value, BST::empty(), BST::empty());
                break;
            }
            match current.node {
                Some(ref mut a) 
                    =>
                    if a.key == key {
                        a.value = value;
                        break;
                    } else if a.key > key {
                        current = &mut a.left;
                    } else {
                        current = &mut a.right;
                    }
                _ => break
            }
        }
    }
    
    pub fn lookup(&self, key: K) -> Option<&V> {
        let mut current = self;
        loop {
            match current.node {
                None => { return None; }
                Some(ref a)
                    =>
                    if a.key == key { 
                        return Some(&(a.value)); 
                    }
                    else if a.key > key { 
                        current = &a.left; 
                    } else { 
                        current = &a.right;
                    }
            }
        }
    }
}

// Prevent stack overflows when deallocating large BSTs
impl <K: Ord, V> Drop for BST<K, V> {
    fn drop(&mut self) {
        let mut curr = None;
        ::std::mem::swap(&mut curr, &mut self.node);
        let mut stack: Vec<Box<Node<K, V>>> = vec!();
        while let Some(mut node) = curr {
            if node.left.node.is_some() {
                let mut tmp = None;
                ::std::mem::swap(&mut tmp, &mut node.left.node);
                stack.push(tmp.unwrap());
            }
            if node.right.node.is_some() {
                let mut tmp = None;
                ::std::mem::swap(&mut tmp, &mut node.right.node);
                stack.push(tmp.unwrap());
            }
            curr = stack.pop();
        }
    }
}

#[cfg(test)]
mod tests {
    use crate::ex8_non_recursive::*;

    fn make_tree1() -> BST<&'static str, i32> {
        BST {
            node: Some(Box::new(Node {
                key: "test",
                value: 23,
                left: BST::singleton("abc", 13),
                right: BST::singleton("xyz", 42)
            }))
        }
    }

    fn make_tree2() -> BST<&'static str, i32> {
        BST {
            node: Some(Box::new(Node {
                key: "test",
                value: 42,
                left: BST::singleton("abc", 23),
                right: BST::singleton("xyz", 13)
            }))
        }
    }

    #[test]
    fn empty_tree() {
        let tree = BST::<&'static str, i32>::empty();
        assert!(tree.is_empty());
        assert!(!tree.contains("test"));
    }

    #[test]
    fn insert() {
        let mut tree = BST::empty();
        tree.insert("test", 23);
        tree.insert("abc", 13);
        tree.insert("xyz", 42);
        assert_eq!(tree, make_tree1());
    }

    #[test]
    fn insert_override() {
        let mut tree = make_tree1();
        tree.insert("test", 42);
        tree.insert("abc", 23);
        tree.insert("xyz", 13);
        assert_eq!(tree, make_tree2());
    }

    #[test]
    fn lookup() {
        let tree = make_tree1();
        assert!(tree.contains("test"));
        assert_eq!(tree.lookup("test"), Some(&23));
        assert_eq!(tree.lookup("abc"), Some(&13));
        assert_eq!(tree.lookup("xyz"), Some(&42));
    }

    #[test]
    fn large_tree() {
        let mut tree = BST::empty();
        for i in 1..10000000 {
            tree = BST::new(i, i+1, tree, BST::empty());
        }
        assert_eq!(tree.lookup(1), Some(&2));
        assert_eq!(tree.lookup(9999999), Some(&10000000));
        assert!(!tree.contains(0));
        assert!(!tree.contains(10000000));
        tree.insert(10000000, 42);
        assert_eq!(tree.lookup(10000000), Some(&42));
    }
}
