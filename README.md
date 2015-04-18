# Ponds and Islands

This program pulls a map of X's and .'s from a text file. The X's correspond to land and the .'s correspond to water. When the program "flood fills" the map, it uses recursion to check for "islands" and "ponds". Islands are masses of land that are connected. A block of land is connected to any other block of land that is in any of the 8 spaces around it. Ponds are masses of water that are connected. A block of water is connected to any other block of water that is above, below,  or to the sides of it. The flood fill detects for these and replaces them with characters corresponding to a single mass.

Made for APCS 2013.