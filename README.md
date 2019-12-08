# six-degrees-of-kevin-bacon
Programmer: Denali Webber

Background:
Six Degrees of Kevin Bacon is a knowledge game based on the “small world” or “six degrees of
separation” concept. It is possible for a large network to be linked by a limited number of steps through
one or more who are well-connected.

Program: 
The point of this program is to find the shortest possible path between two user inputted actors/actresses.

Notes:
This program reads in data from the file tmdb_5000_credits.csv, which is available for downloads through the Kaggle's TMDB 5000 Movie Dataset: https://www.kaggle.com/tmdb/tmdb-movie-metadata#
In order to download this file, you will need credentials for the Kaggle site. See below for how to give the path to the file when executing this program on the command line.
In order to compile and run program you must have JSON-simple installed to parse the tmdb_5000_credits.csv file. You can follow this guide on how to download and set up JSON simple on your device: https://www.tutorialspoint.com/json_simple/json_simple_quick_guide.htm
Program is not case sensitive, so although the actors names don't have to have the correct capitalization, they do need to have the right spelling.

How to compile: 
javac A3.java

How to run: 
java A3 /tmp/data_files/tmdb_5000_credits.csv

Sample output:
Actor 1 name: Haley Steinfeld
No such actor.
Actor 1 name: Hailee Steinfeld
Actor 2 name: Abigail Breslin
Path between Hailee Steinfeld and Abigail Breslin: Hailee Steinfeld --> Abigail Breslin

Actor 1 name: Asa Butterfield
Actor 2 name: Paul Dano
Path between Asa Butterfield and Paul Dano: Asa Butterfield --> Viola Davis --> Paul Dano
