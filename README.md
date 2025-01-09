# Contacts Management Application
This is a Java desktop application for managing a list of contacts. The application uses a SQLite database to store contact information and provides a graphical user interface (GUI) built with Swing for interaction.
## Features
- **View Contacts**: Displays a list of all saved contacts.
- **Add New Contacts**: Allows users to add new contacts via a user-friendly form.
- **Delete Contacts**: Enables users to delete selected contacts using a key press (```d```).
- **View Contact Details**: Double-click on a contact to view detailed information.
- **Database Integration**: Uses SQLite for storing and retrieving contact data.
## Getting Started
### Prerequisites
To run this project, ensure you have the following installed on your system:

- Java Development Kit (JDK) 8 or higher
- SQLite JDBC Driver (included in the project)
### Installation
1. Clone the repository:

```bash
git clone https://github.com/yourusername/contacts-management-app.git
cd Phone_book
```
2. Add the required SQLite database file:

- Place the identifier.sqlite file in the project root directory.
- Ensure the database contains a contacts table with the following schema:
```sql
CREATE TABLE contacts(
    id       INTEGER PRIMARY KEY AUTOINCREMENT ,
    name     TEXT          NOT NULL,
    surname  TEXT          NOT NULL,
    nickname TEXT          NOT NULL,
    number   TEXT          NOT NULL
);
```
3. Build the project using your preferred IDE or the command line:

```bash
javac -cp .:sqlite-jdbc.jar *.java
```

4. Run the application:

```bash
java -cp .:sqlite-jdbc.jar AppStarter
```
## Usage
1. **Viewing Contacts**:
- The main window displays a table of contact nicknames. Use the table to select a contact.

2. **Viewing Contact Details**:
- Double-click a nickname in the table to view detailed information about the contact on the right panel.

3. **Adding a New Contact**:
- Click the "AddCont" button to open the "Add Contact" dialog.
- Fill in the contact's name, surname, nickname, and phone number, then click "Save".

4. **Deleting a Contact**:
- Select a contact in the table, then press the d key to delete it.

**Key Shortcuts**:

**Double-click**: View contact details

```d``` **key**: Delete the selected contact
## Code Overview
The application consists of the following key components:

1. ```DBHandler```: Handles database operations like retrieving, adding, and deleting contacts. It also establishes a connection to the SQLite database.

2. ```AppFrame```: The main application frame. It contains the contact table, details panel, and buttons for user interaction.

3. ```InfoPanel```: Displays detailed information about a selected contact, such as name, nickname, and phone number.

4. ```AppStarter```: The entry point for launching the application.

## Example Database Connection
```java
String connString = "jdbc:sqlite:identifier.sqlite";
connection = DriverManager.getConnection(connString);
```
**Adding a New Contact**
```java
String sql = "INSERT INTO contacts (name, surname, nickname, number) VALUES (?, ?, ?, ?)";
PreparedStatement stat = connection.prepareStatement(sql);
stat.setString(1, name);
stat.setString(2, surname);
stat.setString(3, nickname);
stat.setString(4, phone);
stat.executeUpdate();
```
## Technologies Used
- **Java**: Core programming language
- **Swing**: For building the graphical user interface
- **SQLite**: Database for storing contact information
- **Lombok**: Simplifies boilerplate code (e.g., ```@Getter``` and ```@Setter```)
- **Logback (via SLF4J)**: For logging application events
