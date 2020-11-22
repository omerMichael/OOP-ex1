package ex1.src;

import ex0.node_data;

import java.io.Serializable;
import java.util.*;

public class WGraph_DS implements weighted_graph , Serializable {
    int key;
    HashMap<Integer, HashMap<Integer,Double>> weight = new HashMap<Integer, HashMap<Integer,Double>>();
    HashMap<Integer, HashMap<Integer,node_info>> Nei = new HashMap<Integer, HashMap<Integer,node_info>>();
    HashMap<Integer, node_info> nodes = new HashMap<Integer, node_info>();

    private int MC=0;
    private int numOfEd = 0;

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        return key == wGraph_ds.key &&
                MC == wGraph_ds.MC &&
                numOfEd == wGraph_ds.numOfEd &&
                Objects.equals(weight, wGraph_ds.weight) &&
                Objects.equals(Nei, wGraph_ds.Nei) &&
                Objects.equals(nodes, wGraph_ds.nodes);
    }


    /**
     * Default constructor
     */
    public WGraph_DS(){

    }
    //copy constructor

    /**
     * copy constructor
     * @param other
     */
    public WGraph_DS(weighted_graph other){
        this.MC = other.getMC();
        this.numOfEd = other.edgeSize();
        // now copy hashMap of nodes graph
        // O(V+E)
        for (node_info i:other.getV()) {
            this.nodes.put(i.getKey(),i);
           //for each, each node run on all his nei
            //Complex O(E)
            for (node_info j: other.getV(i.getKey())) {
                //now copy hashMap of weight
                double wight = other.getEdge(i.getKey(),j.getKey());
                this.weight.get(i.getKey()).put(j.getKey(),wight);
                this.weight.get(j.getKey()).put(i.getKey(),wight);
                //now copy hashMap of Nei
                this.Nei.get(i.getKey()).put(j.getKey(),j);
                this.Nei.get(j.getKey()).put(i.getKey(),i);

            }
        }





    }



   public static class nodeInfoIM implements node_info,Serializable{

        int key;
        double tag;
        String info;
//        private Collection<node_info> nei=new HashSet<>();
        //private static int count = 0;

       /**
        * toString
        * @return
        */
       @Override
       public String toString() {
           return "{" +
                   "key=" + key +
                   ", tag=" + tag +
                   '}';
       }

       @Override
       public boolean equals(Object o) {
           if (this == o) return true;
           if (o == null || getClass() != o.getClass()) return false;
           nodeInfoIM that = (nodeInfoIM) o;
           return key == that.key &&
                   Double.compare(that.tag, tag) == 0 &&
                   Objects.equals(info, that.info);
       }



       public nodeInfoIM(int key) {
            this.key =key;
        }


       /**
        * Return the key (id) associated with this node.
        * @return
        */
        @Override
        public int getKey() {
            return this.key;
        }

       /**
        * return the remark (meta data) associated with this node.
        * @return
        */
        @Override
        public String getInfo() {
            return this.info;
        }

        /**
         *Allows changing the remark (meta data) associated with this node.
         */
        @Override
        public void setInfo(String s) {
            this.info = s;
        }

       /**
        * Temporal data (aka distance, color, or state)
        * which can be used be algorithms
        * @return
        */
        @Override
        public double getTag() {
            return this.tag;
        }

       /**
        *Allow setting the "tag" value for temporal marking an node - common
        *practice for marking by algorithms.
        * @param t - the new value of the tag
        */
        @Override
        public void setTag(double t) {
            this.tag = t;
        }
    } //end of inner class node_info


    /**
     * return the node_data by the node_id
     * @param key - the node_id
     * @return
     */
    @Override
    public node_info getNode(int key) {

          return this.nodes.get(key);
    }

    /**
     *return true iff (if and only if) there is an edge between node1 and node2
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
//        if (!nodes.containsKey(node1) || !nodes.containsKey(node1) )
//    return false;

//        node_info n1 = nodes.get(node1);
//        node_info n2 = nodes.get(node2);

        if (!Nei.containsKey(node1)|| !Nei.containsKey(node2)) return false;
//        System.out.println("nei is: "+Nei.get(n1.getKey()));
        return Nei.get(node1).containsKey(node2) || Nei.get(node2).containsKey(node1);
    }

    /**
     * return the weight if the edge (node1, node1). In case
     * there is no such edge - should return -1
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (!(hasEdge(node1,node2))) return -1;
       double w = this.weight.get(node1).get(node2);
        return w;
    }

    /**
     * add a new node to the graph with the given key.
     * @param key
     */
    @Override
    public void addNode(int key) {
        if(!nodes.containsKey(key)) {
            node_info n1 = new nodeInfoIM(key);
            nodes.put(key, n1);
            HashMap<Integer, node_info> Nihash = new HashMap<>();
            Nei.put(key, Nihash);
            HashMap<Integer, Double> Weihash = new HashMap<>();
            weight.put(key, Weihash);
        }
    }

    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     * @param node1
     * @param node2
     * @param w
     */
    @Override
    public void connect(int node1, int node2, double w) {
//        System.out.println("conncet" + node1 + " to " + node2);
        if(node1!=node2&&this.getNode(node1)!=null&&this.getNode(node2)!=null) {
            if (hasEdge(node1, node2)) {
                weight.get(node1).replace(node2, w);
                weight.get(node2).replace(node1, w);
                MC++;
            } else{
                node_info n1 = this.getNode(node1);
                node_info n2 = this.getNode(node2);
                // Now the problem after key 3 this.Nei.get(node1) is null
                // meybe the problem in the addNode
                this.Nei.get(node1).put(node2, n2);
                this.Nei.get(node2).put(node1, n1);
                this.weight.get(node1).put(node2, w);
                this.weight.get(node2).put(node1, w);


                this.numOfEd++;
                numOfEd=numOfEd;
                MC++;

            }
        }

    }

    /**
     * This method return a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     * @return
     */
    @Override
    public Collection<node_info> getV() {
        return nodes.values();
    }

    /**
     * This method returns a Collection containing all the
     * nodes connected to node_id
     * Note: this method can run in O(k) time, k - being the degree of node_id.
     * @param node_id
     * @return
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        return this.Nei.get(node_id).values();
    }

    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(n), |V|=n, as all the edges should be removed.
     * @return the data of the removed node (null if none).
     * @param key
     * @return
     */
    @Override
    public node_info removeNode(int key) {
        if(this.nodes.containsKey(key)) {
            Iterator<node_info> i=getV(key).iterator();

           while(i.hasNext()){
               node_info t=i.next();
                removeEdge(key,t.getKey());
                i=getV(key).iterator();
            }
            Nei.remove(key, Nei.get(key));
            weight.remove(key, weight.get(key));
            node_info nodeI = getNode(key);
            nodes.remove(key);
            return nodeI;
        }
        return  null;
    }

    /**
     * Delete the node (with the given ID) from the graph -
     *and removes all edges which starts or ends at this node.
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (hasEdge(node1,node2)&&node1!=node2) {

            Nei.get(node1).remove(node2,Nei.get(node1).get(node2));
            Nei.get(node2).remove(node1,Nei.get(node2).get(node1));
            weight.get(node1).remove(node2);
            weight.get(node2).remove(node1);
            numOfEd--;
            MC++;
        }

    }

    /**
     * return the number of vertices (nodes) in the graph.
     * @return
     */
    @Override
    public int nodeSize() {
        return this.nodes.size();
    }

    /**
     *return the number of edges (undirectional graph).
     * @return
     */
    @Override
    public int edgeSize() {
//        System.out.println("fun edgesize now is " +this.numOfEd);
        return this.numOfEd;
    }

    /**
     * return the Mode Count - for testing changes in the graph.
     * @return
     */
    @Override
    public int getMC() {
        return this.MC;
    }
}
