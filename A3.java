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
            //Read in CSV file by first splitting by commas, then parse the data formatted in JSON using JSON parse
            String splitByComma = ",\"";
            String fileName = args[0]; //"tmdb_5000_credits.csv";
            
            BufferedReader  reader = new BufferedReader(new FileReader(fileName));
            String l = "";

            try 
            {
                l = reader.readLine();
                while ((l = reader.readLine()) != null) 
                {
                    //blank casts
                    if(l.contains("[],[]"))
                    {
                        //skip over line
                        l = reader.readLine();
                    }
                
                    //build String array that holds all the info
                    String[] castinfo = l.split(splitByComma);
                    String cast = "";

                    //File had to be converted to a JSON Format, there were double quotes instead of single quotes. Replace doubles with singles
                    if(castinfo[1].contains("[{"))
                        cast = castinfo[1].replace("\"\"","\"");
                    //checks for quotes within movie names
                    else if(castinfo[2].contains("[{"))
                        cast = castinfo[2].replace("\"\"","\"");
                    //builds the Parse and JSON array
                    JSONParser parser = new JSONParser();
                    JSONArray castArray;
                    try
                    {
                        castArray  = (JSONArray) parser.parse(cast);
                    }
                    catch(Exception e) //catches Exceptions that were thrown because of weird data within the cast JSON
                    {
                        l = reader.readLine();
                        castinfo = l.split(splitByComma);
                        cast = "";
                            if(castinfo[1].contains("[{"))
                                    cast = castinfo[1].replace("\"\"","\"");
                            castArray  = (JSONArray) parser.parse(cast);
                    }

                    //Now we add the values into our graph
                    for(Object o : castArray) //Go through each name in cast
                    { 
                        JSONObject one = (JSONObject) o;
                        String name1 = ((String) one.get("name"));
                        
                        int id1 = Math.toIntExact((Long)(one.get("id")));
                        
                        //checks if Name exists in Map
                        if(!g.containsActor(name1.toLowerCase()))
                        {
                            g.addActor(name1, id1);
                        }

                        for(Object j : castArray) //builds edge to cast member 1
                        { 
                            JSONObject two = (JSONObject) j;
                            //convert everything to LowerCase so you do not have to worry about it within the PathFinders
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

            if (path.size()==1)
            {
                System.out.println(path.get(0));
            }

            else
            {
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
}