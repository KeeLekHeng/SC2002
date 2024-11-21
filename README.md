# Hospital Management System (HMS)
This is our group assignment for SC2002 (Object Oriented Programming).

# Setup Instructions
## Project Folder Structure
> Folder structure of our project.

### Top Level Directory Layout
```
.
├── declarations               # Individual GAI Declarations
├── diagram                    # UML Class Diagram
├── javadoc                    # Javadocs generated as html
├── report                     # Main Report
├── src                        # Source files (all the codes)
├── LICENSE
└── README.md
```
### Source Files
```
.
├── ...
├── src                       # Source files (all the codes)
│   ├── controller            # Managers classes
│   ├── database              # Database classes
│   ├── helper                # Helper classes
│   ├── model                 # Model classes
│   ├── view                  # View/Interface classes
│   └── HospitalApp.java      # Main Driver file (HMS App)
└── ...
```

## Scripts
> How to run our project
1. In your command line change directory into src  
```
cd /Users/maeganliew/Desktop/Hospital/src
```  
2. Compile the java files using command line
```
javac -d bin src/controller/*.java src/database/*.java src/helper/*.java src/model/*.java src/view/*.java src/HospitalApp.java
```  
3. Run the java file using command line
```
java './src/HospitalApp.java'
```

# Java docs
Create javadocs - make sure you are at "Hospital" directory
```
javadoc -d ./javadocs/ ./src/controller/*.java ./src/database/*.java ./src/helper/*.java ./src/model/*.java ./src/view/*.java ./src/HospitalApp.java -encoding ISO-8859-1
```  
Launch the index.html under ./javadoc/index.html  

# Contributors
@Gentleseann  
@KeeLekHeng  
@BenjaminKam  
@maeganliew  
@xbxrnx  
