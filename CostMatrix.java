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



/**
 * La matrice Q. S'occupe de sa mise à jour.
 * @author pf
 *
 */

public class CostMatrix
{
	private double[][][] cost;
	private double learningRate = 0.1;
	private double actualisationFactor = 1;
	
	public CostMatrix(int tailleX, int tailleY)
	{
		cost = new double[tailleX][tailleY][Action.values().length];
	}
	
	public double getCost(State s, Action a)
	{
		return cost[s.x][s.y][a.ordinal()];
	}
	
	public void updateCost(State s, Action a, State next, double reward)
	{
		cost[s.x][s.y][a.ordinal()] += learningRate * (reward + actualisationFactor * cost[s.x][s.y][getBestAction(next).ordinal()] - cost[s.x][s.y][a.ordinal()]);
	}
	
	public Action getBestAction(State s)
	{
		int bestAction = 0;
		double best = cost[s.x][s.y][0];
		for(int i = 1; i < Action.values().length; i++)
			if(cost[s.x][s.y][i] > best)
			{
				best = cost[s.x][s.y][i];
				bestAction = i;
			}
		return Action.values()[bestAction];
	}
}
