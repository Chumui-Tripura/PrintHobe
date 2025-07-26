document.addEventListener("DOMContentLoaded", () => {
  const dropArea = document.getElementById("dropArea");
  const fileInput = document.getElementById("fileInput");

  let uploadData = null;

  // When drop area clicked, trigger file input
  dropArea.addEventListener("click", () => {
    fileInput.click();
  });

  // When file selected, upload it
  fileInput.addEventListener("change", () => {
    if (fileInput.files.length > 0) {
      const file = fileInput.files[0];
      uploadFile(file);
    }
  });

  // Recalculate amount & time when copies or color changes
  const copiesInput = document.getElementById("copies");
  const colorSelect = document.getElementById("color");

  function reuploadIfFilePresent() {
    const file = document.getElementById("fileInput").files[0];
    if (file) {
      uploadFile(file);
    }
  }

  copiesInput.addEventListener("input", reuploadIfFilePresent);
  colorSelect.addEventListener("change", reuploadIfFilePresent);

  function uploadFile(file) {
    const user = JSON.parse(sessionStorage.getItem("user"));
    const userId = user.userId;
    const printerId = 9; // your fixed printer id
    const color = document.getElementById("color").value;
    const copies = document.getElementById("copies").value || 1;

    const formData = new FormData();
    formData.append("file", file);
    formData.append("userId", userId);
    formData.append("printerId", printerId);
    formData.append("color", color);
    formData.append("copies", copies);

    fetch("http://localhost:8080/api/documents/upload-info", {
      method: "POST",
      body: formData,
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Upload failed with status " + response.status);
        }
        return response.json();
      })
      .then((data) => {
        uploadData = {
          ...data,
          colorCost: data.costColor,
          bwCost: data.costBw,
          colorTime: data.timePerPageColor,
          bwTime: data.timePerPageBw,
        };

        console.log("Upload success:", data);
        // TODO: update UI with pages, cost, etc from data
        // Fill pages
        document.getElementById("pages").value = data.pages;

        // Fill amount
        document.getElementById("amount").value = data.amount;

        // Format estimated time to local string and show it
        const estTime = new Date(data.estimatedTime);
        document.getElementById("estimatedTime").value =
          estTime.toLocaleString();

        // Enable or disable the Use Package button
        const usePackageBtn = document.querySelector(".btn.use-package");
        if (data.canUsePackage) {
          usePackageBtn.disabled = false;
          usePackageBtn.classList.remove("disabled");
        } else {
          usePackageBtn.disabled = true;
          usePackageBtn.classList.add("disabled");
        }

        // OPTIONAL: Store the response globally for use in the next step
        window.latestUpload = {
          ...data,
          fileName: file.name,
          printerId: 9,
          userId: userId,
          color: color,
          copies: copies,
        };
      })
      .catch((err) => {
        console.error("Upload error:", err);
        // TODO: show error in UI
      });
  }

  function recalculateCostAndTime() {
    if (!uploadData) return;

    const pages = uploadData.pages;
    const copies = parseInt(document.getElementById("copies").value) || 1;
    const color = document.getElementById("color").value;

    const costPerPage =
      color === "COLOR" ? uploadData.colorCost : uploadData.bwCost;
    const timePerPage =
      color === "COLOR" ? uploadData.colorTime : uploadData.bwTime;

    const amount = costPerPage * pages * copies;
    const totalSeconds = timePerPage * pages * copies + 300; // +5 min buffer

    document.getElementById("amount").value = amount.toFixed(2);

    if (!isNaN(totalSeconds)) {
      const now = new Date();
      now.setSeconds(now.getSeconds() + totalSeconds);
      document.getElementById("estimatedTime").value = now.toLocaleString();
    } else {
      document.getElementById("estimatedTime").value = "Invalid time";
    }
  }

  const usePackageBtn = document.querySelector(".btn.use-package");

  usePackageBtn.addEventListener("click", () => {
    const upload = window.latestUpload;
    const fileInput = document.getElementById("fileInput");

    if (!upload || fileInput.files.length === 0) {
      alert("Please upload a file first.");
      return;
    }

    const user = JSON.parse(sessionStorage.getItem("user"));
    const userId = user.userId;

    const formData = new FormData();
    formData.append("userId", userId);
    formData.append("printerId", 9); // ( is for OS LAB printer)
    formData.append("pages", upload.pages);
    formData.append("copies", upload.copies);
    formData.append("color", upload.color);
    formData.append("sides", document.getElementById("sides").value);
    formData.append("punching", document.getElementById("punching").value);
    formData.append("filepath", upload.fileName); // optional use, backend can ignore if unused
    formData.append("file", fileInput.files[0]); // send the file again

    fetch("http://localhost:8080/api/documents/upload-use-package", {
      method: "POST",
      body: formData,
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error("Upload failed");
        }
        return res.json();
      })
      .then((data) => {
        alert("Document saved using package successfully!");
        console.log("Success:", data);
        window.location.href = "../User/user.html";
      })
      .catch((err) => {
        console.error("Use Package Error:", err);
        alert("Failed to use package for this document.");
      });
  });

  document.querySelector(".submit-btn").addEventListener("click", (e) => {
  e.preventDefault();
  const referenceId = document.querySelector(".bkash-inner input").value.trim();
  if (!referenceId) {
    alert("Please enter a reference number.");
    return;
  }

  const fileInput = document.getElementById("fileInput");
  if (!fileInput.files[0]) {
    alert("Please upload a file before submitting payment.");
    return;
  }

  const upload = window.latestUpload;
  const user = JSON.parse(sessionStorage.getItem("user"));

  const formData = new FormData();
  formData.append("referenceId", referenceId);
  formData.append("userId", user.userId);
  formData.append("operatorId", 9); // OS Lab
  formData.append("printerId", 9); // id of the OS lab printer operator
  formData.append("amount", upload.amount);
  formData.append("pages", upload.pages);
  formData.append("copies", upload.copies);
  formData.append("color", upload.color);
  formData.append("sides", document.getElementById("sides").value);
  formData.append("punching", document.getElementById("punching").value);
  formData.append("paymentFor", "PRINTING");
  formData.append("file", fileInput.files[0]);

  fetch("http://localhost:8080/api/documents/submit-payment", {
    method: "POST",
    body: formData,
  })
    .then((res) => {
      if (!res.ok) {
        return res.text().then((msg) => {
          throw new Error(msg);
        });
      }
      return res.text();
    })
    .then((msg) => {
      alert(msg);
      window.location.href = "../User/user.html";
    })
    .catch((err) => {
      console.error("Payment Submission Error:", err);
      alert("Failed to submit payment and document: " + err.message);
    });
});
});
