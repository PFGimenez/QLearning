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

package qlearning.strategy;

import qlearning.Action;
import qlearning.Motion;
import qlearning.State;

/**
 * Stratégie d'exploration pure intelligente : elle explore là où elle ne connaît pas.
 * @author pf
 *
 */

public class SmartFullExploration extends Strategy
{
	public SmartFullExploration(Motion motion) 
	{
		super(motion);
	}

	@Override
	protected Action chooseAction(Motion motion, State current, int nbTours)
	{
		Action best = null;
		int valueBest = 0;
		for(Action a : Action.values())
		{
			int x = current.x + a.dx;
			int y = current.y + a.dy;
			if(!new State(x,y).isPossible())
				continue;
			
			if(best == null || comptes[current.x][current.y][a.ordinal()] < valueBest)
			{
				best = a;
				valueBest = comptes[current.x][current.y][a.ordinal()];
			}
		}
		return best;
	}

}
