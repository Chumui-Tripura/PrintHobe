/* ======================== For Time ========================*/
function updatePrintingTime() {
  const user = JSON.parse(sessionStorage.getItem("user"));
  const selectedTime = document.getElementById("printingTime").value;

  if (!selectedTime) {
    alert("Please select a valid time.");
    return;
  }

  const now = new Date();
  const [hours, minutes] = selectedTime.split(":").map(Number);
  const selectedDateTime = new Date();
  selectedDateTime.setHours(hours, minutes, 0, 0);

  if (selectedDateTime <= now) {
    alert("You cannot select a past time. Please choose a future time.");
    return;
  }

  fetch(
    `http://localhost:8080/api/operators/printer/${user.operatorId}/available-till`,
    {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ time: selectedTime }),
    }
  )
    .then((res) => {
      if (!res.ok)
        return res.text().then((msg) => {
          throw new Error(msg);
        });
      return res.text();
    })
    .then((msg) => {
      alert(msg);
    })
    .catch((err) => {
      console.error(err);
      alert(err.message || "Error updating printing time");
    });
}

/* ======================== For Time ========================*/

// ============ PAGE LOAD ============
document.addEventListener("DOMContentLoaded", function () {
  const user = JSON.parse(sessionStorage.getItem("user"));

  if (!user || user.role !== "OPERATOR") {
    alert("Not authorized or not logged in");
    window.location.href = "../Homepage/homepage.html";
    return;
  }

  // ======= Fetch Operator Dashboard Data =======
  fetch(`http://localhost:8080/api/operators/dashboard/${user.operatorId}`)
    .then((res) => {
      if (!res.ok) throw new Error("Dashboard fetch failed");
      return res.json();
    })
    .then((data) => {
      renderPrintJobs(data.documentReferences);
      renderPackageRequests(data.packageRequest);

      document.getElementById("totalPrintings").textContent =
        data.totalPrintings ?? "--";
      document.getElementById("inProgressCount").textContent =
        data.approvedDocsInProgress ?? "--";
      document.getElementById("totalEarnings").textContent =
        data.totalEarnings != null ? `${data.totalEarnings} /=` : "--";
      document.getElementById("printingTime").value =
        data.availableTill || "00:00";

      const statusBtn = document.getElementById("printerStatusBtn");
      if (statusBtn) {
        const currentStatus = user.printerStatus;
        updatePrinterButtonUI(currentStatus);
        statusBtn.addEventListener("click", function () {
          const newStatus =
            user.printerStatus === "AVAILABLE" ? "NOT_AVAILABLE" : "AVAILABLE";

          fetch(
            `http://localhost:8080/api/operators/printer/${user.operatorId}/status?status=${newStatus}`,
            {
              method: "PUT",
            }
          )
            .then((res) => {
              if (!res.ok) throw new Error("Failed to update status");
              return res.text(); // or res.json()
            })
            .then((msg) => {
              // Update sessionStorage
              user.printerStatus = newStatus;
              sessionStorage.setItem("user", JSON.stringify(user));

              // Update the button UI
              updatePrinterButtonUI(newStatus);
              alert(msg);
            })
            .catch((err) => {
              console.error(err);
              alert("Error updating printer status");
            });
        });
      }
    })
    .catch((err) => {
      console.error("Dashboard fetch error:", err);
      alert("Failed to load dashboard data");
    });
});

function updatePrinterButtonUI(status) {
  const statusBtn = document.getElementById("printerStatusBtn");

  if (status === "AVAILABLE") {
    statusBtn.textContent = "Make My Printer Unavailable";
    statusBtn.classList.remove("green");
    statusBtn.classList.add("red");
  } else {
    statusBtn.textContent = "Make My Printer Available";
    statusBtn.classList.remove("red");
    statusBtn.classList.add("green");
  }
}

function renderPrintJobs(documents) {
  const tbody = document.getElementById("printJobsList");
  tbody.innerHTML = "";

  if (!documents || documents.length === 0) {
    tbody.innerHTML = `<tr><td colspan="6">No documents found</td></tr>`;
    return;
  }

  documents.forEach((doc) => {
    const row = document.createElement("tr");

    const statusDisplay = doc.status === "APPROVED" ? "In Progress" : "Pending";

    row.innerHTML = `
      <td>${doc.referenceId || "-"}</td>
      <td>${doc.originalFileName}</td>
      <td>${doc.color}</td>
      <td>${doc.copies}</td>
      <td class="doc-status">${statusDisplay}</td>
      <td>
        ${
          doc.status !== "APPROVED"
            ? `<button onclick="startPrinting(this, '${doc.filePath}', '${
                doc.documentId
              }', '${
                doc.referenceId || ""
              }')">Print</button><button onclick="rejectDocument(this, '${
                doc.documentId
              }', '${doc.referenceId || ""}')">Reject</button>`
            : ""
        }
        ${
          doc.status === "APPROVED"
            ? `<button onclick="markCompleted(this, '${doc.documentId}')">Completed</button>`
            : ""
        }
        
      </td>
    `;

    tbody.appendChild(row);
  });
}

function startPrinting(button, filePath, documentId, referenceId) {
  // 1. Open the PDF in a new tab
  window.open(`http://localhost:8080${filePath}`, "_blank");

  // 2. Approve the document in backend
  fetch(`http://localhost:8080/api/documents/${documentId}/approve`, {
    method: "PUT",
  })
    .then((res) => {
      if (!res.ok) throw new Error("Failed to approve document");

      // 3. If referenceId exists, also approve payment
      if (referenceId) {
        return fetch(
          `http://localhost:8080/api/payments/package/${referenceId}/status?status=APPROVED`,
          {
            method: "PUT",
          }
        );
      }
    })
    .then(() => {
      // 4. Update the row in UI
      const row = button.closest("tr");
      row.querySelector(".doc-status").textContent = "In Progress";

      // Replace buttons
      const buttonCell = row.querySelector("td:last-child");
      buttonCell.innerHTML = `
        <button onclick="markCompleted(this, '${documentId}')">Completed</button>
      `;
    })
    .catch((err) => {
      console.error("Error in startPrinting:", err);
      alert("Failed to start printing");
    });
}

function markCompleted(button, documentId) {
  fetch(`http://localhost:8080/api/documents/${documentId}/complete`, {
    method: "PUT",
  })
    .then((res) => {
      if (!res.ok) throw new Error("Failed to mark as completed");
      return res.text();
    })
    .then((msg) => {
      alert(msg);

      // Update the row UI
      const row = button.closest("tr");
      row.querySelector(".doc-status").textContent = "COMPLETED";

      // Disable or remove all action buttons
      const buttonCell = row.querySelector("td:last-child");
      buttonCell.innerHTML = `<span style="color: green; font-weight: bold;">Completed</span>`;

      setTimeout(() => {
        row.classList.add("fade-out");
        setTimeout(() => row.remove(), 500); // match fade-out duration
      }, 200);
    })
    .catch((err) => {
      console.error("Error in markCompleted:", err);
      alert("Failed to mark document as completed");
    });
}

function rejectDocument(button, documentId, referenceId) {
  fetch(`http://localhost:8080/api/documents/${documentId}/reject`, {
    method: "PUT",
  })
    .then((res) => {
      if (!res.ok) throw new Error("Failed to reject document");
      if (referenceId) {
        // Reject payment if reference exists
        return fetch(
          `http://localhost:8080/api/payments/package/${referenceId}/status?status=REJECTED`,
          {
            method: "PUT",
          }
        );
      }
    })
    .then(() => {
      const row = button.closest("tr");
      row.querySelector(".doc-status").textContent = "Rejected";

      // Disable or remove all action buttons
      const buttonCell = row.querySelector("td:last-child");
      buttonCell.innerHTML = `<span style="color: red; font-weight: bold;">Rejected</span>`;

      setTimeout(() => {
        row.classList.add("fade-out");
        setTimeout(() => row.remove(), 500); // match fade-out duration
      }, 200);
    })
    .catch((err) => {
      console.error("Rejection failed:", err);
      alert("Error rejecting document");
    });
}

function renderPackageRequests(packageRequests) {
  const tbody = document.getElementById("printPackagesList");
  tbody.innerHTML = "";

  if (!packageRequests || packageRequests.length === 0) {
    tbody.innerHTML = `<tr><td colspan="4">No package requests found</td></tr>`;
    return;
  }

  packageRequests.forEach((pkg) => {
    const row = document.createElement("tr");

    row.innerHTML = `
      <td class="reference-id">${pkg.referenceId}</td>
      <td class="pages">${pkg.packagePage}</td>
      <td class="amount">${pkg.amount}</td>
      <td>
        <button class="accept" onclick="acceptPackage(this)">Accept</button>
        <button class="reject" onclick="rejectPackage(this)">Reject</button>
        <div class="package-status" style="display:none;">${pkg.status}</div>
      </td>
    `;

    tbody.appendChild(row);
  });
}
