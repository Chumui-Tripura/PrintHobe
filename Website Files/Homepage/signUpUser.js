document.addEventListener("DOMContentLoaded", function () {
  const signupForm = document.getElementById("signupForm");

  if (signupForm) {
    signupForm.addEventListener("submit", async function (e) {
      e.preventDefault();

      const form = e.target;

      const formData = {
        firstName: form.firstName.value,
        lastName: form.lastName.value,
        email: form.querySelector('input[type="email"]').value,
        password: form.querySelector('input[type="password"]').value
      };

      try {
        const response = await fetch("http://localhost:8080/api/users/register", {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify(formData)
        });

        if (response.ok) {
          const data = await response.json();
          sessionStorage.setItem("user", JSON.stringify(data));
          window.location.href = "../User/user.html";
        } else {
          const errorText = await response.text();
          alert("Signup failed: " + errorText);
        }
      } catch (error) {
        alert("Error: " + error.message);
      }
    });
  }
});
