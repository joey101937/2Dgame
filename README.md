# Overview
Freshman Year Project, rts game that allows user to build vehciles and buildings, mine resources, and fight against an AI opponent
![Screenshot](http://i63.tinypic.com/2qcmhiu.png)
# Mechanics
## Pathing
All units in this game have pathfinders which allows them to navigate around the world. This pathfinder controls collision with other objects. Units also have a pathing layer: ground, air, or sea which determines which type of terrain they can navigate through and whether or not they may be targeted by certain projectiles. Projectiles have their own special pathfinders to allow them to get to their destination
## Terrain
Terrain maps in this game use an expandable tile based system, creating a grid of terrain types that comes together into a playable surface. Terrain types may be grass (land), sand (land), and water (sea) and each has differnt interactions with units' pathfinders. Additionally, terrain tiles may contain up to one of two types of resources: Iron and Oil. The yield type of a tile dictates what may be built in that location. For example, Oil Pumps must be built on oil-rich terrain while factories require open grass.
## Unit Mechanics
The game has 5 basic units and 4 basic structures. Tank units are basic land-based combat vehicles that will attack nearby enemies if able. Helicopters and Boats also exist and serve the same function except boats travel in water and have more health and helicopters can fly over other units and are able to traverse any terrain. Trucks are the builder unit and are used to build structures such as Iron Mines and Factories to generate resources and produce more units. Factories are able to create new units at the expense of resources. The amount is dependent on the cost of the unit to build. Once a unit begins construction. It is frozen by the factory until its production time is complete, after which it is able to be used normally.
## AI
The game features an AI opponent to play against. This opponent is able to make decisions about what to build, where to send its units, and react to the player's actions. AI will consider amount of units, type of units (both sides), where units are, and resources available to make the optimal play against the human user. Players can edit the settings of the AI to alter its playstyle and play speed to create different challenges and difficulties.
