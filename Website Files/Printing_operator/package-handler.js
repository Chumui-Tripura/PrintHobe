// Accept Package Handler
function acceptPackage(button) {
  const row = button.closest("tr");
  const referenceId = row.querySelector(".reference-id").textContent;

  fetch(`http://localhost:8080/api/payments/package/${referenceId}/status?status=APPROVED`, {
    method: "PUT",
  })
    .then((res) => {
      if (!res.ok) throw new Error("Failed to approve package");
      return res.text();
    })
    .then(() => {
      fadeOutAndRemoveRow(row);
      updatePackageMetrics();
    })
    .catch((err) => {
      console.error(err);
      alert("Error while approving package");
    });
}

// Reject Package Handler
function rejectPackage(button) {
  const row = button.closest("tr");
  const referenceId = row.querySelector(".reference-id").textContent;

  fetch(`http://localhost:8080/api/payments/package/${referenceId}/status?status=REJECTED`, {
    method: "PUT",
  })
    .then((res) => {
      if (!res.ok) throw new Error("Failed to reject package");
      return res.text();
    })
    .then(() => {
      fadeOutAndRemoveRow(row);
      updatePackageMetrics();
    })
    .catch((err) => {
      console.error(err);
      alert("Error while rejecting package");
    });
}

function fadeOutAndRemoveRow(row) {
  row.classList.add("fade-out");

  setTimeout(() => {
    row.remove();
    const tbody = document.getElementById("printPackagesList");

    if (tbody.children.length === 0) {
      tbody.innerHTML = `<tr><td colspan="4">No package requests found</td></tr>`;
    }
  }, 500); // match the CSS transition time
}


// Function to update metrics after package actions
function updatePackageMetrics() {

  const acceptedPackages = document.querySelectorAll('.package-status[style*="display: inline-block"]').length;
  console.log(`Accepted packages: ${acceptedPackages}`);

}


document.addEventListener('DOMContentLoaded', function() {

  const statusMessages = document.querySelectorAll('.package-status');
  statusMessages.forEach(msg => {
    msg.style.display = 'none';
  });
  

});

// Example function for adding new package rows (can be connected to a form or API)
function addPackageToTable(referenceId, pages, amount) {
  const tableBody = document.getElementById('printPackagesList');
  if (!tableBody) return;
  
  // Create new row
  const newRow = document.createElement('tr');
  newRow.innerHTML = `
    <td class="reference-id">${referenceId}</td>
    <td class="pages">${pages}</td>
    <td class="amount">${amount}</td>
    <td>
      <button class="accept" onclick="acceptPackage(this)">Accept</button>
      <button class="reject" onclick="rejectPackage(this)">Reject</button>
      <div class="package-status">Accepted</div>
    </td>
  `;
  
  // Add new row to table
  tableBody.appendChild(newRow);
}

// Function to toggle printer availability status
function togglePrinterStatus() {
  const button = document.getElementById('printerStatusBtn');
  
  if (button.textContent === 'Available My Printer') {
    // Change to unavailable
    button.textContent = 'Unavailable My Printer';
    button.classList.add('unavailable');
  } else {
    // Change back to available
    button.textContent = 'Available My Printer';
    button.classList.remove('unavailable');
  }
}

function styleStatusDiv(div, approved) {
  div.style.color = approved ? "#008000" : "#B22222";
  div.style.fontWeight = "bold";
  div.style.padding = "4px 10px";
  div.style.borderRadius = "4px";
  div.style.backgroundColor = approved ? "#e6ffe6" : "#ffe6e6";
  div.style.border = `1px solid ${approved ? "#008000" : "#B22222"}`;
  div.style.boxShadow = "0 1px 3px rgba(0,0,0,0.1)";
}

