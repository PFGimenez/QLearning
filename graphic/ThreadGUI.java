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

package graphic;

import maze.Labyrinthe;
import qlearning.Motion;
import qlearning.strategy.Strategy;

/**
 * Thread qui s'occupe de l'affichage graphique
 * @author pgimenez
 *
 */

public class ThreadGUI extends Thread
{
	private Strategy strat;
	private GUIDisplay display;
	
	public ThreadGUI(Labyrinthe l, Motion m, Strategy strat)
	{
		this.strat = strat;
		display = new GUIDisplay(l, m, 32, true);
	}
	
	@Override
	public void run()
	{
		System.out.println("Démarrage du thread d'affichage");
		try {
			while(true)
			{
				synchronized(strat)
				{
					strat.wait();
					display.display(strat.getCurrent());
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
