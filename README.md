# TaskMaster

This week, we will build up an app that allows tracking tasks on a project. (Have you ever thought, “Trello boards are cool, but why do we have to use Trello?” Let’s solve that problem.) While we will start small today, this app will build up over time to create something fully-featured.

## Lab 40: Beginning TaskMaster
* Users should be able to create a Project.
* Users should be able to add a task to a Project.
  * Both Tasks and Projects should be saved locally to a database.
* A user should be able to “Assign” themselves a task, “Accept” that task, and later “Complete” that task.
* A task should have one property which represents what state the task is in.
  * Available - the task has been created, and is publicly available, but no other user has claimed it yet.
  * Assigned - someone has been assigned that task.
  * Accepted - the assignee has accepted that task.
  * Finished - the task is totally complete Write tests making sure a task progresses through it’s states properly.

|             Project List              |             Project1's Tasks           |             Project2's Tasks           |
|---------------------------------------|----------------------------------------|----------------------------------------|
| ![Lab 40](screenshots/screenshot.png) | ![Lab 40](screenshots/screenshot1.png) | ![Lab 40](screenshots/screenshot2.png) |

## Lab 41: TaskMaster with Firebase
Today, we’ll use Firebase to create a backend for our TaskMaster app
* Users should be able to log in.
*   At a minimum, they should be able to log in with email and password.
* Users should be able to see all the Projects in a cloud database, and add Tasks to those projects.
*   (Hint: In a NoSQL database, there are many options for how to store Tasks associated with Projects and Users. Read the Firebase documentation on structuring data carefully, and have fun exploring!)
* A user should be able to “Assign” a task to themself or another user.
* The user assigned to a task should be able to “Accept” that task, and later “Complete” that task.

|              User Login                |              Profile Page              |              Project List              |            Project1's Tasks            |     Assign User (Incomplete)           |
|----------------------------------------|----------------------------------------|----------------------------------------|----------------------------------------|----------------------------------------|
| ![Lab 41](screenshots/screenshot3.png) | ![Lab 41](screenshots/screenshot4.png) | ![Lab 41](screenshots/screenshot5.png) | ![Lab 41](screenshots/screenshot6.png) | ![Lab 41](screenshots/screenshot7.png) |

## Information Structure Using Firebase

* Projects store a collection of the document IDs of the tasks that belong to them.
* Tasks store the ID of the project they belong too.
* Users store a collection of the document IDs of the tasks they have been assigned and a collection of the document IDs of the tasks they have accepted (incomplete).

|          "projects" Collection           |           "tasks" Collection            |           "users" Collection             |
|------------------------------------------|-----------------------------------------|------------------------------------------|
|  ![FireBase](screenshots/firebase.png)   |  ![FireBase](screenshots/firebase1.png)  |  ![FireBase](screenshots/firebase2.png)  |

~~## Information Structure Using Room~~

~~Project and Task are entities linked by the ProjectWithTasks Pojo (plain old Java object). Tasks have a field to keep track of the id of the project they belong to. ProjectWithTasks is connected to the project's id and the tasks stored copy of the project's id.~~

![Lab 40](screenshots/roomDatabaseStructure.png)

## Change Log
* Monday 1/28/2019 - Created project and database entities (Project and Task).
* Tuesday 1/29/2019 - Created project recycler view, ability to add projects to room and create an event listener to be used to add tasks to a project.
* Wednesday 1/30/2019 - Tasks can be added to projects, users can log in using Firebase
* Thursday 1/31/2019 - Removed corpse code (from Lab 40) and allowed users to save projects and tasks to Firebase
* Friday 2/1/2019 - Dealt with gitignore issues
* Saturday 2/2/2019 - Users can add tasks to a projects and everything is stored in Firebase
* Sunday 2/3/2019 - Made progress towards allowing users to assign tasks to themselves or other users, updated documentation

## Resources
* http://androidkt.com/database-relationships/
* http://www.vogella.com/tutorials/AndroidRecyclerView/article.html
* https://stackoverflow.com/questions/13593069/androidhide-keyboard-after-button-click/13593232 (second answer)
* https://stackoverflow.com/questions/44330452/android-persistence-room-cannot-figure-out-how-to-read-this-field-from-a-curso/44424148#44424148
* https://stackoverflow.com/questions/2091465/how-do-i-pass-data-between-activities-in-android-application
* https://stackoverflow.com/questions/4298225/how-can-i-start-an-activity-from-a-non-activity-class
* https://stackoverflow.com/questions/2091465/how-do-i-pass-data-between-activities-in-android-application
* https://developer.android.com/docs/
* https://firebase.google.com/docs/
* https://github.com/JessLovell/taskMaster/blob/review/app/src/main/java/com/taskmaster/taskmaster/MyAdapter.java
* https://stackoverflow.com/questions/13485918/android-onclick-listener-in-a-separate-class
* https://dzone.com/articles/cloud-firestore-read-write-update-and-delete