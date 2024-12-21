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
    private void initializeCityTree() {
        TreeNode cityTreeRoot = new TreeNode("Istanbul", 1); // Root şehri derinliği 0

        // Birinci seviye şehirler
        TreeNode ankara = new TreeNode("Ankara", 2);
        TreeNode kastamonu = new TreeNode("Kastamonu", 2);
        TreeNode bursa = new TreeNode("Bursa", 2);

        // Ankara'nın alt şehirleri
        TreeNode mersin = new TreeNode("Mersin", 4);
        TreeNode kayseri = new TreeNode("Kayseri", 4);
        ankara.children.add(mersin);
        ankara.children.add(kayseri);

        // Izmir'in alt şehirleri
        TreeNode tokat = new TreeNode("Tokat", 4);
        TreeNode giresun = new TreeNode("Giresun", 4);
        kastamonu.children.add(tokat);
        kastamonu.children.add(giresun);

        // Bursa'nın alt şehirleri
        TreeNode i̇zmir = new TreeNode("İzmir", 4);
        TreeNode canakkale = new TreeNode("Çanakkale", 4);
        bursa.children.add(i̇zmir);
        bursa.children.add(canakkale);

        // Daha alt seviyedeki şehirler
        mersin.children.add(new TreeNode("K.Maraş", 7));
        mersin.children.add(new TreeNode("Hatay", 7));

        kayseri.children.add(new TreeNode("Malatya", 7));
        kayseri.children.add(new TreeNode("Elazığ", 7));

        tokat.children.add(new TreeNode("Erzincan", 7));
        tokat.children.add(new TreeNode("Sivas", 7));

        giresun.children.add(new TreeNode("Erzurum", 7));
        giresun.children.add(new TreeNode("Rize", 7));

        i̇zmir.children.add(new TreeNode("Muğla", 7));
        i̇zmir.children.add(new TreeNode("Antalya", 7));

        // Şehirleri kök düğüme ekleme
        cityTreeRoot.children.add(ankara);
        cityTreeRoot.children.add(kastamonu);
        cityTreeRoot.children.add(bursa);

        int maxDepth = cityTreeRoot.calculateDepth(cityTreeRoot, 0);
        System.out.println("Ağaç yapısının derinliği: " + maxDepth);
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