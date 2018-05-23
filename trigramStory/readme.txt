A readme for the Trigram class for Java

Created Winter 2017 for Artificial Intelligence and Knowledge Acquisition

The program takes several public domain works (Alice in Wonderland, The Adventures of Sherlock Holmes, The Casebook of Sherlock Holmes, Call of the Wild, Billy Budd, and the Adventures of Tom Sawyer) and parsing each text in order to create trigrams. The program stores the trigrams in a HashMap using a String as the key and a Linkedlist containing the options as a value. Finally we create a new 1000 word text by randomly selecting words and using the trigrams to choose follow up words. They are randomly selected in proportion to how frequently the words occur.
