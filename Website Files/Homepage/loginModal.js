document.addEventListener("DOMContentLoaded", function () {
  const modal = document.getElementById("loginModal");
  const loginBtn = document.querySelector(".login-button");
  const closeBtn = document.querySelector(".close");
  const loginForm = document.getElementById("loginForm");
  const signupForm = document.getElementById("signupForm");
  const signupLink = document.querySelector(".signup-link");
  const loginLink = document.querySelector(".login-link");

  // Show login modal
  loginBtn.onclick = function () {
    modal.style.display = "block";
    showLoginForm();
  };

  // Close modal
  closeBtn.onclick = function () {
    modal.style.display = "none";
    showLoginForm();
  };

  // Close when clicking outside modal
  window.addEventListener("click", function (event) {
    if (event.target === modal) {
      modal.style.display = "none";
      showLoginForm();
    }
  });

  //LOGIN LOGIC 
  loginForm.addEventListener("submit", function (e) {
    e.preventDefault();


    const email = document.getElementById("emailLogin").value;
    const password = document.getElementById("passwordLogin").value;


    fetch("http://localhost:8080/api/users/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, password })
    })
    .then(res => {
      if (!res.ok) throw new Error("Login failed");
      return res.json();
    })
    .then(data => {
      sessionStorage.setItem("user", JSON.stringify(data));
      modal.style.display = "none";

      // Redirect based on role
      if (data.role === "USER") {
        window.location.href = "../User/user.html";
      } else if (data.role === "OPERATOR") {
        window.location.href = "../Printing_operator/operator.html";
      } else {
        alert("Unknown role: " + data.role);
      }
    })
    .catch(err => {
      const errEl = loginForm.querySelector(".error") 
                  || document.createElement("p");
      errEl.classList.add("error");
      errEl.innerText = "Invalid email or password";
      loginForm.appendChild(errEl);
    });
    // Redirect to another page after clicking login (bypassing email/password validation)
    /* window.location.href = "../User/user.html"; */
  });

  function showLoginForm() {
    if (signupForm) signupForm.style.display = "none";
    loginForm.style.display = "block";
  }
});

// Switch to Signup
function switchToSignUpForm() {
  document.getElementById("loginForm").style.display = "none";
  document.getElementById("signupForm").style.display = "block";
}

// Switch to Login
function switchToLoginForm() {
  document.getElementById("signupForm").style.display = "none";
  document.getElementById("loginForm").style.display = "block";
}