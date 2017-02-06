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

package qlearning;

/**
 * Les états du processus markovien. En l'occurence, les positions dans le labyrinthe.
 * @author pf
 *
 */

public class State
{
	private static int tailleX, tailleY;
	
	public static void setTailles(int tailleX, int tailleY)
	{
		State.tailleX = tailleX;
		State.tailleY = tailleY;
	}
	
	public final int x, y;

	public State(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	@Override
	public int hashCode()
	{
		return x*5000+y;
	}
	
	@Override
	public boolean equals(Object other)
	{
		return other instanceof State && ((State)other).x == x && ((State)other).y == y;
	}
	
	
	/**
	 * Renvoie "vrai" ssi cette case est dans les limites du labyrinthe.
	 * Les murs sont "traversables" a priori : c'est à l'IA de comprendre qu'il ne faut pas foncer dedans.
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isPossible()
	{
		return x >= 0 && x < tailleX && y >= 0 && y < tailleY;
	}
	
	public String toString()
	{
		return "("+x+","+y+")";
	}
}
