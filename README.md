# Genderer
Java library to determine gender or vocative form name by name in Czech language.

## Usage
Two classes provides methods which search passed name in tables, returns most occurrence case.

### Genderer class
Methods returns gender. Returned value is Gender enumeration. Examples:
```
firstName("Petr")  Returns Gender "MALE".
surname("Procházková")  Returns Gender "FEMALE".
firstNameAndSurname("Petra" "Nováková")  Returns Gender "FEMALE".
firstNameAndSurname("Jiří" "Svobodová")  Returns null.
firstNameAndSurname_preferFirstName("Jiří" "Svobodová")  Returns Gender "MALE".
firstNameAndSurname_preferSurname("Jiří" "Svobodová")  Returns Gender "FEMALE".
```

### Inflectioner class
Methods returns vocative form name. Examples:
```
firstName("Věra")  Returns String "Věro".
surname("Procházka")  Returns String "Procházko".
firstNameAndSurname("Tomáš" "Kučera")  Returns String "Tomáši Kučero".
firstNameAndSurname("Tomáš" "Xyz")  Returns String "Tomáši Xyz".
firstNameAndSurname_bothNamesVocative("Tomáš" "Xyz")  Returns null.
```

## Database queries execution needs
DatabaseConnection constructor receiving instrument to connect to database. This instrument should implement Database interface.

## Data source
Data source comes from the government official database ([MVCR](https://www.mvcr.cz/)) with contributing of authors working on Genderer project.
