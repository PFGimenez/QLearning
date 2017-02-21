package graphic;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import maze.Labyrinthe;
import maze.SquareType;
import qlearning.Action;
import qlearning.Motion;
import qlearning.State;

/*
Copyright (C) 2017 Pierre-François Gimenez

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>

*/

/**
 * Petite interface graphique
 * @author pgimenez
 *
 */

public class GUIDisplay extends JPanel {

	private Labyrinthe l;
	private Motion m;
	private Image spriteMur, spritePerso, spriteSol, spritePiege, spriteEntry, spriteExit, spriteTeleport;
	private Image[] spritesArrow = new Image[4];
	private static final long serialVersionUID = 1L;
	private State current;
	private int taille;
	private boolean drawArrow;
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		int tailleX = l.getTailleX();
		int tailleY = l.getTailleY();
		for(int i = 0; i < tailleX; i++)
			for(int j = 0; j < tailleY; j++)
			{
				SquareType type = l.getType(i, j);
				if(type == SquareType.EMPTY)
					g.drawImage(spriteSol, i*taille, (tailleY-j-1)*taille, Color.WHITE, null);
				else if(type == SquareType.ENTRY)
					g.drawImage(spriteEntry, i*taille, (tailleY-j-1)*taille, Color.WHITE, null);
				else if(type == SquareType.EXIT)
					g.drawImage(spriteExit, i*taille, (tailleY-j-1)*taille, Color.WHITE, null);
				else if(type == SquareType.TRAP)
					g.drawImage(spritePiege, i*taille, (tailleY-j-1)*taille, Color.WHITE, null);
				else if(type == SquareType.WALL)
					g.drawImage(spriteMur, i*taille, (tailleY-j-1)*taille, Color.WHITE, null);
				else if(type == SquareType.TELEPORT)
					g.drawImage(spriteTeleport, i*taille, (tailleY-j-1)*taille, Color.WHITE, null);
				Action a = m.getBestAction(new State(i,j));
				if(m.hasBeenVisited(new State(i,j)) && drawArrow)
					g.drawImage(spritesArrow[a.ordinal()], i*taille, (tailleY-j-1)*taille, new Color(0,0,0,0), null);
				if(current != null && current.x == i && current.y == j)
					g.drawImage(spritePerso, i*taille, (tailleY-j-1)*taille, new Color(0,0,0,0), null);
			}
	}

	public GUIDisplay(Labyrinthe l, Motion m, int taille, boolean drawArrow)
	{
		this.drawArrow = drawArrow;
		this.m = m;
		this.taille = taille;
		this.l = l;
		try {
			spriteMur = ImageIO.read(new File("img"+taille+"/wall.png"));
			spriteSol = ImageIO.read(new File("img"+taille+"/floor.png"));
			spritePerso = ImageIO.read(new File("img"+taille+"/character.png"));
			spritePiege = ImageIO.read(new File("img"+taille+"/trap.png"));
			spriteEntry = ImageIO.read(new File("img"+taille+"/entry.png"));
			spriteExit = ImageIO.read(new File("img"+taille+"/exit.png"));
			spriteTeleport = ImageIO.read(new File("img"+taille+"/portal.png"));
			spritesArrow[0] = ImageIO.read(new File("img"+taille+"/arrow_top.png"));
			spritesArrow[1] = ImageIO.read(new File("img"+taille+"/arrow_bottom.png"));
			spritesArrow[2] = ImageIO.read(new File("img"+taille+"/arrow_left.png"));
			spritesArrow[3] = ImageIO.read(new File("img"+taille+"/arrow_right.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(taille*l.getTailleX(),taille*l.getTailleY()));
        JFrame frame = new JFrame();
		frame.setTitle("TP QLearning");
		frame.setSize(taille*l.getTailleX(),taille*l.getTailleY());
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             
        frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);
		repaint();
	}

	public void display(State current) {
		this.current = current;
		repaint();
	}
}
