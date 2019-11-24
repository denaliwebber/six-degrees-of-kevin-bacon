# six-degrees-of-kevin-bacon
Programmer: Denali Webber

Background:

Six Degrees of Kevin Bacon is a knowledge game based on the “small world” or “six degrees of
separation” concept. It is possible for a large network to be linked by a limited number of steps through
one or more who are well-connected. As it turns out, Kevin Bacon may be less prolific than others —
Meryl Streep or Ben Kingsley, for example — so this game may belie the biases of the inventors

Program: 
The point of this program is to find the shortest possible path between two user inputted actors/actresses.

How to compile: 
javac A3.java

How to run: 
java A3 /tmp/data_files/tmdb_5000_credits.csv

Sample output:
Actor 1 name: David Guy Brizan
No such actor.
Actor 1 name: Hailee Steinfeld
Actor 2 name: Abigail Breslin
Path between Hailee Steinfeld and Abigail Breslin: Hailee Steinfeld -->
Abigail Breslin
Actor 1 name: Asa Butterfield
Actor 2 name: Paul Dano
Path between Asa Butterfield and Paul Dano: Asa Butterfield --> Abigail
Breslin --> Paul Dano

