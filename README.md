# Android Repository Viewer

This Android application fetches and displays a list of GitHub repositories for a specified user, providing essential details and allowing users to filter by programming language. The app features pagination for scrolling through repositories and error handling for API requests.

## Requirements

### 1. List of Repositories
- The application fetches and displays a list of repositories for a specified GitHub user.
- Each repository shows key information, including:
  - **Repository Name**: The name of the repository.
  - **Description**: A brief description of the repository.
  - **Programming Language**: The primary language used in the repository.
  - **Stars**: The number of stars the repository has received.
  - **Forks**: The number of times the repository has been forked.

### 2. Filtering
- Users can filter the displayed repositories by programming language (e.g., Kotlin, Java).

### 3. Repository Details
- When a user selects a repository, the app displays additional details, such as:
  - Detailed description of the repository.
  - Owner information.
  - Last updated date.

### 4. Pagination
- The app implements pagination to load more repositories as the user scrolls to the bottom of the list, enhancing the user experience.

### 5. Error Handling
- The application gracefully handles API errors, such as:
  - Rate limits.
  - Network issues.
- Appropriate error messages are displayed to the user if something goes wrong.

## Getting Started

### Prerequisites
- Android Studio installed on your machine.
- Basic knowledge of Kotlin and Android development.

### Installation
1. **Clone the Repository**:
   Open a terminal and run:
   ```bash
   git clone https://github.com/Bishwajeets1/NTTData.git
