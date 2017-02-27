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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	private Random r = new Random();
	
	public SmartFullExploration(Motion motion) 
	{
		super(motion);
	}

	@Override
	protected Action chooseAction(Motion motion, State current, int nbTours)
	{
		List<Action> bests = new ArrayList<Action>();
		int valueBest = 0;
		for(Action a : Action.values())
		{
			int x = current.x + a.dx;
			int y = current.y + a.dy;
			if(!new State(x,y).isPossible())
				continue;
			
			if(bests.isEmpty() || comptes[current.x][current.y][a.ordinal()] <= valueBest)
			{
				if(comptes[current.x][current.y][a.ordinal()] < valueBest)
					bests.clear();
				valueBest = comptes[current.x][current.y][a.ordinal()];
				bests.add(a);
				if(valueBest == 0 && !motion.hasBeenVisited(new State(x,y)))
					return a;
			}
		}
		return bests.get(r.nextInt(bests.size()));
	}

}
