package Post;

import java.util.Set;
import java.util.TreeSet;

public class Test {
    public static void main(String[] args) {
        Set<Integer> set = new TreeSet<>(); // Sorted set

        set.add(3);
        set.add(1);
        set.add(5);

        System.out.println("Original set: " + set); // Output: Original set: [1, 3, 5]

        // Update an element's value (this doesn't automatically change its position in the set)
        // You would need to remove and re-add the element to see the updated order
        set.remove(3);
        System.out.println("Original set: " + set);
        set.add(2);
        System.out.println("Updated set: " + set); // Output: Updated set: [1, 2, 5]
    }
}
