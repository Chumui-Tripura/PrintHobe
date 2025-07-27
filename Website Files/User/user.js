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

  if (ongoingCard) {
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
            <td>${doc.date}</td>
            <td>${doc.time}</td>
            <td>${doc.documentName}</td>
            <td>${doc.pages}</td>
            <td>${doc.type}</td>
            <td>${doc.cost}</td>
            <td><span class="status ${getStatusClass(
              doc.status
            )}">${formatStatus(doc.status)}</span></td>
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
      case "APPROVED":
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


  function loadPrintingHistory(userId) {
  fetch(`http://localhost:8080/api/documents/user-printing-history/${userId}`)
    .then(response => response.json())
    .then(data => {
      const tbody = document.querySelector("#historyModal tbody");
      tbody.innerHTML = "";

      
      data.forEach(doc => {
        const statusClass = doc.status === "REJECTED" ? "status-rejected" : "status-approved";
        const row = `
          <tr>
            <td>${doc.date}</td>
            <td>${doc.time}</td>
            <td>${doc.fileName}</td>
            <td>${doc.numberOfPages}</td>
            <td>${doc.color}</td>
            <td>${doc.amount}</td>
            <td class="${statusClass}">${doc.status}</td>
          </tr>`;
        tbody.innerHTML += row;
      });
    });
}

  // Open history modal
  if (historyCard && historyModal) {
    historyCard.onclick = function () {
      loadPrintingHistory(user.userId);
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
const user = JSON.parse(sessionStorage.getItem("user"));

paymentHistory.addEventListener("click", () => {
  paymentHistoryModal.style.display = "block";
  fetchPaymentHistory(user.userId);
});

closePaymentHistory.addEventListener("click", () => {
  paymentHistoryModal.style.display = "none";
});

window.addEventListener("click", (event) => {
  if (event.target === paymentHistoryModal) {
    paymentHistoryModal.style.display = "none";
  }
});



// Function to map status for styling
function getStatusClass(status) {
  switch (status.toUpperCase()) {
    case "REJECTED":
      return "status rejected";
    case "APPROVED":
      return "status approved";
    case "VERIFYING":
      return "status pending";
    default:
      return "status";
  }
}

function fetchPaymentHistory(userId) {
  fetch(`http://localhost:8080/api/payments/user-payment-history/${userId}`)
    .then((response) => response.json())
    .then((data) => {
      const tableBody = document.getElementById("paymentHistoryBody");
      tableBody.innerHTML = ""; 

      data.forEach((entry) => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${entry.date}</td>
            <td>${entry.time}</td>
            <td>${entry.referenceId}</td>
            <td>${entry.amount} Tk</td>
            <td>${
              entry.paymentFor === "PACKAGE" ? "PACKAGE" : entry.paymentFor
            }</td>
            <td><span class="${getStatusClass(entry.paymentStatus)}">${
          entry.paymentStatus.charAt(0) +
          entry.paymentStatus.slice(1).toLowerCase()
        }</span></td>
          `;

        tableBody.appendChild(row);
      });
    })
    .catch((error) => {
      console.error("Error fetching payment history:", error);
    });
}

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

  function preventClick(e) {
    e.preventDefault();
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

      const availableTillElement = document.getElementById("availableTill");
    if (data.available && data.availableTill) {
      availableTillElement.textContent = `Available Till: ${data.availableTill}`;
      availableTillElement.style.display = "block";
    } else {
      availableTillElement.textContent = ``;
      availableTillElement.style.display = "block";
    }

      // Update cost
      document.getElementById("costText").innerHTML = `
        Print Cost:<br/>${data.colorCost} tk (Color) <br/>${data.blackWhiteCost} tk (B&W)
      `;

      // Update availability
      const availabilityBtn = document.getElementById("availabilityBtn");
      const visitBtn = document.querySelector(".visit-btn");


      if (data.available) {
        availabilityBtn.textContent = "Available Now";
        availabilityBtn.style.backgroundColor = "#0d9675";

       visitBtn.classList.remove("disabled");
      visitBtn.removeAttribute("disabled");
      visitBtn.style.pointerEvents = "auto";
      visitBtn.style.opacity = "1";
      } else {
        availabilityBtn.textContent = "Not Available";
        availabilityBtn.style.backgroundColor = "#878787";

        // Disable the visit link
        visitBtn.classList.add("disabled");
      visitBtn.setAttribute("disabled", "true");
      visitBtn.style.pointerEvents = "none";
      visitBtn.style.opacity = "1";
      visitBtn.style.backgroundColor = "#878787ff";
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
