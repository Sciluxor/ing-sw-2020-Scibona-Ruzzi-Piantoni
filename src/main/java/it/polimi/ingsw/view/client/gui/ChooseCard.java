package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButton;
import static it.polimi.ingsw.view.client.gui.ChallengerChoiseCards.returnGodChoosen;

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
        ConfirmButton confirm = new ConfirmButton();
        List<JButton> godChoosen = returnGodChoosen();

        if(numberOfPanel == 3 || numberOfPanel == 2) {

            choose.setBounds(frameSize.width * 35/100, frameSize.height * 10/100, frameSize.width * 30/100, frameSize.height * 10/100);
            add(choose);
            confirm.addActionListener(new Gui.ChangePanel());
            add(confirm);

            if (numberOfPanel == 3){
                godChoosen.get(0).setBounds(x, y, cardSize.width, cardSize.height);
                this.add(godChoosen.get(0));

                godChoosen.get(1).setBounds(x + frameSize.width * 12/100, y, cardSize.width, cardSize.height);
                this.add(godChoosen.get(1));

                godChoosen.get(2).setBounds(x + frameSize.width * 24/100, y, cardSize.width, cardSize.height);
                this.add(godChoosen.get(2));
            }

            if (numberOfPanel == 2){
                godChoosen.get(0).setBounds(x + frameSize.width * 6/100, y, cardSize.width, cardSize.height);
                this.add(godChoosen.get(0));

                godChoosen.get(1).setBounds(x + frameSize.width * 18/100, y, cardSize.width, cardSize.height);
                this.add(godChoosen.get(1));
            }
        }

        else {

            if (numberOfPanel == 1){

                choise.setBounds(frameSize.width * 35/100, frameSize.height * 10/100, frameSize.width * 30/100, frameSize.height * 10/100);
                add(choise);

                godChoosen.get(0).setBounds((int) (frameSize.width * 45.5/100), y, cardSize.width, cardSize.height);
                this.add(godChoosen.get(0));
            }
            else{
                wait.setBounds(frameSize.width * 35/100, frameSize.height * 10/100, frameSize.width * 30/100, frameSize.height * 10/100);
                add(wait);
            }
        }
        JButton back = backgroundButton();
        add(back);
    }
}
