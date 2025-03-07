# Advanced Kotlin Database Development

This is the repository for the LinkedIn Learning course Advanced Kotlin Database Development. The full course is
available from [LinkedIn Learning][lil-course-url].

![Advanced Kotlin Database Development][lil-thumbnail-url]

Kotlin is the preferred language for Android development, but its popularity in web development is on the rise, since
it’s a modern programming language that is interoperable with Java and easier to learn than Scala. To develop a Kotlin
backend application of any complexity, you need to interact with databases extensively. In this course, Alexey Soshinl
teaches you how to get started. He shows you how to set up the connection using the Exposed library, define and populate
tables, and write and debug custom queries. He also provides a challenge after each chapter so you can test your
knowledge as you work through the course.

## Instructions

This repository has branches for each of the videos in the course. You can use the branch pop up menu in github to
switch to a specific branch and take a look at the course at that stage, or you can add `/tree/BRANCH_NAME` to the URL
to go to the branch you want to access.

## Branches

The branches are structured to correspond to the videos in the course. The naming convention is `CHAPTER#_MOVIE#`. As an
example, the branch named `02_03` corresponds to the second chapter and the third video in that chapter.
Some branches will have a beginning and an end state. These are marked with the letters `b` for "beginning" and `e`
for "end". The `b` branch contains the code as it is at the beginning of the movie. The `e` branch contains the code as
it is at the end of the movie. The `main` branch holds the final state of the code when in the course.

When switching from one exercise files branch to the next after making changes to the files, you may get a message like
this:

    error: Your local changes to the following files would be overwritten by checkout:        [files]
    Please commit your changes or stash them before you switch branches.
    Aborting

To resolve this issue:

    Add changes to git using this command: git add .
	Commit changes using this command: git commit -m "some message"

### Instructor

Alexey Soshin

Check out my other courses on [LinkedIn Learning](https://www.linkedin.com/learning/instructors/alexey-soshin).

[lil-course-url]: https://www.linkedin.com/learning/advanced-kotlin-database-development?dApp=59033956

[lil-thumbnail-url]: https://media.licdn.com/dms/image/C560DAQEgBRstLtOJbA/learning-public-crop_675_1200/0/1671157490377?e=2147483647&v=beta&t=6SkFJLW3QefCYjY-sI8P12BYrV4oQjNmMeYfjKJgwgY

## Pre-requisites

- JDK 11
- Kotlin 1.7.xx
- Docker and Docker Compose

## How to use this repository

1. Install Dependencies

    ```bash
    ./gradlew clean build
    ```
2. Run docker-compose up to start the PostgreSQL database.

    ```bash
    docker-compose up -d
    ```

### Part 1

We can start the application with the following command.

```bash
./gradlew run
```

### Part 2

1. Run the create_populated_db.sh script to create the database and populate it with data.

    ```bash
    ./create_populated_db.sh
    ```

2. Run the application.

    ```bash
    ./gradlew run
    ```
