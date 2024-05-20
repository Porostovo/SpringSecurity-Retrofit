# Spring Security Retrofit Small Project

## Overview

This project demonstrates a comprehensive implementation of a Spring Boot application with several key features:
- **Authentication**: Implemented using Spring Security.
- **API Integration**: Connected to an external API to fetch movie data.
- **Database Management**: Downloaded movie data is saved to a MySQL database.
- **Frontend Interface**: Includes a simple frontend interface for user registration, login, searching for movies, and displaying them on the page.

## Features

### 1. Authentication with Spring Security
- Secure user authentication and authorization.
- Password encryption using BCrypt.

### 2. API Connection
- Integrated with an external movie database API to fetch movie details.
- Used Retrofit for seamless API communication.

### 3. Database Management
- Movie data fetched from the API is saved into a MySQL database.
- Utilized JPA and Hibernate for database operations.

### 4. Frontend Interface
- **Registration**: Users can register by providing their details.
- **Login**: Secure login functionality for registered users.
- **Search Movies**: Users can search for movies using keywords.
- **Display Movies**: Search results are displayed on the webpage.

## Getting Started

1. **Clone the repository:**
   ```sh
   git clone https://github.com/OndrejJizba/SpringSecurityRetrofit.git
