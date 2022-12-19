package Taqueria;

import Taqueria.Order.Order;
import javax.swing.*;
import java.awt.*;

import static javax.swing.ScrollPaneConstants.*;

public class KitchenGUI extends JFrame{

        JLabel tacoTitleLabel = new JLabel("\uD83C\uDF43 Taqueria Piña's beställningar \uD83C\uDF43");
        JPanel panel = new JPanel();
        TextArea orderText = new TextArea(20,50);
        JScrollPane scrollPane = new JScrollPane(orderText);
        Font font = new Font("times new roman", Font.PLAIN,25);

        KitchenGUI(){
            displayorderListORDERED();
        }

        public void displayorderListORDERED(){
            setTitle("\uD83C\uDF43 Taqueria Piña \uD83C\uDF43");
            panel.setLayout(new BorderLayout());
            panel.add(tacoTitleLabel, BorderLayout.NORTH);
            panel.add(scrollPane, BorderLayout.CENTER);
            orderText.setEditable(false);
            orderText.setFont(font);
            scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
            add(panel);
            pack();

            setVisible(true);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

        }
}
