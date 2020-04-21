package it.polimi.ingsw.view.Client.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static it.polimi.ingsw.view.Client.GUI.Gui.BackgroundButton;
import static it.polimi.ingsw.view.Client.GUI.Gui.ConfirmButtonCreate;

public class ChooseCard extends  JPanel{
    Dimension frameSize = new Dimension();

    public ChooseCard(Dimension screen, Dimension frame, Integer numberOfPanel) throws IOException {
        frameSize.setSize(frame);
        setPreferredSize(frameSize);
        setLayout(null);

        int x = (int) (frameSize.width * 33.5/100);
        int y = (int) screen.getHeight() * 35/100;
        Dimension cardSize = new Dimension();
        cardSize.setSize((int) (frameSize.getWidth() * 9/100), (int) (frameSize.getHeight() * 23.15/100));

        JLabel choose = ImageHandler.setImage("src/main/resources/Graphics/Texts/choose_your_god.png", 100, 100, frameSize.width * 30/100, frameSize.height * 10/100);
        JLabel choise = ImageHandler.setImage("src/main/resources/Graphics/Texts/this_is_your_god.png", 100, 100, frameSize.width * 30/100, frameSize.height * 10/100);
        JLabel wait = ImageHandler.setImage("src/main/resources/Graphics/Texts/waiting_for_other_players_choice.png", 100, 100, frameSize.width * 30/100, frameSize.height * 10/100);
        JButton confirm = ConfirmButtonCreate();
        JButton button1 = new JButton("First");
        JButton button2 = new JButton("Second");
        JButton button3 = new JButton("Third");

        if(numberOfPanel == 3 || numberOfPanel == 2) {

            choose.setBounds(frameSize.width * 35/100, frameSize.height * 10/100, frameSize.width * 30/100, frameSize.height * 10/100);
            add(choose);

            add(confirm);

            if (numberOfPanel == 3){
                button1.setBounds(x, y, cardSize.width, cardSize.height);
                this.add(button1);

                button2.setBounds(x + frameSize.width * 12/100, y, cardSize.width, cardSize.height);
                this.add(button2);

                button3.setBounds(x + frameSize.width * 24/100, y, cardSize.width, cardSize.height);
                this.add(button3);
            }

            if (numberOfPanel == 2){
                button1.setBounds((int) (x + frameSize.width * 6/100), y, cardSize.width, cardSize.height);
                this.add(button1);

                button2.setBounds((int) (x + frameSize.width * 18/100), y, cardSize.width, cardSize.height);
                this.add(button2);
            }
        }

        else {

            if (numberOfPanel == 1){

                choise.setBounds(frameSize.width * 35/100, frameSize.height * 10/100, frameSize.width * 30/100, frameSize.height * 10/100);
                add(choise);

                button1.setBounds((int) (frameSize.width * 45.5/100), y, cardSize.width, cardSize.height);
                this.add(button1);
            }
            else{
                wait.setBounds(frameSize.width * 35/100, frameSize.height * 10/100, frameSize.width * 30/100, frameSize.height * 10/100);
                add(wait);
            }
        }
        JButton back = BackgroundButton();
        add(back);
    }
}
