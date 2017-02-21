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

import graphic.ThreadGUI;
import maze.Labyrinthe;
import qlearning.Motion;
import qlearning.strategy.*;

/**
 * La gestion de l'application
 * @author pf
 *
 */

public class QLearning
{
	
	public static void main(String[] args)
	{
		Labyrinthe l;
		try {
			l = new Labyrinthe("maze1.txt");
//			l = new Labyrinthe(10, 10, 3, 1, 10, 0);
			System.out.println("Labyrinthe : \n"+l);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		Motion m = new Motion(l, 0.95, true, false);
		Strategy strat = new EpsilonStrategy(0.99, m, new SmartFullExploration(m), true);
//		Strategy strat = new FullExploration(m);
//		Strategy strat = new SmartFullExploration(m);
//		Strategy strat = new FullExploitation(m);
		ThreadGUI display = new ThreadGUI(l, m, strat, 32, true);
		display.start();
		System.out.println("Score cumulé : "+strat.learn(200000, 100));
		System.out.println(m);
	}
	
}
