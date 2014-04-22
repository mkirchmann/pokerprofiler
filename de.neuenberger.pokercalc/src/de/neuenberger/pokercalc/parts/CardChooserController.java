// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 17:36:38
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CardChooserController.java

package de.neuenberger.pokercalc.parts;

import de.neuenberger.poker.common.model.Card;
import de.neuenberger.poker.common.model.Deck;
import de.neuenberger.poker.common.model.GameDescription;
import de.neuenberger.poker.common.parts.IController;
import de.neuenberger.poker.common.ui.util.CardChooser;
import de.neuenberger.poker.common.ui.util.CardLabel;
import de.neuenberger.pokercalc.model.*;
import de.neuenberger.pokercalc.model.util.ModelFromStringToolkit;
import de.neuenberger.pokercalc.ui.CardChooserPanel;
import de.neuenberger.pokercalc.ui.PlayerCardSelector;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

// Referenced classes of package de.neuenberger.pokercalc.parts:
//            IController

public class CardChooserController
    implements IController, ActionListener
{

    public CardChooserController()
    {
        mainPanel = new JPanel();
        cardChooserPanel = new CardChooserPanel();
        gameDescription = new GameDescription();
        deck = Deck.getInstance();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(cardChooserPanel, "Center");
        setup();
    }

    protected void setup()
    {
        cardChooserPanel.getCardChooserFlop()[0].addActionListener(this);
        cardChooserPanel.getCardChooserFlop()[1].addActionListener(this);
        cardChooserPanel.getCardChooserFlop()[2].addActionListener(this);
        cardChooserPanel.getCardChooserTurn().addActionListener(this);
        cardChooserPanel.getCardChooserRiver().addActionListener(this);
        cardChooserPanel.getPlayerCardSelector().addActionListener(this);
        cardChooserPanel.getPasteSelection().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0)
            {
                push_pasteSelection();
            }

        });
    }

    private void push_pasteSelection()
    {
        GameDescription gd = ModelFromStringToolkit.getGameDescription(cardChooserPanel);
        if(gd != null)
        {
            Card pCard = gd.getPlayerCards()[0][0];
            if(pCard != null)
            {
                cardChooserPanel.getPlayerCardSelector().getCardChooserPocket()[0].setSelectedColor(pCard.getColor());
                cardChooserPanel.getPlayerCardSelector().getCardChooserPocket()[0].setSelectedRank(pCard.getRank());
            }
            pCard = gd.getPlayerCards()[0][1];
            if(pCard != null)
            {
                cardChooserPanel.getPlayerCardSelector().getCardChooserPocket()[1].setSelectedColor(pCard.getColor());
                cardChooserPanel.getPlayerCardSelector().getCardChooserPocket()[1].setSelectedRank(pCard.getRank());
            }
            for(int i = 0; i < 3; i++)
            {
                Card card = gd.getFlop()[i];
                if(card != null)
                {
                    cardChooserPanel.getCardChooserFlop()[i].setSelectedColor(card.getColor());
                    cardChooserPanel.getCardChooserFlop()[i].setSelectedRank(card.getRank());
                }
            }

            Card turnCard = gd.getTurn();
            if(turnCard != null)
            {
                cardChooserPanel.getCardChooserTurn().setSelectedColor(turnCard.getColor());
                cardChooserPanel.getCardChooserTurn().setSelectedRank(turnCard.getRank());
                Card riverCard = gd.getRiver();
                if(riverCard != null)
                {
                    cardChooserPanel.getCardChooserRiver().setSelectedColor(riverCard.getColor());
                    cardChooserPanel.getCardChooserRiver().setSelectedRank(riverCard.getRank());
                } else
                {
                    cardChooserPanel.getCardChooserRiver().setSelectedRank(-1);
                }
            } else
            {
                cardChooserPanel.getCardChooserRiver().setSelectedRank(-1);
                cardChooserPanel.getCardChooserTurn().setSelectedRank(-1);
            }
        }
    }

    public Component getComponent()
    {
        return mainPanel;
    }

    public void saveComponents()
    {
    }

    public CardChooserPanel getCardChooserPanel()
    {
        return cardChooserPanel;
    }

    public void actionPerformed(ActionEvent arg0)
    {
        CardChooser cc[] = cardChooserPanel.getCardChooserFlop();
        Card flop[] = {
            deck.gimmeCard(cc[0].getSelectedRank(), cc[0].getSelectedColor()), deck.gimmeCard(cc[1].getSelectedRank(), cc[1].getSelectedColor()), deck.gimmeCard(cc[2].getSelectedRank(), cc[2].getSelectedColor())
        };
        CardChooser cChooser = null;
        cChooser = cardChooserPanel.getCardChooserTurn();
        Card turn = deck.gimmeCard(cChooser.getSelectedRank(), cChooser.getSelectedColor());
        cChooser = cardChooserPanel.getCardChooserRiver();
        Card river = deck.gimmeCard(cChooser.getSelectedRank(), cChooser.getSelectedColor());
        cc = cardChooserPanel.getPlayerCardSelector().getCardChooserPocket();
        gameDescription.setFlopCards(flop);
        gameDescription.setTurn(turn);
        gameDescription.setRiver(river);
        gameDescription.setPlayersCard(0, deck.gimmeCard(cc[0].getSelectedRank(), cc[0].getSelectedColor()), deck.gimmeCard(cc[1].getSelectedRank(), cc[1].getSelectedColor()));
        makeSummaryFromGameDescription();
    }

    private void makeSummaryFromGameDescription()
    {
        for(int i = 0; i < 3; i++)
            setLabelFromCard(cardChooserPanel.getFlopLabel()[i], gameDescription.getFlop()[i]);

        setLabelFromCard(cardChooserPanel.getTurnLabel(), gameDescription.getTurn());
        setLabelFromCard(cardChooserPanel.getRiverLabel(), gameDescription.getRiver());
        setLabelFromCard(cardChooserPanel.getPlayerCard()[0][0], gameDescription.getPlayerCards()[0][0]);
        setLabelFromCard(cardChooserPanel.getPlayerCard()[0][1], gameDescription.getPlayerCards()[0][1]);
    }

    private void setLabelFromCard(CardLabel jl, Card c)
    {
        if(c != null)
        {
            jl.setText(Card.toString(c.getRank()));
            jl.setColor(c.getColor());
        } else
        {
            jl.setText("-");
            jl.setColor(-1);
        }
    }

    public GameDescription getGameDescription()
    {
        return gameDescription;
    }

    public Deck getDeck()
    {
        return deck;
    }

    public void setDeck(Deck deck)
    {
        this.deck = deck;
    }

    JPanel mainPanel;
    CardChooserPanel cardChooserPanel;
    GameDescription gameDescription;
    Deck deck;

}