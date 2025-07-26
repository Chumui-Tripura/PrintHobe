// This script handles the display of a popup for bKash payment when the "Pay Now" button is clicked.
document.addEventListener("DOMContentLoaded", () => {
  const payNowBtn = document.querySelector(".pay-now");
  const popupOverlay = document.getElementById("bkash-popup-overlay");

  // Get the operator phone number from localStorage
  const operatorPhone = localStorage.getItem("operatorPhoneNumber");

  // Show it in the popup
  document.querySelector(
    ".bkash-inner h3"
  ).innerHTML = `Bkash Number<br><strong>${operatorPhone}</strong>`;

  if (payNowBtn && popupOverlay) {
    payNowBtn.addEventListener("click", (e) => {
      e.preventDefault();
      popupOverlay.style.display = "block";
    });

    // Optional: close when clicking outside
    popupOverlay.addEventListener("click", (e) => {
      if (e.target.id === "bkash-popup-overlay") {
        popupOverlay.style.display = "none";
      }
    });
  }
});
