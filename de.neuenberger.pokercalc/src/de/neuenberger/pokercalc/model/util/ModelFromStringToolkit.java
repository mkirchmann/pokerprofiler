// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 29.04.2007 17:36:38
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ModelFromStringToolkit.java

package de.neuenberger.pokercalc.model.util;

import de.neuenberger.poker.common.model.Card;
import de.neuenberger.poker.common.model.Deck;
import de.neuenberger.poker.common.model.GameDescription;
import de.neuenberger.pokercalc.model.*;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.*;
import java.io.PrintStream;
import javax.swing.SwingUtilities;

public class ModelFromStringToolkit
{

    public ModelFromStringToolkit()
    {
    }

    public static GameDescription getGameDescription(Component comp)
    {
        Transferable clipData = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(SwingUtilities.getRootPane(comp));
        try
        {
            String clipString = (String)clipData.getTransferData(DataFlavor.stringFlavor);
            return processString(clipString);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    protected static GameDescription processString(String clipString)
    {
        GameDescription gameDescription = new GameDescription();
        String strArr[] = clipString.split("\n");
        boolean metFlop = false;
        boolean metTurn = false;
        boolean metRiver = false;
        boolean metPocket = false;
        for(int i = strArr.length - 1; i >= 0; i--)
        {
            int length = strArr[i].length();
            if(strArr[i].matches("^.*to.*\\[.{5}\\]$"))
            {
                System.out.println("Found Pocket match: " + strArr[i]);
                String cardString = strArr[i].substring(length - 6, length - 1);
                Card cardArray[] = getCardsFromStringSpaceSeparated(cardString);
                gameDescription.getPlayerCards()[0][0] = cardArray[0];
                gameDescription.getPlayerCards()[0][1] = cardArray[1];
                metPocket = true;
            } else
            if(strArr[i].matches("^.*Flop.*\\[.{6}\\]$") || strArr[i].matches("^.*FLOP.*\\[.{6}\\]$"))
            {
                if(metFlop)
                    break;
                metFlop = true;
                System.out.println("Found Flop match: " + strArr[i]);
                metFlop = true;
                String cardString = strArr[i].substring(length - 7, length - 1);
                Card cardArray[] = getCardsFromStringNonSeparated(cardString);
                gameDescription.getFlop()[0] = cardArray[0];
                gameDescription.getFlop()[1] = cardArray[1];
                gameDescription.getFlop()[2] = cardArray[2];
            } else
            if(strArr[i].matches("^.*Flop.*\\[.{8}\\]$") || strArr[i].matches("^.*FLOP.*\\[.{8}\\]$"))
            {
                if(metFlop)
                    break;
                metFlop = true;
                System.out.println("Found Flop match: " + strArr[i]);
                metFlop = true;
                String cardString = strArr[i].substring(length - 9, length - 1);
                Card cardArray[] = getCardsFromStringSpaceSeparated(cardString);
                gameDescription.getFlop()[0] = cardArray[0];
                gameDescription.getFlop()[1] = cardArray[1];
                gameDescription.getFlop()[2] = cardArray[2];
            } else
            if(strArr[i].matches("^.*Turn.*\\[.{2}\\]$") || strArr[i].matches("^.*TURN.*\\[.{2}\\]$"))
            {
                if(metTurn)
                    break;
                metTurn = true;
                System.out.println("Found Turn match: " + strArr[i]);
                String cardString = strArr[i].substring(length - 3, length - 1);
                gameDescription.setTurn(getCardsFromStringNonSeparated(cardString)[0]);
            } else
            if(strArr[i].matches("^.*River.*\\[.{2}\\]$") || strArr[i].matches("^.*RIVER.*\\[.{2}\\]$"))
            {
                if(metRiver)
                    break;
                metRiver = true;
                System.out.println("Found River match: " + strArr[i]);
                String cardString = strArr[i].substring(length - 3, length - 1);
                gameDescription.setRiver(getCardsFromStringNonSeparated(cardString)[0]);
            } else
            {
                System.out.println("No match: " + strArr[i]);
            }
            if(metPocket)
                break;
        }

        return gameDescription;
    }

    public static Card[] getCardsFromStringNonSeparated(String str)
    {
        Card cardArray[] = new Card[str.length() / 2];
        for(int i = 0; i < str.length() / 2; i++)
        {
            String cardString = str.substring(i * 2, (i + 1) * 2);
            cardArray[i] = Deck.getInstance().gimmeCard(Card.getRankFromCharacter(cardString.charAt(0)), Card.getColorFromCharacter(cardString.charAt(1)));
        }

        return cardArray;
    }

    public static Card[] getCardsFromStringSpaceSeparated(String str)
    {
        String strArr[] = str.split(" ");
        Card cardArray[] = new Card[strArr.length];
        for(int i = 0; i < strArr.length; i++)
            cardArray[i] = Deck.getInstance().gimmeCard(Card.getRankFromCharacter(strArr[i].charAt(0)), Card.getColorFromCharacter(strArr[i].charAt(1)));

        return cardArray;
    }
}