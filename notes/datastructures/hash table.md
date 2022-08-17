
# Hash Tables

> Hash tables are data structures that maps a key to value in by a technique called hashing.

## Notes :
- if H(x) = H(y) then x and y might be equal, but if **H(x) != H(y) then x and y must be not equal**
- Hash function should be deterministic so every time I calculate a **hash for a key it must gives me the save value**
-  when we try to implement a hash function we should try hard to make it uniform to **reduce the collisions**
- Inorder to make the hash function **deterministic** we need to make the **key immutable** to make sure that it's value won't be changed if it is hased again
-  There are two types of collision in hash table:
    1. Separate chaining
    2. open addressing
- Inorder to acheive constant time hash function operations you need to have a good uniform hash function

## Complexity

| Operation | Avg | Worst | 
| :--- | :---: | ---:
| Insertion | O(1) | O(n) |
| Removal | O(1) | O(n) |
| Search | O(1) | O(n) | 
```