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



import java.util.Random;

/**
 * Labyrinthe, qui s'occupe de sa génération et des obstacles
 * @author pf
 *
 */

public class Labyrinthe {

	private int tailleX, tailleY;
	private boolean[][] traversable;
	
	public Labyrinthe(int tailleX, int tailleY)
	{
		this.tailleX = tailleX;
		this.tailleY = tailleY;
		Random r = new Random();
		traversable = new boolean[tailleX][tailleY];
		for(int i = 0; i < tailleX; i++)
			for(int j = 0; j < tailleY; j++)
				traversable[i][j] = r.nextDouble() > 0.20; // 20% de murs
	}
	
	public boolean isTraversable(int x, int y)
	{
		return x >= 0 && x < tailleX && y >= 0 && y < tailleY && traversable[x][y];
	}
	
}
