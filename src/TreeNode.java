import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    int cityId;  // Şehri tanımlayan ID
    String city;  // Şehir adı
    List<TreeNode> children;
    int depth;  // Şehirlerin derinliğini tutan alan
    int deliveryTime; // Teslimat süresi (derinlikle ilişkili)
    int cargoCount;


    public TreeNode(int cityId, String city, int depth) {
        this.cityId = cityId;  // ID'yi atıyoruz
        this.city = city;
        this.children = new ArrayList<>();
        this.depth = depth;
        this.deliveryTime = depth;
        this.cargoCount = 0;
    }

    // Derinlik hesaplamak için bir yöntem
    public int calculateDepth(TreeNode node, int currentDepth) {
        int maxDepth = currentDepth;
        for (TreeNode child : node.children) {
            int childDepth = calculateDepth(child, currentDepth + 1);
            maxDepth = Math.max(maxDepth, childDepth);
        }
        return maxDepth;
    }
}
