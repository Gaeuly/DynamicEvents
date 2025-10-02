# DynamicEvents

[![Java](https://img.shields.io/badge/Java-8-orange.svg?style=flat-square)](https://www.java.com/)

## Project Description 📝

DynamicEvents is a Minecraft plugin designed to introduce unpredictable and exciting world events into the game. This plugin aims to enhance the player experience by creating dynamic gameplay scenarios that require adaptation and collaboration. Instead of relying on predictable, static world conditions, servers using DynamicEvents can offer players a constantly evolving and challenging environment.

This plugin allows server administrators to configure and control a range of events, from meteor showers to custom-defined occurrences. These events can be triggered randomly or scheduled based on configurable parameters, offering a high degree of flexibility and customization. By adding a layer of unpredictability, DynamicEvents breathes new life into Minecraft servers, preventing monotony and fostering a sense of community as players react and strategize together.

DynamicEvents solves the problem of static gameplay by providing a framework for creating and managing a variety of in-game events. It is ideal for server owners looking to engage their player base, increase player retention, and create memorable experiences. The plugin is currently in BETA TESTER phase, meaning feedback and contributions are highly valued to ensure its stability and effectiveness.

## Key Features ✨

*   **Dynamic Event Triggering**: Introduces random and scheduled world events, keeping gameplay fresh and exciting.
*   **Configurable Events**: Allows server administrators to customize event parameters, such as frequency, duration, and intensity.
*   **Custom Event Creation**: Provides a framework for creating new and unique events, expanding the plugin's capabilities beyond the default options.
*   **Event API**: Enables other plugins to interact with DynamicEvents, fostering integration and expanding possibilities.
*   **Real-time Event Management**: Provides in-game commands for controlling and managing events, giving server operators immediate control over the game world.

## Tech Stack & Tools 🛠️

| Category | Technology/Tool      | Description                                                              |
| :------- | :------------------- | :----------------------------------------------------------------------- |
| Language | Java                 | The primary programming language used for the plugin.                     |
| Build    | Maven                | Used for dependency management and building the plugin.                   |
| API      | Bukkit/Spigot        | Minecraft server API for plugin development.                            |
| Config   | YAML                 | Used for configuration files (config.yml, message.yml).                   |
| IDE      | IntelliJ IDEA/Eclipse | (Inferred) Likely used for development.                                 |

## Installation & Running Locally 🚀

This guide assumes you have a Minecraft server running Spigot or Paper, and that you have basic familiarity with plugin installation.

**Prerequisites:**

*   Java 8 or higher
*   Maven

**Steps:**

1.  **Clone the repository:**

    ```bash
    git clone https://github.com/Gaeuly/DynamicEvents.git
    ```

2.  **Navigate to the project directory:**

    ```bash
    cd DynamicEvents
    ```

3.  **Build the project using Maven:**

    ```bash
    mvn clean install
    ```

    This will create a `.jar` file in the `target/` directory.

4.  **Copy the `.jar` file to your Minecraft server's `plugins/` directory.**

5.  **Start your Minecraft server.**

    The plugin will load automatically. You can then configure the plugin using the `config.yml` file located in the `plugins/DynamicEvents/` directory. The message.yml is for customized messages.

6.  **(Optional) Restart your Minecraft server.**

    Some configuration changes may require a server restart to take effect.

## How to Contribute 🤝

Contributions to DynamicEvents are welcome! Please submit pull requests with bug fixes, new features, or improvements to existing code. When contributing, please follow these guidelines:

*   Adhere to the project's coding style.
*   Write clear and concise commit messages.
*   Provide detailed descriptions of your changes in the pull request.
*   Test your changes thoroughly before submitting.
