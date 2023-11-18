$(document).on("submit", "form", function (event) {
     event.preventDefault();

    var email = $("#email").val();
    var password = $("#password").val();

    var passengerJson = formToJSON(email, password);

    $.ajax({
            type: "POST",
            url: "http://localhost:8080/api/passengers/login",
            dataType: "json",
            contentType: "application/json",
            data: passengerJson,
            success: function (data) {
                console.log("Login success:", data);
                localStorage.setItem('id', data['id']);
                window.location.href = "home.html";
            },
            error: function (data) {
                console.log(data);
                alert("Invalid credentials!");
          }
    });
});

function formToJSON(email, password) {
    return JSON.stringify({
        "email": email,
        "password": password
   });
}