# Application of Q-learning algorithm to maze solving

## Building

You will need `ant` and `java`.

```
$ git clone https://github.com/PFGimenez/QLearning.git
$ cd QLearning
$ ant
```

You can verify it works by running an example:
    
```   
$ java QLearning --file maze3.txt
```

## Usage

```
java QLearning [-g gamma] [-i nbIter] [-s sleep] [--no-arrows] [--no-backtrack] [-G [16|32]] [-S strategy [param strategy]] --file maze[1|2|3].txt
java QLearning [-g gamma] [-i nbIter] [-s sleep] [--no-arrows] [--no-backtrack] [-G [16|32]] [-S strategy [param strategy]] --gen sizeX sizeY nbEntry nbExit nbTrap nbPortal

Strategies:
	FullRandomExplo: random walk
	FullSmartExplo: explore the maze (not completely at random)
	FullExploit: full exploitation
	Epsilon: epsilon-strategy with FullSmartExplo and FullExploit. Mandatory parameter : epsilon (recom : 0.99)
```

## Screenshot
    
![Screenshot](https://raw.githubusercontent.com/PFGimenez/QLearning/master/screenshot.png)

Sprites designed by [Nevanda](http://nevanda.deviantart.com/art/nethack-tiles-32x32px-416691316) and [DragonDePlatino](http://dragondeplatino.deviantart.com/art/DawnHack-NetHack-3-4-3-UnNetHack-5-1-0-416312313) for the game NetHack.
