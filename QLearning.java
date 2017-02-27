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

import graphic.ThreadGUI;
import maze.Labyrinthe;
import qlearning.Motion;
import qlearning.strategy.*;

/**
 * La gestion de l'application
 * @author pf
 *
 */

public class QLearning
{
	
	public static void main(String[] args)
	{
		
		if(args.length == 0)
		{
			System.out.println("Usage :\njava QLearning [-g gamma] [-i nbIter] [-s sleep] [--no-arrows] [--no-backtrack] [-S strategy [param strategy]] --file maze1.txt");
			System.out.println("java QLearning [-g gamma] [-i nbIter] [-s sleep] [-S strategy [param strategy]] --gen sizeX sizeY nbEntry nbExit nbTrap nbPortal");
			System.out.println("Default values : gamme = 0.95, nbIter = 100000, sleep = 100, strategy = Epsilon 0.99");
			System.out.println("Strategies :");
			System.out.println("	FullRandomExplo : random walk");
			System.out.println("	FullSmartExplo : explore the maze (not completely at random)");
			System.out.println("	FullExploit : full exploitation");
			System.out.println("	Epsilon : epsilon-strategy with FullSmartExplo and FullExploit. Mandatory parameter : epsilon (recom : 0.99)");
			return;
		}
		
		double gamma = 0.95;
		int nbIter = 100000;
		int sleep = 100;
		double epsilon = 0.99;
		boolean backtrack = true;
		boolean arrows = true;
		String filename = "";
		String strategy = "epsilon";
		int sizeX = 0, sizeY = 0, nbEntry = 0, nbExit = 0, nbTrap = 0, nbPortal = 0;
		
		for(int i = 0;  i < args.length; i++)
		{
			String p = args[i];
			if(p.equals("-g"))
				gamma = Double.parseDouble(args[++i]);
			else if(p.equals("-i"))
				nbIter = Integer.parseInt(args[++i]);
			else if(p.equals("-s"))
				sleep = Integer.parseInt(args[++i]);
			else if(p.equals("--file"))
				filename = args[++i];
			else if(p.equals("--no-arrows"))
				arrows = false;
			else if(p.equals("--no-backtrack"))
				backtrack = false;		
			else if(p.equals("--gen"))
			{
				sizeX = Integer.parseInt(args[++i]);
				sizeY = Integer.parseInt(args[++i]);
				nbEntry = Integer.parseInt(args[++i]);
				nbExit = Integer.parseInt(args[++i]);
				nbTrap = Integer.parseInt(args[++i]);
				nbPortal = Integer.parseInt(args[++i]);
			}
			else if(p.equals("-S"))
			{
				strategy = args[++i].toLowerCase();
				if(strategy.equals("epsilon"))
					epsilon = Integer.parseInt(args[++i]);
			}
		}
		
		System.out.println("Gamma = "+gamma+", nbIter = "+nbIter+", sleep = "+sleep+", arrows = "+arrows+", backtracking = "+backtrack);
		
		Labyrinthe l;
		try {
			if(!filename.isEmpty())
				l = new Labyrinthe(filename);
			else if(sizeX > 0)
				l = new Labyrinthe(sizeX, sizeY, nbEntry, nbExit, nbTrap, nbPortal);
			else
			{
				System.out.println("Please use --gen or --file");
				return;
			}
			System.out.println("Maze: \n"+l);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		
		Motion m = new Motion(l, gamma, backtrack, false);

		Strategy strat;
		if(strategy.equals("epsilon"))
		{
			System.out.println("Using EpsilonStrategy with epsilon = "+epsilon);
			strat = new EpsilonStrategy(epsilon, m, new SmartFullExploration(m), false);
		}
		else if(strategy.equals("fullrandomexplo"))
		{
			System.out.println("Using FullRandomExplo");
			strat = new FullExploration(m);
		}
		else if(strategy.equals("fullsmartexplo"))
		{
			System.out.println("Using FullSmartExplo");
			strat = new SmartFullExploration(m);
		}
		else if(strategy.equals("fullexploit"))
		{
			System.out.println("Using FullExploit");
			strat = new FullExploitation(m);
		}
		else
		{
			System.out.println("Unknown strategy: "+strategy);
			return;
		}
		
		ThreadGUI display = new ThreadGUI(l, m, strat, 32, arrows);
		display.start();
		System.out.println("Cumulated score: "+strat.learn(nbIter, sleep));
		System.out.println(m);
	}
	
}
