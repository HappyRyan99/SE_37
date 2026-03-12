# Application Guide

## Build Instructions

1. Ensure that you have Java Development Kit (JDK 8 or later) installed on your system.
2. Download or clone the project repository to your local machine.
3. Open a terminal or command prompt and navigate to the root folder of the project.
4. Compile the project using the following command:
   ```bash
   javac -d out $(find src -name "*.java")
   ```

## Run Instructions

1. After building the application, navigate to the `out` directory:
   ```bash
   cd out
   ```
2. Start the application by running the `MainController` class:
   ```bash
   java org.softwareeng.group37.controller.MainController
   ```

## User Guide

### Key Features:

- **User Management**: Register and manage user accounts for system access
- **Teacher Management**: Register new teachers, view teacher lists, and query specific teacher information
- **Teacher Training**: Provide training opportunities to teachers to enhance their skill sets
- **Skill Management**: Add new skills and view all available skills in the system
- **Teaching Requirements**: Create teaching requirements, assign teachers to fulfill requirements, and track all requirements
- **Secure Login**: User authentication system to ensure secure access to the application
- **Logout & Exit**: Safely exit the application (this saves the data) or return to login for account switching

### Usage Tips:

- When prompted for input, follow the instructions and press Enter after providing your response.
- Ensure the required data files (e.g., `skills.csv`) are placed in the appropriate directory as referenced in the code.
