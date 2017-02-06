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

package maze;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import qlearning.State;

/**
 * Labyrinthe, qui s'occupe de sa génération et des obstacles
 * @author pf
 *
 */

public class Labyrinthe {

	private int tailleX, tailleY;
	private SquareType[][] cases;
	private Random r = new Random();
	private List<State> listEntry = new ArrayList<State>();
	private List<State> listExit = new ArrayList<State>();
	private List<State> listTrap = new ArrayList<State>();

	/**
	 * Constructeur d'un labyrinthe aléatoire
	 * @param tailleX
	 * @param tailleY
	 * @param nbEntry
	 * @param nbExit
	 * @param nbTrap
	 */
	public Labyrinthe(int tailleX, int tailleY, int nbEntry, int nbExit, int nbTrap)
	{
		this.tailleX = tailleX;
		this.tailleY = tailleY;
	    State.setTailles(tailleX, tailleY);
		cases = new SquareType[tailleX][tailleY];
		for(int i = 0; i < tailleX; i++)
			for(int j = 0; j < tailleY; j++)
				cases[i][j] = (r.nextDouble() > 0.20) ? SquareType.EMPTY : SquareType.WALL; // 20% de murs
		
		addType(nbEntry, SquareType.ENTRY, listEntry);
		addType(nbExit, SquareType.EXIT, listExit);
		addType(nbTrap, SquareType.TRAP, listTrap);
	}
	
	private void addType(int nb, SquareType type, List<State> list)
	{
		for(int i = 0; i < nb; i++)
		{
			int x, y;
			do {
				x = r.nextInt(tailleX);
				y = r.nextInt(tailleY);
			} while(cases[x][y] != SquareType.EMPTY);
			cases[x][y] = type;
			list.add(new State(x, y));
		}
	}
	
	/**
	 * Constructeur qui charge un labyrinthe à partir d'un fichier texte
	 * @param filename
	 * @throws IOException
	 */
	public Labyrinthe(String filename) throws IOException
	{
		BufferedReader br;
		br = new BufferedReader(new FileReader(filename));
	    String line;
	    tailleX = Integer.parseInt(br.readLine());
	    tailleY = Integer.parseInt(br.readLine());
	    State.setTailles(tailleX, tailleY);
		cases = new SquareType[tailleX][tailleY];
		for(int i = tailleY-1; i >= 0; i--)
		{
	        line = br.readLine();
			for(int j = 0; j < tailleX; j++)
			{
				cases[j][i] = SquareType.read(line.charAt(j));
				if(cases[j][i] == SquareType.ENTRY)
					listEntry.add(new State(j,i));
				else if(cases[j][i] == SquareType.EXIT)
					listExit.add(new State(j,i));
				else if(cases[j][i] == SquareType.TRAP)
					listTrap.add(new State(j,i));
			}
		}
	    br.close();
	}
	
	/**
	 * Permet d'afficher le labyrinthe
	 */
	public String toString()
	{
		String out = "";
		for(int i = tailleY-1; i >= 0; i--)
		{
			for(int j = 0; j < tailleX; j++)
				out += cases[j][i].symbol;
			out += "\n";
		}
		out += "\n";
		for(SquareType t : SquareType.values())
			out += t.symbol+" = "+t.toString()+"	";
		out += "\n";
		return out;
	}

	/**
	 * Renvoie le type d'une case
	 * @param x
	 * @param y
	 * @return
	 */
	public SquareType getType(int x, int y)
	{
		return cases[x][y];
	}

	/**
	 * Renvoie une sortie aléatoire
	 * @return
	 */
	public State getRandomEntry()
	{
		return listEntry.get(r.nextInt(listEntry.size()));
	}

	public int getTailleX()
	{
		return tailleX;
	}

	public int getTailleY()
	{
		return tailleY;
	}

}
