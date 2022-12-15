package Taqueria;

import Taqueria.Order.Order;
import Taqueria.TacoInterface.Taco;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class KitchenGUI extends JFrame{
        Order order = new Order();
        JLabel tacoTitleLabel = new JLabel("\uD83C\uDF43 Taqueria Piña's beställningar \uD83C\uDF43");
        JPanel panel = new JPanel();
        TextArea orderText = new TextArea();
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
            add(panel);
            pack();
            setSize(800, 500);
            setVisible(true);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            //scrollpane på botten...?
        }
}
