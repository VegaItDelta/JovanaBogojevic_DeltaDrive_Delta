$(document).on("submit", "form", function (event) {
     event.preventDefault();

    var firstName = $("#firstName").val();
    var lastName = $("#lastName").val();
    var email = $("#email").val();
    var birthday = $("#birthday").val();
    var password = $("#password").val();

    var newPassengerJson = formToJSON(firstName, lastName, email, birthday, password);

    $.ajax({
        type: "POST",
        url: "http://localhost:8080/api/passengers/registration",
        dataType: "json",
        contentType: "application/json",
        data: newPassengerJson,
        success: function (data) {
            alert(firstName + " " + lastName + " successfully registered.");
            window.location.href = "index.html";
        },
            error: function (data) {
            console.log("ERROR: ", data);
        }
    });
});

function formToJSON(firstName, lastName, email, birthday, password) {
    return JSON.stringify({
        "firstName": firstName,
        "lastName": lastName,
        "email": email,
        "birthday": birthday,
        "password": password
   });
}