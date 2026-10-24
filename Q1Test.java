import java.util.*;


public class Q1Test {
     public static void main(String[] args) {

          String result, traversal1, traversal2, traversal3;

          traversal1 = "1-2";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "3-2-1";
          traversal3 = "2-3-1";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "3-1-2-5-4";
          traversal2 = "2-1-4-5-3";
          traversal3 = "1-2-3-4-5";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "10-20-30-40-50";
          traversal2 = "20-10-30-40-50";
          traversal3 = "10-50-40-30-20";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();
     } 

     private static class Node {
          int value;
          Node left;
          Node right;

          Node(int value) {
               this.value = value;
          }
     }

     private static Node insert(Node root, int value) {
          if (root == null) {
               return new Node(value);
          }

          if (value < root.value) {
               root.left = insert(root.left, value);
          } else if (value > root.value) {
               root.right = insert(root.right, value);
          }

          return root;
     }

     private static boolean isAscending(String[] values) {
          for (int i = 1; i < values.length; i++) {
               int previous = Integer.parseInt(values[i - 1]);
               int current = Integer.parseInt(values[i]);

               if (previous >= current) {
                    return false;
               }
          }
          return true;
     }

     private static boolean sameValues(String[] first, String[] second) {
          Set<Integer> set1 = new HashSet<>();
          Set<Integer> set2 = new HashSet<>();

          for (String value : first) {
               set1.add(Integer.parseInt(value));
          }

          for (String value : second) {
               set2.add(Integer.parseInt(value));
          }

          return set1.equals(set2);
     }

     private static Node buildTree(String[] values) {
          Node root = null;

          for (String value : values) {
               root = insert(root, Integer.parseInt(value));
          }

          return root;
     }

     private static String preorder(Node root) {
          StringBuilder result = new StringBuilder();
          preorderSubtree(root, result);
          return result.toString();
     }

     private static void preorderSubtree(Node node, StringBuilder result) {
          if (node == null) {
               return;
          }

          // visit the current node first
          if (result.length() > 0) {
               result.append("-");
          }
          result.append(node.value);

          //  visit the left and right subtrees
          preorderSubtree(node.left, result);
          preorderSubtree(node.right, result);
     }

     private static String postorder(Node root) {
          StringBuilder result = new StringBuilder();
          postorderSubtree(root, result);
          return result.toString();
     }

     private static void postorderSubtree(Node node, StringBuilder result) {
          if (node == null) {
               return;
          }

          // visit the left and right subtrees first
          postorderSubtree(node.left, result);
          postorderSubtree(node.right, result);

          // visit the current node last
          if (result.length() > 0) {
               result.append("-");
          }
          result.append(node.value);
     }


      // write your codes here
     public static String verify(String traversal1, String traversal2, String traversal3) {

          String[] traversals = {traversal1, traversal2, traversal3};

          String[][] values = {traversal1.split("-"), traversal2.split("-"), traversal3.split("-")};

          // check that the traversals contain the same number of nodes
          if (values[0].length != values[1].length || values[0].length != values[2].length) {
               return "Invalid traversals";
          }

          // check that they contain the same values
          if (!sameValues(values[0], values[1]) || !sameValues(values[0], values[2])) {
               return "Invalid traversals";
          }

          // try the inorder first 
          for (int in = 0; in < 3; in++) {
               if (!isAscending(values[in])) {
                    continue;
               }

               // now try preorder
               for (int pre = 0; pre < 3; pre++) {
                    // keep finding until
                    if (pre == in) {
                         continue;
                    }

                    int post = 3 - in - pre;

                    Node root = buildTree(values[pre]);

                    // Both traversals must match the tree we built
                    if (preorder(root).equals(traversals[pre]) && postorder(root).equals(traversals[post])) {

                         String[] labels = new String[3];
                         labels[in] = "Inorder";
                         labels[pre] = "Preorder";
                         labels[post] = "Postorder";

                         return "Traversal 1 - " + labels[0] + ", Traversal 2 - " + labels[1] + ", Traversal 3 - " + labels[2];
                    }
               }
          }

          return "Invalid traversals";
     }
}


