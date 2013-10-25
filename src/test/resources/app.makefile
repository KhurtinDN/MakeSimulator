
# Clean temp files
clean:
 rm -f Solution.class
 rm -f classes.jar

# Compile java files
compile:
 javac Solution.java

# Build jar archive
package: compile
 jar cf classes.jar Solution.class

# Run command
run: compile
 java Solution

# clean and package
rebuild: clean package
