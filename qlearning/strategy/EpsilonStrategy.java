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

import java.util.Random;

import qlearning.Action;
import qlearning.Motion;
import qlearning.State;

/**
 * Stratégie de compromis entre exploration et exploitation.
 * Au début, l'exploration et privilégiée puis, petit à petit, la situation va s'inverser.
 * @author pf
 *
 */

public class EpsilonStrategy extends Strategy
{
	private double epsilon;
	private boolean verbose;
	private Random r = new Random();
	private Strategy exploit;
	private Strategy explore;
	
	public EpsilonStrategy(double epsilon, Motion motion, Strategy explore, boolean verbose)
	{
		super(motion);
		this.verbose = verbose;
		exploit = new FullExploitation(motion);
		this.explore = explore;
		this.epsilon = epsilon;
	}
	
	@Override
	protected Action chooseAction(Motion motion, State current, int nbTours)
	{
		double tmpEpsilon = Math.pow(epsilon, nbTours);
		if(verbose)
			System.out.println("epsilon : "+tmpEpsilon);
		if(r.nextDouble() > tmpEpsilon)
		{
			if(verbose)
				System.out.println("Exploite");
			return exploit.chooseAction(motion, current, nbTours);
		}
		
		if(verbose)
			System.out.println("Explore");
		return explore.chooseAction(motion, current, nbTours);
	}

}
