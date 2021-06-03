<p align="center">
<img src="https://user-images.githubusercontent.com/46590330/120651419-b7686c80-c4b9-11eb-8b19-5fd8f41851e5.png" width="30%"/>
<br/>
</p>
<p>This project is for the Design Project of <b>KAIST CS492E: "Human-AI Interaction" course</b>.<p/>

## Project Name & Purpose

**Vocali**

This service recommends songs based on user information for those who have difficulty choosing songs to sing in karaoke. Users record their voices to analyze their vocal range, and input their favorite songs and current moods. Accordingly, the system recommends appropriate songs through machine learning model. Users can get a explanation of the recommended results, or they can send feedback on the recommended results to further customize the results.

## Libraries & Framework

#### Framework
* [Kotlin](https://kotlinlang.org)
* [Spring Boot](https://spring.io/projects/spring-boot)

#### Database
* [PostgreSQL](https://www.postgresql.org)

#### Deploy
* [Heroku](https://www.heroku.com)

#### IDE
* [Intellij](https://www.jetbrains.com/ko-kr/idea/)

### Packages

#### `/controller`

Defined controller for Spring Boot, which contains routers and actual connection with the database.

#### `/model`

`model` package contains database entity and remote request/response models.

#### `/repository`

This package contains database repository interface.
