import java.util.*;

public class Graph
{
    /* 
        HashMaps:
         castGraph - key: an actor, value: a set of neighbor actors they have directly worked with
         actorToID - key: an actor, value: a unique ID
         idToActor - key: a unique ID, value: an actor
         caseCorrect - key: lower case actor name, value: case correct actor name
        maxID:
         represents the possible highest unique ID provided by the csv file
    */
    Map<String, Set<String>> castGraph;
    Map<String, Integer> actorToID;
    Map<Integer, String> idToActor;
    Map<String, String> caseCorrect;
    int maxID;

    public Graph()
    {
        castGraph = new HashMap<>();
        actorToID = new HashMap<>();
        idToActor = new HashMap<>();
        caseCorrect = new HashMap<>();
        maxID = 0;
    }

    public boolean containsActor(String actor)
    {
        if (castGraph.containsKey(actor))
            return true;
        return false;
    }

    private boolean isEdge(String a1, String a2)
    {
        return castGraph.get(a1).contains(a2);
    }

    public void addActor(String actor, int id)  //adds given actor and id to the instance variable HashMaps
    {
        caseCorrect.put(actor.toLowerCase(), actor);

        String actorLower = actor.toLowerCase();

        castGraph.put(actorLower, new HashSet<>());
        actorToID.put(actorLower, id);

        idToActor.put(id, actorLower);
        if (id > maxID)
            maxID = id;
    }

    public void addEdge(String a1, String a2)   //adds actors to each others neighbor sets
    {
        castGraph.get(a1).add(a2);
        castGraph.get(a2).add(a1);
    }

    public LinkedList<String> shortestPath(String a1, String a2)    //returns a list with the shortest path between actors
    {
        //a queue to keep track of the vertices whose neighbor sets still need to be checked
        Queue<String> queue = new LinkedList<String>();
        //an array to mark if a vertex has already been visited
        boolean visited [] = new boolean[maxID+1];
        int distances [] = new int[maxID+1];
        int path [] = new int[maxID+1];
        LinkedList<String> list = new LinkedList<String>();
        int v = 0;

        for (int i = 0; i < (maxID+1); i++)
        {
            visited[i] = false;
            distances[i] = Integer.MAX_VALUE;
            path[i] = -1;
        }

        list.add(caseCorrect.get(a1));
        queue.add(a1);
        int a1IdNum = actorToID.get(a1);
        distances[a1IdNum] = 0;
        if (!a1.equals(a2))
        {
            while (!queue.isEmpty())
            {
            String actor = queue.remove();
            int actorID = actorToID.get(actor);
            visited[actorID] = true;
            
            Iterator it = castGraph.get(actor).iterator();
            while (it.hasNext())
            {
                String neighbour = (String) it.next();
                int neighbourID = actorToID.get(neighbour);
                if (visited[neighbourID] == false)
                {
                    if (distances[neighbourID] > (distances[actorID] + 1))
                    {
                        distances[neighbourID] = distances[actorID] + 1;
                        path[neighbourID] = actorID;

                        if (!queue.contains(neighbour))
                        {
                            queue.add(neighbour);
                        }
                    }
                    if (neighbour.equals(a2))
                    {
                        int index = actorToID.get(a2);
                        while(index != a1IdNum)
                        {
                            int idNum = path[index];
                            String act = idToActor.get(idNum);
                            if (act.equals(a1)==false)
                            {
                                list.add(caseCorrect.get(act));
                            }
                            
                            index = idNum;
                        }
                        list.add(caseCorrect.get(a2));
                        return list;
                    }
                }
            }
        }
        }
        return list;
    }
}