document.addEventListener("DOMContentLoaded", function () {
  const user = JSON.parse(sessionStorage.getItem("user"));
  const welcomeEl = document.getElementById("welcomeMessage");
  const authBtn = document.getElementById("authButton");

  if (user) {
    if (welcomeEl) {
      welcomeEl.textContent = `Hello, ${user.firstName}`;
    }

    if (authBtn) {
      authBtn.textContent = "Logout";
      authBtn.onclick = function () {
        sessionStorage.clear();
        window.location.href = "../Homepage/homepage.html";
      };
    }
  } 
});
