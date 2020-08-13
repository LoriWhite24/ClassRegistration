/*********
AJAX call functions
Author: Tara Kelly
Provide logic for retrieving needed data from our API's
***********/

function validateLogin(){
    //TODO: implement - this would hook up to a login button on login page
    // something like this:
    // open a get request that will returns the student id if credentials are valid
    // call getStudentById() and store student in a session
    // can then pass student obj to other functions

    // for now, enter student id in text field as well b/c of way controller is working
    // note for Lori: we need the student though - if I don't receive student object back (or id), I can't store their id/information in a session in the browser - TK
    let id = txtStudentId.textContent;
    let email = txtEmail.textContent;
    let password = txtPassword.textContent;

    var url = `/students/login/username/${email}/password/${password}`;
    var xhttpList = new XMLHttpRequest();
    
    var student;
    xhttpList.onreadystatechange = function(){
        if(this.readyState == 4 && this.status == 200){
            console.log(this.responseText);
            student = getStudentById(id);
            alert("Valid login");
        }
    };
    xhttpList.open("GET", url, false);
    xhttpList.send();
    console.log("Student login validated");  

    // do I need to keep returning the student? or can I access it from anywhere in page?
    // can I return it here and pass to next webpage?
    return student;    
}

//AJAX call for student using app (for now assume they are logged in)
// for now this is for viewing how data looks in console
function getStudentById(id){

    var url = "/api/students/" + id;
    var xhttpList = new XMLHttpRequest();
    var student;

    xhttpList.onreadystatechange = function(){
        if(this.readyState == 4 && this.status == 200){
            sessionStorage.setItem("student", this.responseText); // this code would depend on what this function is used for
            console.log(this.responseText)
        }
    };
    xhttpList.open("GET", url, false);
    xhttpList.send();
    console.log("Student received");  

    return sessionStorage.getItem("student");

}


// AJAX calls for courses
function getCourses(url){

    // url to pass in for all courses: "/api/courses"
    // don't hardcode url in this function b/c will reuse it in other functions
    var xhttpList = new XMLHttpRequest();

    xhttpList.onreadystatechange = function(){
        if(this.readyState == 4 && this.status == 200){
            sessionStorage.setItem("courses", this.responseText);
        }
    };

    xhttpList.open("GET", url, true);
    xhttpList.send();
    return sessionStorage.getItem("courses");
}

// may or may not need this function - this was for updating
function getCourseById(id){

    var url = "/api/courses/" + id;
    var xhttpList = new XMLHttpRequest();
    var course;

    xhttpList.onreadystatechange = function(){
        if(this.readyState == 4 && this.status == 200){
            sessionStorage.setItem("course", this.responseText); // this code would depend on what this function is used for
            //console.log(this.responseText);
        }
    };
    xhttpList.open("GET", url, false);
    xhttpList.send();
    console.log("Course received");  

    return sessionStorage.getItem("course");
}

function getCoursesByDepartment(dept){

    // TODO: hook this up to web page
    // this is an example of how to reset display of course List
    // var courseList = document.getElementById("courseList");
    // courseList.innerHTML = '';
    let url = "/api/courses/department" + dept;  
    getCourses(url);
}

function getCourseByName(name){
    
    //TODO: reset display or redirect to diff page
    let url = "/api/courses/name" + name;
    getCourses(url);
}

// AJAX calls for Registrations
function getRegistrationsForStudent(id){

    let url = "/api/registration/student/" + id;
    var xhttpList = new XMLHttpRequest();

    xhttpList.onreadystatechange = function(){
        if(this.readyState == 4 && this.status == 200){
           console.log(this.responseText);
           sessionStorage.setItem("registrations", this.responseText);
        }
    };
    xhttpList.open("GET", url, true);
    xhttpList.send();
    console.log("Student's Registrations received");  
    return sessionStorage.getItem("registrations");
    
}

function addRegistration(courseId){

    // hard-coding student id again for now
    let studentId = 1;
    let newRegistration = {
        "id": -1,
        "registrationDate": (new Date()).toJSON(),
        "hasWithdrawn": false,
        "studentId": studentId,
        "courseId": courseId
    }
   
    sendPostRegistration(newRegistration);
}

function sendPostRegistration(sendData){

    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function(){
        // if the POST request went through,
        // the student is alerted and the page is redirected to My Courses page w/ refreshed data
        if(this.readyState == 4 && this.status == 201){
            alert("Registration successful!");
            window.location.href = "./view-registrations.html";
            //location.reload();
        }
    };

    xhttp.open("POST", "/api/add/registration", true);
    xhttp.setRequestHeader('Content-type', 'application/json');
    xhttp.send(JSON.stringify(sendData));
}

/******
 * withdraws a registration for a student
 * can also re-enroll a previously withdrawn registration
 * (param) : id of student, id of course, boolean (true = withdraw)
 ******/
function withdrawOrReEnroll(registrationId, courseId, studentId, value){

    console.log(registrationId);
    console.log(courseId);
    console.log(studentId);
    console.log(value);
    // call this function when a Withdraw button is clicked
    // how to know if withdrawing or reenrolling?
    // for now using two buttons, and passing in a boolean value for each button
    let url = `/update/registration/student/${studentId}/course/${courseId}/withdraw/${value}`;

    //*** I Need Lori to explain how the patch API works before I can troubleshoot */
    // let registration = getRegistrationById();
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function(){
        // if the POST request went through,
        // the student is alerted and the page is redirected to My Courses page w/ refreshed data
        if(this.readyState == 4 && this.status == 200){
            alert("Updated Registration successfully!");
           // window.location.href = "./view-registrations.html";
            location.reload();
        }
    };

    xhttp.open("PATCH", url, true);
    xhttp.setRequestHeader('Content-type', 'application/json');
    // do I need to put something in body?
    // getting a 404 when try and send this request
    xhttp.send();

}

/***********
Render HTML functions
Author: Tara Kelly
Rendering portions of HTML with data from our database
***********/

function renderCourseTableRows(){

    let courseList = getCourses("/api/courses/");
    let jsonArray = JSON.parse(courseList);

    let rows = "";
    // the last cell in each table row will be a Register button
    // the id of the button will be "register-" + the id of the course, i.e. "register-1"
    // we can then pull the course id when someone clicks the Register button
    jsonArray.forEach(json => {
        rows +=
        `<tr>
            <td>${json.id}</td>
            <td>${json.department}</td>
            <td>${json.name}</td>
            <td>${json.noCredits}</td>
            <td><button class="btn btn-primary" id="register-${json.id}" onclick="addRegistration(${json.id})">Register</button></td>
        </tr>`;
    });
 
    document.getElementById("courseTable").innerHTML = rows;

}

/******
 * renders table rows of registered courses for a student
 * (param) : id of student 
 * uses student id to get list of registrations
 * parses list of registrations to get each course object
 * displays course object in a table row 
 ******/
function renderRegisteredCourses(studentId){

    
    let registrations = getRegistrationsForStudent(studentId);
    let jsonArray = JSON.parse(registrations);

    let rows = "";

    for(var index = 0; index < jsonArray.length; index++){

        let registration = jsonArray[index];
        // try using registration id next
        let registrationId = registration.id;
        let course = getCourseById(registration.courseId);
        let json = JSON.parse(course);

        rows +=
        `<tr>
            <td>${json.id}</td>
            <td>${json.department}</td>
            <td>${json.name}</td>
            <td>${json.noCredits}</td>
            <td>
                <button class="btn btn-primary" id="withdraw-${json.id}" onclick="withdrawOrReEnroll(${registrationId}, ${json.id}, ${studentId}, true)">Withdraw</button>
                <button class="btn btn-danger" id="reenroll-${json.id}" onclick="withdrawOrReEnroll(${registrationId}, ${json.id}, ${studentId}, false)">ReEnroll</button>
            </td>
        </tr>`;
    }

    document.getElementById("registeredCoursesTable").innerHTML = rows;

}


