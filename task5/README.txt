wsl:
javac -h . ImageC.java
g++ -c -fPIC -I /usr/lib/jvm/java-17-openjdk-amd64/include/ -I /usr/lib/jvm/java-17-openjdk-amd64/include/linux/ ImageC.cpp -o ImageC.o
g++ -shared -fPIC -o libimage.so ImageC.o -lc
javac MainCompare.java
java -cp . -Djava.library.path=/mnt/c/Users/MarkB/projects/ProjectsIdea/optimization_java/task5/src/main/java MainCompare

javac -h . ImageC.java && g++ -c -fPIC -I /usr/lib/jvm/java-17-openjdk-amd64/include/ -I /usr/lib/jvm/java-17-openjdk-amd64/include/linux/ ImageC.cpp -o ImageC.o && g++ -shared -fPIC -o libimage.so ImageC.o -lc && javac MainCompare.java && java -cp . -Djava.library.path=/mnt/c/Users/MarkB/projects/ProjectsIdea/optimization_java/task5/src/main/java MainCompare
javac -h . ImageC.java && g++ -c -fPIC -I /usr/lib/jvm/java-17-openjdk-amd64/include/ -I /usr/lib/jvm/java-17-openjdk-amd64/include/linux/ ImageCInvert.cpp -o ImageC.o && g++ -shared -fPIC -o libimage.so ImageC.o -lc && javac MainCompare.java && java -cp . -Djava.library.path=/mnt/c/Users/MarkB/projects/ProjectsIdea/optimization_java/task5/src/main/java MainCompare

javac -h . ImageC.java && g++ -c -fPIC -I /usr/lib/jvm/java-17-openjdk-amd64/include/ -I /usr/lib/jvm/java-17-openjdk-amd64/include/linux/ ImageC.cpp -o ImageC.o && g++ -shared -fPIC -o libimage.so ImageC.o -lc && javac Main.java && /usr/bin/time -v java -cp . -Djava.library.path=/mnt/c/Users/MarkB/projects/ProjectsIdea/optimization_java/task5/src/main/java Main

run:
javac MainCompare.java
java MainCompare

javac MainCompare.java && java MainCompare