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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import maze.Labyrinthe;
import maze.SquareType;

/**
 * Gestion des mouvements, met à jour la matrice de coût
 * @author pf
 *
 */

public class Motion
{	
	private State[][] etats;
	private Labyrinthe l;
	private int tailleX, tailleY;
	private CostMatrix matrix;
	private boolean backtracking;
	private LinkedList<State> statePath = new LinkedList<State>();
	private LinkedList<Action> actionPath = new LinkedList<Action>();
	private LinkedList<Double> rewardPath = new LinkedList<Double>();
	private State depart;
	private boolean verbose;
	
	public Motion(Labyrinthe l, double gamma, boolean backtracking, boolean verbose)
	{
		this.verbose = verbose;
		this.backtracking = backtracking;
		tailleX = l.getTailleX();
		tailleY = l.getTailleY();
		matrix = new CostMatrix(tailleX, tailleY, gamma);
		etats = new State[tailleX][tailleY];
		for(int i = 0; i < tailleX; i++)
			for(int j = 0; j < tailleY; j++)
				etats[i][j] = new State(i,j);
		this.l = l;
	}
	
	public Action getBestAction(State s)
	{
		return matrix.getBestAction(s);
	}
	
	public boolean hasBeenVisited(State s)
	{
		return matrix.hasBeenVisited(s);
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
			if(new State(s.x+a.dx, s.y+a.dy).isPossible())
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
		if(!new State(etat.x+action.dx, etat.y+action.dy).isPossible())
			return null;
		
		/*
		 * L'état dans lequel on sera si tout se passe bien
		 */
		State voisin = etats[etat.x+action.dx][etat.y+action.dy];
		State nextState = voisin; // a priori, le déplacement se fait normalement
		
		double cout;

		/*
		 * Si on avance dans un mur, on ne bouge pas
		 */
		if(l.getType(voisin.x, voisin.y) == SquareType.WALL)
		{
			cout = SquareType.WALL.recompense;
			nextState = etat;
		}
		else if(l.getType(voisin.x, voisin.y) == SquareType.TELEPORT)
		{
			cout = SquareType.TELEPORT.recompense;
			nextState = l.getRandomPortal(voisin);
		}
		else
			cout = l.getType(nextState.x, nextState.y).recompense;
		
		statePath.addFirst(nextState);
		actionPath.addFirst(action);
		rewardPath.addFirst(cout);

		if(verbose)
			System.out.println("Passage de "+etat+" à "+nextState+"("+l.getType(nextState.x, nextState.y)+")"+" par "+action+". Récompense = "+cout);

		return new StateReward(nextState, cout);
	}
	
	/**
	 * Permet d'afficher la stratégie d'exploitation
	 */
	public String toString()
	{
		NumberFormat formatter = new DecimalFormat("#0.00");
		String out = "";//l.toString()+"\n";
		for(int i = tailleY-1; i >= 0; i--)
		{
			for(int j = 0; j < tailleX; j++)
			{
				Action a = getBestAction(new State(j,i));
				double r = matrix.getCost(new State(j,i), a);
				out += a+" "+formatter.format(r)+"	";
			}
			out += "\n";
		}
		return out;
	}

	/**
	 * Récupère une entrée aléatoire pour se replacer
	 * @return
	 */
	public State getRandomEntry()
	{
		State out = l.getRandomEntry();
		depart = out;
		return out;
	}
	
	/**
	 * Mise à jour du coût dans la matrice
	 * @param s
	 * @param a
	 * @param next
	 * @param reward
	 */
	public void updateCost(State s, Action a, State next, double reward)
	{
		matrix.updateCost(s, a, next, reward);
	}

	/**
	 * Est-on arrivé à une sortie du labyrinthe ?
	 * @param state
	 * @return
	 */
	public boolean isExited(State state)
	{
		boolean exited = l.getType(state.x, state.y) == SquareType.EXIT;
		if(exited && backtracking)
		{
			matrix.doBacktrack(depart, statePath, actionPath, rewardPath);
			statePath.clear();
			actionPath.clear();
			rewardPath.clear();
		}
		return exited;
	}

	public int getTailleX() 
	{
		return l.getTailleX();
	}

	public int getTailleY() 
	{
		return l.getTailleY();
	}

}
