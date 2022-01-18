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

    pub fn size(&self) -> usize {
        match self.node {
            None           => 0,
            Some(ref a) => 1 + a.left.size() + a.right.size()
        }
    }

    pub fn is_empty(&self) -> bool {
        self.node.is_none()
    }

    pub fn insert(&mut self, key: K, value: V) {
        match self.node {
            None        => *self = BST::new(key, value, BST::empty(), BST::empty()),
            Some(ref mut a) 
                =>
                if a.key == key {
                    a.value = value;
                } else if a.key > key {
                    a.left.insert(key, value);
                } else {
                    a.right.insert(key, value);
                } 
        }
    }

    pub fn lookup(&self, key: K) -> Option<&V> {
        match self.node {
            None => { return None; }
            Some(ref a)
                =>
                if a.key == key { return Some(&(a.value)); }
                else if a.key > key { a.left.lookup(key) }
                else { a.right.lookup(key) }
        }
    }
}

#[cfg(test)]
mod tests {
    use crate::ex8_recursive::*;

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
    fn size() {
        let tree = make_tree1();
        assert_eq!(tree.size(), 3);
        assert!(!tree.is_empty());
    }

    #[test]
    fn empty_tree() {
        let tree = BST::<&'static str, i32>::empty();
        assert_eq!(tree.size(), 0);
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
}
