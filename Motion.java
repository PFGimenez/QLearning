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



import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Gestion des mouvements, met à jour la matrice de coût
 * @author pf
 *
 */

public class Motion {

	private class StateReward
	{
		public final State s;
		public final double r;
		
		public StateReward(State s, double r)
		{
			this.s = s;
			this.r = r;
		}
	}
	
	private State[][] etats;
	private Labyrinthe l;
	
	private double coutDeplacement;
	private CostMatrix matrix;
	private double epsilon;
	private Random r = new Random();
	
	public Motion(int tailleX, int tailleY, double coutDeplacement, double epsilon, Labyrinthe l)
	{
		this.coutDeplacement = coutDeplacement;
		matrix = new CostMatrix(tailleX, tailleY);
		etats = new State[tailleX][tailleY];
		for(int i = 0; i < tailleX; i++)
			for(int j = 0; j < tailleY; j++)
				etats[i][j] = new State(i,j);
		this.l = l;
	}
	
	public boolean isPossible(State etat, Action action)
	{
		return l.isTraversable(etat.x+action.dx, etat.y+action.dy);
	}
	
	public Action drawAction(State s)
	{
		Action bestAction = matrix.getBestAction(s), drawn;
		if(r.nextDouble() > epsilon)
			return bestAction;

		do {
			drawn = Action.values()[r.nextInt(Action.values().length)];
		} while(drawn != bestAction);
		return drawn;
	}
	
	public Action getBestAction(State s)
	{
		return matrix.getBestAction(s);
	}
	
	/**
	 * Renvoie la liste des actions possibles à partir de cet état
	 * @param s
	 * @return
	 */
	public List<Action> getPossibleActions(State s)
	{
		List<Action> out =  new ArrayList<Action>();
		for(Action a : Action.values())
			if(l.isTraversable(s.x+a.dx, s.y+a.dy))
				out.add(a);
		return out;
	}
	
	/**
	 * Renvoie le couple état/récompense qui résulte de cette action à partir de cet état
	 * Si le mouvement est interdit, renvoie null.
	 * @param etat
	 * @param action
	 * @return
	 */
	public StateReward getNextStateReward(State etat, Action action)
	{
		/*
		 * Action impossible (bord du terrain)
		 */
		if(!l.isTraversable(etat.x+action.dx, etat.y+action.dy))
			return null;
		
		/*
		 * L'état dans lequel on sera si tout se passe bien
		 */
		State voisin = etats[etat.x+action.dx][etat.y+action.dy];
		State nextState = voisin; // a priori, le déplacement se fait normalement
		
		double cout = 0;
		if(l.getType(voisin.x, voisin.y) == SquareType.EXIT)
			cout = 100;
		else if(l.getType(voisin.x, voisin.y) == SquareType.TRAP)
			cout = -10;
		else if(l.getType(voisin.x, voisin.y) == SquareType.WALL)
		{
			cout = -5;
			/*
			 * Si on avance dans un mur, on ne bouge pas
			 */
			nextState = etat;
		}
		
		return new StateReward(nextState, coutDeplacement + cout);
	}
	
	public String toString()
	{
		return l.toString();
	}
	
}
