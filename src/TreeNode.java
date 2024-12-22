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
        this.deliveryTime = depth; // Derinlik ile teslimat süresini ilişkilendiriyoruz
        this.cargoCount = 0;
    }


    // Derinlik hesaplamak için recursive fonksiyon
    public int calculateDepth(TreeNode node, int currentDepth) {
        int maxDepth = currentDepth;
        for (TreeNode child : node.children) {
            int childDepth = calculateDepth(child, currentDepth + 1);
            maxDepth = Math.max(maxDepth, childDepth);
        }
        return maxDepth;
    }

    // Şehir ekleme fonksiyonu
    public void addChild(TreeNode child) {
        this.children.add(child);
    }

    // Diğer getter ve setter metotları
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public int getDepth() {
        return depth;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public int getCargoCount() {
        return cargoCount;
    }

    public void setCargoCount(int cargoCount) {
        this.cargoCount = cargoCount;
    }

}