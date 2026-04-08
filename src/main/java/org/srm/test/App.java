package org.srm.test;

import org.srm.test.bridge.Deal;
import org.srm.test.bridge.Hand;
import org.srm.test.bridge.Player;
import org.srm.test.bridge.card.Card;
import org.srm.test.bridge.card.Suit;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class App {


    public static void main( String[] args ) {
        //final Deal d = new Deal().nextDeal();
        Deal d = Deal.parse( "N:AK.AQ.J9.AKT9642 T3.973.Q87632.Q8 Q764.KJT62.AT5.7 J9852.854.K4.J53");

        System.out.println(d);
        String text = "W";

        BufferedImage img = new BufferedImage(10,10, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        Font font = new Font("Courier", Font.BOLD, 18);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        System.out.println(g2d.getFontMetrics().getFont().getSize() + " - " +  g2d.getFont().getSize());
        int width = fm.stringWidth(text)*32;
        int height = fm.getHeight()*18;
        g2d.dispose();

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setFont(font);
        System.out.println(g2d.getFontMetrics().getFont().getSize() + " - " +  g2d.getFont().getSize());
        g2d.setColor(new Color(0x4C, 0xBB, 0x17));
        g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
        g2d.setColor(Color.WHITE);

        int northLeft = fm.stringWidth(text)*11;
        int northTop = fm.getHeight();

        int northEnd = drawPlayer(Player.NORTH, d, northLeft, northTop , g2d)+10;
        int eastEnd = drawPlayer(Player.EAST, d, northLeft*2, northEnd , g2d)+10;
        drawCentralBox(g2d, font, fm, northLeft, northEnd);
        drawPlayer(Player.WEST, d, 0, northEnd , g2d);
        drawPlayer(Player.SOUTH, d, northLeft, eastEnd , g2d);
        g2d.dispose();

        try {
            ImageIO.write(img, "png", new File("deal.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private static void drawCentralBox(Graphics2D g2d, Font font, FontMetrics fm, int northLeft, int northEnd) {
        g2d.setColor(Color.WHITE);
        int thickness = 5;
        int boxSize = fm.getHeight()*5 + thickness;
        g2d.fill(new Rectangle(northLeft - thickness*2, northEnd - thickness*2, boxSize+thickness, boxSize+thickness ));

        g2d.setColor(new Color(0, 51, 20));
        g2d.fill(new Rectangle(northLeft - thickness, northEnd - thickness, boxSize-thickness, boxSize-thickness) );
        g2d.setColor(Color.WHITE);

        g2d.drawString("N", northLeft + boxSize/2- font.getSize(), northEnd +3*thickness);
        g2d.drawString("W", northLeft, northEnd + boxSize/2);
        g2d.drawString("E", northLeft + boxSize-2* font.getSize(), northEnd + boxSize/2);
        g2d.drawString("S", northLeft + boxSize/2- font.getSize(), northEnd + boxSize-3*thickness);
    }

    static int drawPlayer(Player p, Deal d, int startx, int starty, Graphics2D g2d) {
        final FontMetrics fm = g2d.getFontMetrics();
        final String spades = getStringRepresentation(Suit.SPADE, p, d) ;
        final String hearts = getStringRepresentation(Suit.HEART, p, d) ;
        final String diamonds = getStringRepresentation(Suit.DIAMOND, p, d) ;
        final String clubs = getStringRepresentation(Suit.CLUB, p, d) ;

        g2d.setColor(Color.BLACK);
        g2d.drawString(Suit.SPADE.toString(), startx, starty + fm.getHeight());
        g2d.setColor(Color.WHITE);
        final int gap = 15;
        g2d.drawString(spades, startx + gap, starty + fm.getHeight());
        g2d.setColor(Color.RED);
        g2d.drawString(Suit.HEART.toString(), startx, starty + 2 * fm.getHeight());
        g2d.setColor(Color.WHITE);
        g2d.drawString(hearts, startx+gap, starty + 2 * fm.getHeight());
        g2d.setColor(Color.RED);
        g2d.drawString(Suit.DIAMOND.toString(), startx, starty + 3 * fm.getHeight());
        g2d.setColor(Color.WHITE);
        g2d.drawString(diamonds, startx+gap, starty + 3 * fm.getHeight());
        g2d.setColor(Color.BLACK);
        g2d.drawString(Suit.CLUB.toString(), startx, starty + 4 * fm.getHeight());
        g2d.setColor(Color.WHITE);
        g2d.drawString(clubs, startx+ gap, starty + 4 * fm.getHeight());

        return starty + 5* fm.getHeight();
    }

    static String getStringRepresentation(Suit suit, Player p, Deal d) {
        Hand h = d.getHand(p);
        return h.isVoid(suit)? "-" : h.holding(suit).stream().map(Card::rank).sorted().map(Objects::toString).collect(Collectors.joining());
        //return suit.toString() + " " + holding;
    }
}
