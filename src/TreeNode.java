import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    String city;
    List<TreeNode> children;
    int depth;  // Şehirlerin derinliğini tutan alan
    int deliveryTime; // Teslimat süresi (derinlikle ilişkili)
    int cargoCount;

    public TreeNode(String city, int depth) {
        this.city = city;
        this.children = new ArrayList<>();
        this.depth = depth;
        this.deliveryTime = depth;
        this.cargoCount = 0;
    }
    //derinilik hesaplıyoruz
    public int calculateDepth(TreeNode node, int currentDepth) {
        int maxDepth = currentDepth;
        for (TreeNode child : node.children) {
            int childDepth = calculateDepth(child, currentDepth + 1);
            maxDepth = Math.max(maxDepth, childDepth);
        }
        return maxDepth;
    }



}