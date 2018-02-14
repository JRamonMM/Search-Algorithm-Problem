# Search-Algorithm-Problem
This repository contains the laboratory project of Intelligent System subject at ESI (UCLM).

Be a field of dimensions (CxF ([0… C-1]x[0…F-1]), in whose squares there may be no more than a quantity of sand than MAX 
(MAX >= k + s) and a small tractor in a box (xt, yt) determined where:
    - k: is the desired quantity in each box.
    - s: is the amount to be redistributed between neighbouring boxes.
Find the sequence of action to be taken by the tractor to ensure that all the sand ins evenly distributed on the ground and 
that all boxes have an equal amount of sand k.
An action is determined by:
    - Distribution: The tractor may distribute a quantity of sand s form its box to the adjacent boxes (N, S, E, W).
    - Movement: The tractor may move to an adjacent position.
    
The possible algorithms you can choose to solve the problem are what follows:
    - BFS.
    - DFS (Simple, Bounded or Iterative).
    - UCS.
    - A*.
    
The initial state of the field is read from a file and the solution of the problem is saved into a .txt file. I also saved the
valid movements from the initial state into a .txt file called movements.txt.

Source file has to satisfy some constraints as follows:
First line has to contain: xt yt k max c r:
    - xt: x position of the tractor.
    - yt: y position of the tractor.
    - k: desired quantity of sand in each box.
    - max: maximum quantity of sand that a box can contain.
    - c: number of colums of the field.
    - f: number of rows of the field.

After this first line ther must be an array de integers according the size established with c and r values, I mean an array of
integers of size cxr, for example, for c=3 and r=3 it can be:
6 8 5
6 8 2
2 0 8

Some errors can happen cause a wrong format of the source field. Most of them are detected.