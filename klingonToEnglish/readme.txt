A readme for the KlingonToEnglish class for Java

Created Summer 2017 for Intro to NLP

This project is composed of a training file, and four classes: Main, Transition, Emission, Decoding.

The Main class serves as the hub. If you were to want to test the whole program you would have to alter the String target in this class.

The Emission class is for creating an emission probability table. This counts every word in the training file and creates a table based on how often each word showed up with each part-of-speech tag. This table uses smoothing in order to ensure that no data is impossible.

The Transition class builds the transition probability table. This table counts and calculates the probability of each part-of-speech tag going to every other part-of-speech tag. As an example this table would display the probability that a Noun was followed by another Noun, Verb, etc.

The Decoding class uses the Viterbi algorithm and the previously generated tables to decode the likely part-of-speech tags of each word in the target sentence.

Possible improvements:
These four classes could be expanded further to better translate phrases. You could add a class to generate a word to english table and go from there to better have an estimate of the possible meanings, or at least the possible words used in the sentence.

The Emission and Transition table share enough methodsand functions that they would probably be better built inheriting from a class. Likely one called ProbTable.

