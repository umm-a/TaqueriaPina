package Taqueria;

import javax.swing.*;
import java.awt.*;

public class KitchenGUI extends JFrame{
        JLabel tacoTitleLabel = new JLabel("\uD83C\uDF43 TaqueriaPiña's beställningar \uD83C\uDF43");
        JPanel panel = new JPanel();
        TextArea orderText = new TextArea();
        JScrollPane scrollPane = new JScrollPane(orderText);

        KitchenGUI(){
            displayActiveOrderList();
        }

        public void displayActiveOrderList(){
            setTitle("\uD83C\uDF43 TaqueriaPiña \uD83C\uDF43");
            panel.setLayout(new BorderLayout());
            panel.add(tacoTitleLabel, BorderLayout.NORTH);
            panel.add(scrollPane, BorderLayout.CENTER);
            orderText.setEditable(false);
            add(panel);
            pack();
            setSize(400, 400);
            setVisible(true);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            orderText.append("testing testing\n");
            orderText.append("testing2 testing2\n");
            //scrollpane på botten...?

     /*   for (Order o: activeOrderList) {
     ->ta fram tacolist för vardera beställning
        ArrayList<Taco> tempList = o.getTacoList();
            for (Taco p: tempList){ todo något sådant
            -> taco get description
            -> Skriv ut dessa Strings i rutan
            orderText.append(p.getDescription);
            }
        }*/
        }



        public static void main(String[] args) {
            KitchenGUI k = new KitchenGUI();
        }
}
