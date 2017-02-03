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
 * Gestion des mouvements, met à jour la matrice de coût
 * @author pf
 *
 */

public class Motion {

	private State[][] etats;
	private Labyrinthe l;
	
	private double coutDeplacement;
	private CostMatrix matrix;
	private State exit;

	public Motion(int tailleX, int tailleY, double coutDeplacement)
	{
		this.coutDeplacement = coutDeplacement;
		matrix = new CostMatrix(tailleX, tailleY);
		Random r = new Random();
		for(int i = 0; i < tailleX; i++)
			for(int j = 0; j < tailleY; j++)
				etats[i][j] = new State(i,j);
		l = new Labyrinthe(tailleX, tailleY);
		do {
			exit = etats[r.nextInt()%tailleX][r.nextInt()%tailleY];
		} while(!l.isTraversable(exit.x, exit.y));
	}
	
	public boolean isPossible(State etat, Action action)
	{
		return l.isTraversable(etat.x+action.dx, etat.y+action.dy);
	}
	
	public double getCost(State etat, Action action)
	{
		return coutDeplacement;
	}
	
	/**
	 * On suppose que le trajet est possible
	 * @param etat
	 * @param action
	 * @return
	 */
	public State getNextState(State etat, Action action)
	{
		return etats[etat.x+action.dx][etat.y+action.dy];
	}
	
}
