package ex1.src;

import ex0.graph;
import ex0.node_data;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms, Serializable {
    private weighted_graph gr;


    /**
     * Init the graph on which this set of algorithms operates on.
     *
     * @param g
     */
    @Override
    public void init(weighted_graph g) {
        this.gr = g;
    }

    /**
     * Return the underlying graph of which this class works.
     *
     * @return
     */
    @Override
    public weighted_graph getGraph() {
        return gr;
    }

    /**
     * Compute a deep copy of this weighted graph.
     *
     * @return
     */
    @Override
    public weighted_graph copy() {
        weighted_graph c = new WGraph_DS(gr);
        return c;
    }

    //bfs

    /**
     * This method actually checks whether the graph we are working with is is connected.
     * That is, that there is a trajectory between all the vertices.
     * In this method I used BFS algorithm and checkVisit method.
     * In case there is a vertex from which there is no trajectory to the other vertices the method will recognize this.
     *
     * @return
     */
    @Override
    public boolean isConnected() {
        if (gr.nodeSize() <= 1)
            return true;
        else if (gr.nodeSize() - 1 > gr.edgeSize())
            return false;
        infini();
        Iterator<node_info> n = this.gr.getV().iterator();
        //send the key of the first node
        BFSc(n.next().getKey());
        return checkVisit();
    }

    /**
     * Checks if there is a vertex that we have not visited.
     *
     * @return
     */
    private boolean checkVisit() {
        for (node_info t : this.gr.getV()) {
            if (t.getTag() != 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Initializes the graph that the algorithm is working on.
     */
    private void BFSinit() {
        for (node_info n : gr.getV()) {
            n.setTag(0);
        }
    }

    /**
     * BFS algorithm, detailed in the Readme file, brief explanation,
     * Goes over the vertices of the graph, a vertex we were at changes it to 0 using TAG and using a queue I store all the vertices I visited.
     *
     * @param key
     */
    private void BFSc(int key) {

        node_info n = this.gr.getNode(key);
        Queue<node_info> q = new LinkedList<node_info>();
        q.add(n);
        n.setTag(1); //visit

        while (!q.isEmpty()) {
            n = q.poll();
            int y = n.getKey();
            y = y;
            // run on the nei of the node
            for (node_info l : gr.getV(n.getKey())) {
                int h = l.getKey();
                h = h;
                double m = l.getTag();
                m = m;
                m = m;
                if (l.getTag() != 1) {
                    l.setTag(1); //visit
                    q.add(l); // add the node to the queue
                }
            }
            //  n = q.poll(); // dequeue from the queue
        }
        System.out.println("here ");
    }

    //dijkstra

    /**
     * returns the length of the shortest path between src to dest
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        //
        if (!this.gr.getV().contains(gr.getNode(src)) || !this.gr.getV().contains(gr.getNode(dest))) return -1;
        if (src == dest) return 0;
        //
        infini();
        Queue<node_info> q = new LinkedList<node_info>();
        node_info srcN = gr.getNode(src);
        srcN.setTag(0);
        srcN.setInfo("" + srcN.getKey());
        Compy comperaTor = new Compy();
        q.add(srcN);
        //check the queue is not empty and if not visit in node dest
        while (!q.isEmpty()) {//&&gr.getNode(dest).getTag()==Double.MAX_VALUE){
            System.out.println(q.toString());
            //q.stream().sorted(comperaTor);
            System.out.println(q.toString());
            node_info prevNode = q.poll();//give the first from the q
            int f = prevNode.getKey();
            f = f;
            double k = prevNode.getTag();
            k = k;
            for (node_info Neicurr : gr.getV(prevNode.getKey())) {
                int b = Neicurr.getKey();
                f = f;
                double e = Neicurr.getTag();
                e = e;
                //check unvisited
                double weightCurr = prevNode.getTag() + gr.getEdge(prevNode.getKey(), Neicurr.getKey());
                // if min weight from the prev nodes to current node
                if (Neicurr.getTag() > weightCurr) {
                    Neicurr.setTag(weightCurr);
                    Neicurr.setInfo(prevNode.getInfo() + " " + Neicurr.getKey());
                    q.add(Neicurr);
                }


            }
            //for each on the nei of curr and check the wight

        }

        if (gr.getNode(dest).getTag() == Double.MAX_VALUE) {
            return -1;
        }


        return gr.getNode(dest).getTag();


    }

    private void infini() {
        for (node_info i : gr.getV()) {
            i.setTag(Double.MAX_VALUE);

        }


    }

    /**
     *returns the the shortest path between src to dest - as an ordered List of nodes:
     *      * src--> n1-->n2-->...dest
     *      * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     *      using dijkstra algorithem
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
       //init graphAlgo to use in my graph
        WGraph_Algo g2 = new WGraph_Algo();
        g2.init(gr);
        // call to function to find the path and add mataData "info" on the node- his path

        double w = shortestPathDist(src, dest);
        // create a list that describe the path from sec to dest
        List<node_info> shortList = new LinkedList<node_info>();
        //get the path from dest Node
        String path = gr.getNode(dest).getInfo();

        String[] splitPath = path.split(" ");
        //for each arr to add to the list
        for (String p : splitPath) {
            int key = Integer.parseInt(p);
            shortList.add(gr.getNode(key));
        }
        return shortList;
    }

    @Override
    public boolean save(String file) {
        try {
            OutputStream outStream = new FileOutputStream(file);
            ObjectOutputStream ObjectOutStream = new ObjectOutputStream(outStream);
            ObjectOutStream.writeObject(this.gr);
            ObjectOutStream.close();
            outStream.close();

        } catch (IOException e) {

            throw new RuntimeException("Error: Can't save the graph to file");

        }
        return true;
    }

    @Override
    public boolean load(String file) {
        try{
            FileInputStream myFile = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(myFile);
            weighted_graph g = (weighted_graph)ois.readObject();
            this.init(g);
            System.out.println("succses load graph");
            ois.close();
            myFile.close();
        }
        catch (Exception error){
            error.getMessage();
            return false;
        }
        return true;
    }



}

class Compy implements Comparator<node_info> {

    //sort by comp
    @Override
    public int compare(node_info o1, node_info o2) {
        return Double.compare(o1.getTag(), o2.getTag());
    }
}