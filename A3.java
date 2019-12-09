import java.util.*;
import java.io.*;
import java.lang.*;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class A3
{
	public static void main(String [] args) throws Exception
	{
        if (args.length < 1 || args.length >= 2)
            System.out.println("Invalid command line input.\nPlease run with file name and location.");
        else
        {
            Graph g = new Graph();
            Scanner scan = new Scanner(System.in);
            
            String splitByComma = ",\"";
            String fileName = args[0]; //tmdb_5000_credits.csv and path
            
            //read in the tmbd CSV file
            BufferedReader  reader = new BufferedReader(new FileReader(fileName));
            String l = "";

            try 
            {
                l = reader.readLine();
                while ((l = reader.readLine()) != null) 
                {
                    //if line is a blank casts
                    if(l.contains("[],[]"))
                    {
                        //skip over line
                        l = reader.readLine();
                    }
                
                    //array to holds all the info separated by commas
                    String[] castinfo = l.split(splitByComma);
                    String cast = "";

                    //replace double quotes with single quotes to support the JSON format and parser
                    if(castinfo[1].contains("[{"))
                        cast = castinfo[1].replace("\"\"","\"");
                    
                    JSONParser parser = new JSONParser();
                    JSONArray castArray;
                    try
                    {
                        castArray  = (JSONArray) parser.parse(cast);
                    }
                    catch(Exception e) //catches any Exceptions thrown because of unexpected data within the file
                    {
                        //skips over line and creates the castArray
                        l = reader.readLine();
                        castinfo = l.split(splitByComma);
                        cast = "";
                            if(castinfo[1].contains("[{"))
                                    cast = castinfo[1].replace("\"\"","\"");
                        castArray  = (JSONArray) parser.parse(cast);
                    }

                    for(Object o : castArray) //Go through each name and ID number in cast
                    { 
                        JSONObject one = (JSONObject) o;

                        String name1 = ((String) one.get("name"));  //extracts name of actor
                        
                        int id1 = Math.toIntExact((Long)(one.get("id")));   //extracts the actor's id number
                        
                        //checks if name exists in HashMap
                        if(!g.containsActor(name1.toLowerCase()))
                        {
                            g.addActor(name1, id1);
                        }

                        for(Object j : castArray) //adds an edge between actor and all other actors in the movie
                        { 
                            JSONObject two = (JSONObject) j;
                            
                            String name2 = ((String) two.get("name"));
                            int id2 = Math.toIntExact((Long)two.get("id"));
                        
                            if(!g.containsActor(name2.toLowerCase()))
                            {
                                g.addActor(name2, id2);
                            }

                            g.addEdge(name1.toLowerCase(), name2.toLowerCase());
                        }
                    }
                }
            }

            catch (FileNotFoundException e) 
            {
                e.printStackTrace();
            }

            System.out.print("Actor 1 name: ");
            String a1 = scan.nextLine();
            while (!g.containsActor(a1.toLowerCase()))
            {
                System.out.println("No such actor.");
                System.out.print("Actor 1 name: ");
                a1 = scan.nextLine();
            }

            System.out.print("Actor 2 name: ");
            String a2 = scan.nextLine();
            while (!g.containsActor(a2.toLowerCase()))
            {
                System.out.println("No such actor.");
                System.out.print("Actor 2 name: ");
                a2 = scan.nextLine();
            }

            List<String> path = g.shortestPath(a1.toLowerCase(), a2.toLowerCase());

            System.out.print("Path between "+path.get(0)+" and "+path.get(path.size()-1)+": ");

            for (int i=0; i<path.size(); i++)
            {
                System.out.print(path.get(i));
                if (i != path.size()-1)
                   System.out.print(" --> ");
            }
            System.out.println();
        }
    }
}