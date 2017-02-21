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

/**
 * Le type d'une case dans le labyrinthe
 * @author pf
 *
 */

public enum SquareType {
	ENTRY('<', -1),
	EMPTY('.', -1),
	WALL('#', -5),
	EXIT('>', 1000),
	TRAP('^', -10),
	TELEPORT('T', -5);
	
	/**
	 * Le symbole associé à ce type dans un fichier texte
	 */
	public final char symbol;
	
	/**
	 * La récompense du Q-learning en passant par ce point
	 */
	public final double recompense;
	
	private SquareType(char symbol, double recompense)
	{
		this.symbol = symbol;
		this.recompense = recompense;
	}
	
	/**
	 * Renvoie le type associé à un symbole
	 * @param symbol
	 * @return
	 */
	public static SquareType read(char symbol)
	{
		for(SquareType t : SquareType.values())
			if(t.symbol == symbol)
				return t;
		return null;
	}
}
