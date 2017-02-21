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
import qlearning.StateReward;

/**
 * Classe mère des stratégies
 * @author pf
 *
 */

public abstract class Strategy
{
	private Motion motion;
	private double cumulatedReward;
	protected static int[][][] comptes = null; // si on combine plusieurs stratége, il faut quand même une seule matrice de comptes
	private State current = null;
	
	public Strategy(Motion motion)
	{
		this.motion = motion;
		if(comptes == null)
			comptes = new int[motion.getTailleX()][motion.getTailleY()][Action.values().length];
	}
	
	public State getCurrent()
	{
		return current;
	}
	
	/**
	 * Apprend pendant nbToursMax
	 * Renvoie la somme des récompenses obtenues
	 * @param nbToursMax
	 * @return
	 */
	public double learn(int nbToursMax, int sleep)
	{
		long lastAff = System.currentTimeMillis();
		cumulatedReward = 0;
		for(int i = 0; i < comptes.length; i++)
			for(int j = 0; j < comptes[i].length; j++)
				for(int k = 0; k < Action.values().length; k++)
					comptes[i][j][k] = 0;
		
		current = motion.getRandomEntry();
		for(int i = 0; i < nbToursMax; i++)
		{
			if(System.currentTimeMillis() - lastAff > 5000)
			{
				lastAff = System.currentTimeMillis();
				System.out.println(i+" itérations");
			}
			synchronized(this)
			{
				Action a = chooseAction(motion, current, i);
				comptes[current.x][current.y][a.ordinal()]++;
				StateReward sr = motion.getNextStateReward(current, a);
				motion.updateCost(current, a, sr.s, sr.r);
				current = sr.s;
				cumulatedReward += sr.r;
				if(motion.isExited(current))
					current = motion.getRandomEntry();
				notify();
			}
			if(sleep > 0)
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
		return cumulatedReward;
	}
	
	/**
	 * Choisit une action pour un état donné
	 * @param motion
	 * @param current
	 * @return
	 */
	protected abstract Action chooseAction(Motion motion, State current, int nbTours);
}
