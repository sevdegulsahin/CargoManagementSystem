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
        setBackground(Color.decode("#012a4a"));

        if (root != null) {
            drawTree(g, root, getWidth() / 2, 50, getWidth() / 4, 80);
        }
    }

    private void drawTree(Graphics g, TreeNode node, int x, int y, int xOffset, int yOffset) {
        g.setColor(Color.PINK);
        g.fillOval(x - 5, y - 5, 10, 10);

        g.setColor(Color.WHITE);
        String cityLabel = node.city + " (Kargo: " + node.cargoCount + ")";
        g.drawString(cityLabel, x - cityLabel.length() * 3, y + 20); //

        int childY = y + yOffset;
        int numChildren = node.children.size();

        int childXBase = x - (numChildren - 1) * xOffset / 2;

        for (int i = 0; i < numChildren; i++) {
            TreeNode child = node.children.get(i);
            int childX = childXBase + i * xOffset;

            g.setColor(Color.WHITE);
            g.drawLine(x, y, childX, childY);

            // Çocuğu çizmek için recursive çağrı
            drawTree(g, child, childX, childY, xOffset / 2, yOffset + 70);
        }
    }
}
