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

import java.util.List;
import java.util.Random;

import qlearning.Action;
import qlearning.Motion;
import qlearning.State;

/**
 * Stratégie d'exploration pure : choix au hasard de l'action à prendre.
 * @author pf
 *
 */

public class FullExploration implements Strategy
{
	private Random r = new Random();
	
	@Override
	public Action chooseAction(Motion motion, State current, int nbTours)
	{
		List<Action> actions = motion.getPossibleActions(current);
		return actions.get(r.nextInt(actions.size()));
	}

}
