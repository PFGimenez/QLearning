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
import java.util.Random;

/**
 * Labyrinthe, qui s'occupe de sa génération et des obstacles
 * @author pf
 *
 */

public class Labyrinthe {

	private int tailleX, tailleY;
	private SquareType[][] cases;
	private Random r = new Random();

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
		cases = new SquareType[tailleX][tailleY];
		for(int i = 0; i < tailleX; i++)
			for(int j = 0; j < tailleY; j++)
				cases[i][j] = (r.nextDouble() > 0.20) ? SquareType.EMPTY : SquareType.WALL; // 20% de murs
		
		addType(nbEntry, SquareType.ENTRY);
		addType(nbExit, SquareType.EXIT);
		addType(nbTrap, SquareType.TRAP);
	}
	
	private void addType(int nb, SquareType type)
	{
		for(int i = 0; i < nb; i++)
		{
			int x, y;
			do {
				x = r.nextInt(tailleX);
				y = r.nextInt(tailleY);
			} while(cases[x][y] != SquareType.EMPTY);
			cases[x][y] = type;
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
		cases = new SquareType[tailleX][tailleY];
		for(int i = tailleY-1; i >= 0; i--)
		{
	        line = br.readLine();
			for(int j = 0; j < tailleX; j++)
				cases[j][i] = SquareType.read(line.charAt(j));
		}
	    br.close();
	}
	
	/**
	 * Renvoie "vrai" ssi cette case est dans les limites du labyrinthe.
	 * Les murs sont "traversables" a priori : c'est à l'IA de comprendre qu'il ne faut pas foncer dedans.
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isTraversable(int x, int y)
	{
		return x >= 0 && x < tailleX && y >= 0 && y < tailleY;
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

	public SquareType getType(int x, int y)
	{
		return cases[x][y];
	}
	
}
