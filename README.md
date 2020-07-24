# Genderer
Java library to determine gender or vocative form name by name in Czech language.

## Usage
Two classes provides methods which search passed name in tables, returns most occurrence case.

### Genderer class
Methods returns gender. Examples:
```
firstName("Petr") &nbsp; // Returns String "MALE".
firstNameAndSurname("Petra" "Nováková") &nbsp; // Returns String "FEMALE".
```

### Inflectioner class
Methods returns vocative form name. Examples:
```
surname("Procházková") &nbsp; // Returns String "Procházková".
firstNameAndSurname("Tomáš" "Kučera") &nbsp; // Returns String "Tomáši Kučero".
```

## Data source
Data source comes from the government official database ([MVCR](https://www.mvcr.cz/)) with contributing of authors working on Genderer project.
