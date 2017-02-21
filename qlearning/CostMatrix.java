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
import java.util.List;

/**
 * La matrice Q. S'occupe de sa mise à jour.
 * @author pf
 *
 */

public class CostMatrix
{
	private double[][][] cost;
	private double learningRate = 1;
	private double actualisationFactor;
	
	public CostMatrix(int tailleX, int tailleY, double gamma)
	{
		actualisationFactor = gamma;
		cost = new double[tailleX][tailleY][Action.values().length];
		for(int i = 0; i < tailleX; i++)
			for(int j = 0; j < tailleY; j++)
				for(int k = 0; k < Action.values().length; k++)
					cost[i][j][k] = 0;
	}
	
	public double getCost(State s, Action a)
	{
		return cost[s.x][s.y][a.ordinal()];
	}

	/**
	 * Met à jour les coûts avec un backtracking
	 * @param path
	 */
	public void doBacktrack(State depart, List<State> statePath, List<Action> actionPath, List<Double> rewardPath)
	{
		/*
		 * La liste est déjà inversée
		 */
		for(int i = 0; i < statePath.size(); i++)
			updateCost((i < statePath.size() - 1 ? statePath.get(i+1) : depart), actionPath.get(i), statePath.get(i), rewardPath.get(i));
	}
	
	/**
	 * Mise à jour du coût
	 * @param s
	 * @param a
	 * @param next
	 * @param reward
	 * @param possibles
	 */
	public void updateCost(State s, Action a, State next, double reward)
	{
		cost[s.x][s.y][a.ordinal()] = cost[s.x][s.y][a.ordinal()] + learningRate * (reward + actualisationFactor * cost[next.x][next.y][getBestAction(next).ordinal()] - cost[s.x][s.y][a.ordinal()]);
	}
	
	/**
	 * Récupère l'action optimale pour l'exploitation
	 * @param s
	 * @param possibles
	 * @return
	 */
	public Action getBestAction(State s)
	{
		Action bestAction = null;
		double best = 0;
		for(Action a : Action.values())
			if(new State(s.x+a.dx, s.y+a.dy).isPossible() && (bestAction == null || cost[s.x][s.y][a.ordinal()] > best))
			{
				best = cost[s.x][s.y][a.ordinal()];
				bestAction = a;
			}
		return bestAction;
	}
}
