package it.polimi.ingsw.view.Client.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ChooseCard extends  JPanel{
    Dimension frameSize = new Dimension();

    public ChooseCard(Dimension screen, Dimension frame, Integer numberOfPanel, JLabel background) throws IOException {
        frameSize.setSize(frame);
        setPreferredSize(frameSize);
        setLayout(null);

        JButton back = new JButton();
        back.setIcon(background.getIcon());
        back.setBounds(0, 0, frameSize.width, frameSize.height);
        back.setOpaque(false);
        back.setContentAreaFilled(false);
        back.setBorderPainted(false);

        int x = (int) (screen.getWidth() * 33.5/100);
        int y = (int) screen.getHeight() * 35/100;
        Dimension cardSize = new Dimension();
        cardSize.setSize(screen.getWidth() * 7/100, screen.getHeight() * 14/100);

        JLabel choose = ImageHandler.setImage("src/main/resources/Graphics/Texts/choose_your_god.png", 100, 100, frameSize.width * 30/100, frameSize.height * 10/100);
        JLabel choise = new JLabel("This is your God Power");
        JLabel wait = new JLabel("Waiting for other players choise");
        JButton confirm = new JButton();
        JButton button1 = new JButton("First");
        JButton button2 = new JButton("Second");
        JButton button3 = new JButton("Third");

        if(numberOfPanel == 3 || numberOfPanel == 2) {

            choose.setBounds(frameSize.width * 35/100, frameSize.height * 10/100, frameSize.width * 30/100, frameSize.height * 10/100);
            add(choose);

            confirm.setBounds((int) (frameSize.width * 43.5/100), (int) (frameSize.height * 79.5/100), (int) (frameSize.width * 13/100), (int) (frameSize.height * 5/100));
            confirm.setOpaque(false);
            confirm.setContentAreaFilled(false);
            confirm.setFocusPainted(false);
            confirm.setBorderPainted(false);
            confirm.setIcon(Gui.lconfirm.getIcon());
            confirm.addMouseListener(new Gui.ConfirmButtonPress());
            add(confirm);
            confirm.addActionListener(new Gui.ChangePanel());

            if (numberOfPanel == 3){
                button1.setBounds(x, y, cardSize.width, cardSize.height);
                this.add(button1);

                button2.setBounds(x + screen.width * 10/100, y, cardSize.width, cardSize.height);
                this.add(button2);

                button3.setBounds(x + screen.width * 20/100, y, cardSize.width, cardSize.height);
                this.add(button3);
            }

            if (numberOfPanel == 2){
                button1.setBounds(x + screen.width * 5/100, y, cardSize.width, cardSize.height);
                this.add(button1);

                button2.setBounds(x + screen.width * 15/100, y, cardSize.width, cardSize.height);
                this.add(button2);
            }


        }

        else {

            if (numberOfPanel == 1){

                choise.setBounds(frameSize.width * 50/100 - 70, frameSize.height * 20/100 - 50, 150, 100);
                add(choise);

                button1.setBounds(frameSize.width * 50/100 - cardSize.width/2, y, cardSize.width, cardSize.height);
                this.add(button1);
            }
            else{
                wait.setBounds(frameSize.width * 50/100 - 100, frameSize.height * 50/100 - 50, 200, 100);
                add(wait);
            }

        }
        add(back);
    }
}
