import javax.swing.*;
import java.awt.*;

class CityTreeVisualizationPanel extends JPanel {
    private TreeNode root;

    public CityTreeVisualizationPanel(TreeNode root) {
        this.root = root;
        setPreferredSize(new Dimension(800, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (root != null) {
            // Çizim fonksiyonunu çağır
            drawTree(g, root, getWidth() / 2, 50, getWidth() / 4, 80);  // yOffset 80 ile her seviyede aşağıya doğru mesafe artırıyoruz
        }
    }



    private void drawTree(Graphics g, TreeNode node, int x, int y, int xOffset, int yOffset) {
        // Düğümü çiz (Pembe)
        g.setColor(Color.BLACK);
        g.fillOval(x - 5, y - 5, 10, 10);  // Şehir noktası

        // Şehir adını ve kargo sayısını yazdır (Siyah)
        g.setColor(Color.BLACK);  // Yazı rengi siyah
        String cityLabel = node.city + " (Kargo: " + node.cargoCount + ")";
        g.drawString(cityLabel, x - cityLabel.length() * 3, y + 20); // Şehir ve kargo sayısını yazdır

        // Çocuk düğümlerini çiz (Mavi çizgiler)
        int childY = y + yOffset;  // Çocuk düğümünün Y konumunu, yOffset ile ayarlıyoruz.
        int numChildren = node.children.size();

        // Çocuklar arasındaki mesafeyi ayarlamak için xOffset'i kullanarak her seviyede daha geniş mesafe
        int childXBase = x - (numChildren - 1) * xOffset / 2;  // Çocuklar arasında daha geniş mesafe sağlamak için base hesapla

        // Çocuk düğümleri arasında daha geniş mesafe sağlamak için, xOffset'i azaltıyoruz
        for (int i = 0; i < numChildren; i++) {
            TreeNode child = node.children.get(i);
            int childX = childXBase + i * xOffset;

            // Çizgiyi mavi renkte çiz
            g.setColor(Color.BLACK);  // Çizgi rengi mavi
            g.drawLine(x, y, childX, childY);  // Çizgi

            // Çocuğu çizmek için recursive çağrı
            drawTree(g, child, childX, childY, xOffset / 2, yOffset + 70);  // Her seviyede daha sıkışık yerleşim
        }
    }



}
