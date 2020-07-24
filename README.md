# Genderer
Java library to determine gender or vocative form name by name in Czech language.

## Usage
Two classes provides methods which search passed name in tables, returns most occurrence case.

### Genderer class
Methods returns gender. Returned value is Gender enumeration. Examples:
```
firstName("Petr") &nbsp; // Returns Gender "MALE".
firstNameAndSurname("Petra" "Nováková") &nbsp; // Returns Gender "FEMALE".
```

### Inflectioner class
Methods returns vocative form name. Examples:
```
surname("Procházková") &nbsp; // Returns String "Procházková".
firstNameAndSurname("Tomáš" "Kučera") &nbsp; // Returns String "Tomáši Kučero".
```

## Data source
Data source comes from the government official database ([MVCR](https://www.mvcr.cz/)) with contributing of authors working on Genderer project.
