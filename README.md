# Manea Dorin-Mihai

# 323CA

# OOP Project - POO TV

## Overview

This project consists of a backend for a movie streaming service. It is
implemented in Java and is modularized into multiple packages:

- *input* - contains classes for reading data from files;
- *output* - contains classes for writing data to files;
- *actions* - contains classes for executing actions;
- *constants* - defines enums used in the code.

Input and output are parsed using JSON files. The output consists of an
ArrayList of objects of type ObjectMessage, that display information such as
the error status, the current user and the current movies list available. The
user can commute between pages and realize several on page actions, based on
which page they are at the moment:

- *login* - the user can log in with their username and password;
- *register* - the user can register into the database;
- *see details* - the user can do operations on the selected movie;
- *purchase* - the user can purchase a movie;
- *watch* - the user can watch a movie;
- *like* - the user can like a movie;
- *rate* - the user can rate a movie on a scale from 1 to 5;
- *search* - the user can search for a movie that starts with a given string;
- *filter* - the user can filter movies based on given genres and actors or
  sort them in ascending or descending orders based on their durations and
  ratings;
- *buy tokens* - the user can buy tokens;
- *buy premium account* - the user can upgrade to a premium account, that
  allows them to watch 15 movies for free and get a movie recommendation;
- *subscribe* - the user can subscribe to a movie genre, that will notify them
  when a new movie that belongs to the genre is added to the database;
- *back* - the logged-in user can go back to the previous page;

Additionally, the administrator can add or remove new movies to the database at
any point in time. Users subscribed to a genre will be notified when a new movie
that belongs to the genre is added to the database. Also, users that have
previously purchased a movie that is being removed from the database will be
notified and will be refunded.

NB:

1. The user can only watch a movie if they have purchased it.
2. The user can only like or rate a movie if they have watched it.
3. The user has access only to movies not banned in their country.

Throughout the project, I have made use of every fundamental OOP concept:
inheritance, polymorphism (overriding and overloading), encapsulation and
abstraction (abstract classes and interfaces). They have been incorporated into
the following design patterns: Singleton and Visitor.

The Output class is a Singleton, as it is used to write data to files. In order
to avoid race conditions, the class is synchronized. Besides, I have considered
the Visitor pattern ideal for the implementation of the actions, as it allows
the separation of the logic from the data and the action classes are not
modified.

For storing current data in the program, I used several collections: ArrayList,
Hashmap, LinkedHashMap and ArrayDequeue. The hashmap is useful for memorising
all the ratings for every movie. The LinkedHashMap, together with deep copy
constructors and synchronized variants for lists, are used for dealing with
multithreading issues encountered during the implementation of the project.
The ArrayDequeue acts as a stack that stores the pages the user navigates
through and is useful for the back action.

After careful thought as to how to notify users everytime a new change is being
made to the database, I decided to use the Observer pattern. The Observable is
the suggestively named NotificationCenter class and the Observers are none
others than the users themselves (the UserExtended class). In order to manage
all the different aspects users have to be notified of and since users shall be
notified only of the changes important to them, I chose to implement a Hashmap
that retains multiple Observable objects, each one corresponding to a different
String (the name of the genre for the addition of a movie to the database and
the name of the movie for the removal of a movie from the database).

## Bibliography

1. [OCW OOP](https://ocw.cs.pub.ro/courses/poo-ca-cd/laboratoare)
