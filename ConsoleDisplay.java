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

import java.io.IOException;

import maze.Labyrinthe;
import qlearning.Motion;
import qlearning.strategy.*;



/**
 * La gestion de l'application
 * @author pf
 *
 */

public class ConsoleDisplay
{
	
	public static void main(String[] args)
	{
//		Labyrinthe l = new Labyrinthe(10,10, 1, 1, 2);
		Labyrinthe l;
		try {
			l = new Labyrinthe("maze1.txt");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Motion m = new Motion(l, false, true);
		System.out.println(m);
//		Strategy strat = new EpsilonStrategy(0.90, m, new SmartFullExploration(m));
//		Strategy strat = new FullExploration(m);
//		Strategy strat = new SmartFullExploration(m);
		Strategy strat = new FullExploitation(m);
		System.out.println(strat.learn(2000));
		System.out.println(m);
	}
	
}
