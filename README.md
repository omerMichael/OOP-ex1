# OOP-ex1


assignment ex1 OOP  Ariel University 


This project is the second assignment in the OOP course at Ariel University in the Department of Computer Science.
This project is actually an improvement of ex0 assignment.The project will deal with unweighted graphs(And unintentional).
now i will describe the structure of the project:

Src folder:
-Three interfaces(node_info,weighted_graph and weighted_graph_algorithms) and their implements(WGraph_DS and WGraph_Algo)
 WGraph_DS implements weighted_graph and node_info (inner class).
 WGraph_Algo implements weighted_graph_algorithms.
 (five class total at Src).
tests folder:
 WGraph_AlgoTest runs tests on WGraph_Algo.
 WGraph_DSTest runs tests on WGraph_DS.

Data structures that I used for the assignment:
-HashMap O(1)
 nodes. Integer(key) , node_info (This is how I store the vertices and their neighbors).
 Nei. (This is how I store the vertices and their neighbors(For by definition it is one who has a edge between him and the other vertex)).
 weight. (Here I hold the weight of the edges)(This is a special structure that includes the HashMap inside the HashMap <Interger (key) ,HashMap Interger(key),Double(weight)>>).
In addition,
I used built-in Ashmap methods (for example, put, size, value...).
They are trivial and have a reference in the code itself.

BFS
first each node by default receives the value 0 through tag method.but if in the BFS algorithm i visit the same node it becomes 1.
I pass a visit to all the neighbors using the HashMap(Which includes<Integer, node_data>) and Queue data structures.
After summoning the BFS I basically go through all the nomads in the graph and check if any of them are not now equal to 1 then it is 0
(We did not visit it) So he did not graph a link and the function will return FALSE


Dijkstra's - An algorithm that goes through the vertices of the graph we learned in class, uses the shortestPathDist method.


below Key methods that I think are appropriate to emphasize. The rest of the methods will be referenced within the code itself(Through Javadoc).

node_info inner class- 
 getkey - Returns the unique key to the same node_info
 get&set Tag - Used For example in bfs to initialize vertices to 0 or 1.

weighted_graph implemented by WGraph_DS class-
 copy constructor(It uses the copy method at WGraph_Algo).
 numOfEd - provides me the number of edges in the graph.
 getNode - returns the node_info 
 
WGraph_Algo-
 checkVisit - a Boolean method that checks whether after each pass on the vertices of the graph there is a vertex that we have not visited.
 BFSinit - initializes the graph vertices to 0.
 Compy - A comparator compares 2 objects.

@OmerMichael

