//ongoing printings and history popup modal
document.addEventListener("DOMContentLoaded", function () {
  /* ============================ Portion Chumui ============================ */

  // Do not run redirect logic on the login page
  const currentPage = window.location.pathname;
  if (currentPage.includes("homepage.html")) {
    return; // Skip further checks
  }

  const user = JSON.parse(sessionStorage.getItem("user"));

  // If not logged in, redirect to homepage
  if (!user) {
    window.location.href = "../Homepage/homepage.html";
    return;
  }

  // Update welcome message
  const welcomeEl = document.getElementById("welcomeMessage");
  if (welcomeEl) {
    welcomeEl.textContent = `Hello, ${user.userName}`;
  }

  const authBtn = document.getElementById("authButton");
  if (authBtn) {
    authBtn.textContent = "Logout";
    authBtn.onclick = function () {
      sessionStorage.clear(); // or sessionStorage.removeItem("user");
      window.location.href = "../Homepage/homepage.html"; // redirect to login
    };
  }

  /* ============================ Portion Chumui ============================ */

  // Elements that trigger modals
  const ongoingCard = document.querySelector(".ongoing-printings");
  const historyCard = document.querySelector(".history");

  // Modals
  const ongoingModal = document.getElementById("ongoingPrintingsModal");
  const historyModal = document.getElementById("historyModal");

  // Close buttons
  const closeOngoing = document.getElementById("closeOngoingPrintings");
  const closeHistory = document.getElementById("closeHistory");

  // Open ongoing modal

  /* ============================== Backend Of Ongoing Printings ============================== */

  if (ongoingCard && ongoingModal) {
    ongoingCard.onclick = function () {
      ongoingModal.style.display = "block";
      ongoingTableBody.innerHTML = "";
      fetch(`http://localhost:8080/api/documents/ongoing/${user.userId}`)
        .then((response) => {
          if (response.status === 204) {
            ongoingTableBody.innerHTML = `
            <tr><td colspan="7" style="text-align:center; color: gray;">No ongoing printings</td></tr>
          `;
            return [];
          } else if (!response.ok) {
            throw new Error("Failed to fetch ongoing printings");
          }
          return response.json();
        })
        .then((documents) => {
          documents.forEach((doc) => {
            const row = document.createElement("tr");
            row.innerHTML = `
            <td>${doc.dateTime ?? ""}</td>
            <td>${doc.documentName}</td>
            <td>${doc.pages}</td>
            <td>${doc.type}</td>
            <td>${doc.cost}</td>
            <td><span class="status ${getStatusClass(
              doc.status
            )}">${formatStatus(doc.status)}</span></td>
            <td></td> <!-- You don't have estimated end time now -->
          `;
            ongoingTableBody.appendChild(row);
          });
        })
        .catch((err) => {
          console.error(err);
          ongoingTableBody.innerHTML = `
          <tr><td colspan="7" style="text-align:center; color: red;">Error loading data</td></tr>
        `;
        });

      ongoingModal.style.display = "block";
    };
  }

  function getStatusClass(status) {
    switch (status) {
      case "VERIFYING":
        return "verifying";
      case "NOT_STARTED":
        return "not-started";
      case "IN_PROGRESS":
        return "in-progress";
      case "FINISHED":
        return "finished";
      default:
        return "";
    }
  }

  function formatStatus(status) {
    return status
      .toLowerCase()
      .split("_")
      .map((word) => word.charAt(0).toUpperCase() + word.slice(1))
      .join(" ");
  }

  /* ============================== Backend Of Ongoing Printings ============================== */
  // Open history modal
  if (historyCard && historyModal) {
    historyCard.onclick = function () {
      historyModal.style.display = "block";
    };
  }

  // Close ongoing modal
  if (closeOngoing && ongoingModal) {
    closeOngoing.onclick = function () {
      ongoingModal.style.display = "none";
    };
  }

  // Close history modal
  if (closeHistory && historyModal) {
    closeHistory.onclick = function () {
      historyModal.style.display = "none";
    };
  }

  // Close modal if clicked outside modal content
  window.onclick = function (event) {
    if (event.target === ongoingModal) {
      ongoingModal.style.display = "none";
    }
    if (event.target === historyModal) {
      historyModal.style.display = "none";
    }
  };
});

// Package gauge and no package message
document.addEventListener("DOMContentLoaded", () => {
  const container = document.querySelector(".package");
  const radius = 54;
  const circumference = 2 * Math.PI * radius;

  // Get user data from sessionStorage
  const user = JSON.parse(sessionStorage.getItem("user"));
  const availablePages = user?.availablePackagePages ?? 0;

  const hasPackage = availablePages > 0;

  // Clear container content first
  container.innerHTML = "";

  if (hasPackage) {
    container.innerHTML = `
      <svg viewBox="0 0 120 120"> 
        <defs>
          <linearGradient id="blueGradient" x1="0%" y1="0%" x2="100%" y2="0%">
            <stop offset="0%" stop-color="#007CF0" />
            <stop offset="100%" stop-color="#00DFD8" />
          </linearGradient>
        </defs>
        <circle class="bg-circle" cx="60" cy="60" r="${radius}" />
        <circle class="progress-circle" cx="60" cy="60" r="${radius}" stroke-dasharray="${circumference}" stroke-dashoffset="${circumference}" />
      </svg>
      <div class="pages-left">${availablePages}</div>
      <div class="pages-text">Pages Left</div>
    `;

    const progressCircle = container.querySelector(".progress-circle");
    const pagesLeftText = container.querySelector(".pages-left");

    function updateGauge(totalPages, pagesLeft) {
      pagesLeft = Math.min(Math.max(pagesLeft, 0), totalPages);
      const percentLeft = pagesLeft / totalPages;
      const offset = circumference * (1 - percentLeft);
      progressCircle.style.strokeDashoffset = offset;
      pagesLeftText.textContent = pagesLeft;
    }

    // Assume total package is 50 (you can replace this with real value later)
    updateGauge(50, availablePages);
    window.updateGauge = updateGauge;
  } else {
    container.innerHTML = `
      <img src="cut_pages.png" alt="No current package" style="width: 100px;" />
      <div style="font-weight: bold; font-size: 20px; color: black;">No current package</div>
    `;
  }
});

// Add event listeners for the payment history modal
const paymentHistory = document.querySelector(".payment-history");
const paymentHistoryModal = document.getElementById("paymentHistoryModal");
const closePaymentHistory = document.getElementById("closePaymentHistory");

paymentHistory.addEventListener("click", () => {
  paymentHistoryModal.style.display = "block";
});

closePaymentHistory.addEventListener("click", () => {
  paymentHistoryModal.style.display = "none";
});

window.addEventListener("click", (event) => {
  if (event.target === paymentHistoryModal) {
    paymentHistoryModal.style.display = "none";
  }
});

/* ========================= Printer Details ========================= */

document.addEventListener("DOMContentLoaded", () => {
  const payNowBtn = document.querySelector(".buy-now");
  const popupOverlay = document.getElementById("bkash-popup-overlay");
  const operatorPhoneDisplay = document.querySelector("#bkash-popup h3 strong");
  const referenceInput = document.querySelector("#bkash-popup input");
  const submitBtn = document.querySelector(".submit-btn");

  const user = JSON.parse(sessionStorage.getItem("user"));
  if (!user) {
    alert("User not logged in.");
    return;
  }

  // ======= Handle Buy Now Button =======
  if (payNowBtn && popupOverlay) {
    payNowBtn.addEventListener("click", (e) => {
      e.preventDefault();
      popupOverlay.style.display = "block";
    });

    // Close on outside click
    popupOverlay.addEventListener("click", (e) => {
      if (e.target.id === "bkash-popup-overlay") {
        popupOverlay.style.display = "none";
      }
    });
  }

  // ======= Fetch Printer Summary and Update UI =======
  fetch("http://localhost:8080/api/users/printer/9/summary")
    .then((res) => res.json())
    .then((data) => {
      // Update banner
      const bannerText = document.querySelector(".banner-text");
      bannerText.innerHTML = `
        Tired of filling the Reference No.<br />
        Every Time you print??<br />
        Buy the package of ${data.packagePages} pages for only ${data.packagePrice}tk
      `;

      // Update printer name
      document.getElementById("printerName").textContent = data.printerName;

      // Update cost
      document.getElementById("costText").innerHTML = `
        Print Cost: ${data.colorCost}tk (Color), ${data.blackWhiteCost}tk <br /> (Black & white)
      `;

      // Update availability
      const availabilityBtn = document.getElementById("availabilityBtn");
      if (data.available) {
        availabilityBtn.textContent = "Available Now";
        availabilityBtn.style.backgroundColor = "#4CAF50";
      } else {
        availabilityBtn.textContent = "Currently Busy";
        availabilityBtn.style.backgroundColor = "#f44336";
      }

      // Setting the operator phone number to local storage for easy access
      localStorage.setItem("operatorPhoneNumber", data.operatorPhoneNumber);

      // Set operator phone number in popup
      operatorPhoneDisplay.textContent = data.operatorPhoneNumber;

      // Submit payment on popup
      submitBtn.addEventListener("click", () => {
        const referenceId = referenceInput.value.trim();
        if (!referenceId) {
          alert("Please enter a reference number.");
          return;
        }

        const payload = {
          referenceId,
          amount: data.packagePrice,
          userId: user.userId,
          operatorId: 9, //  Operator id 9 is for OS LAB Printer
          paymentFor: "PACKAGE",
        };

        fetch("http://localhost:8080/api/payments/make", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(payload),
        })
          .then((res) => {
            if (!res.ok) {
              return res.text().then((text) => {
                throw new Error("Payment failed: " + text);
              });
            }
            return res.json();
          })
          .then(() => {
            alert("Package purchase request submitted!");
            popupOverlay.style.display = "none";
            referenceInput.value = "";
          })
          .catch((err) => {
            console.error("Payment error:", err.message);
            alert("Failed to process payment: " + err.message);
          });
      });
    })
    .catch((err) => {
      console.error("Failed to fetch printer summary:", err);
    });
});

/* ========================= Printer Details ========================= */
