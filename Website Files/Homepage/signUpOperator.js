document.addEventListener("DOMContentLoaded", () => {
  const openBtn = document.querySelector(".operator-signup-btn");
  const modal = document.getElementById("operatorModal");
  const closeBtn = document.querySelector(".close-operator");
  const signupForm = document.getElementById("operatorSignupForm");

  const step1 = document.getElementById("step1");
  const step2 = document.getElementById("step2");

  const nextBtn = document.getElementById("nextBtn");
  const backBtn = document.getElementById("backBtn");

  const createPackageBtn = document.getElementById("createPackageBtn");
  const packageSection = document.getElementById("packageSection");

  // Open modal and show step 1
  openBtn.addEventListener("click", () => {
    modal.style.display = "block";
    step1.style.display = "block";
    step2.style.display = "none";
  });

  // Close modal on clicking close button
  closeBtn.addEventListener("click", () => {
    modal.style.display = "none";
  });

  // Close modal on clicking outside modal content
  window.addEventListener("click", (event) => {
    if (event.target === modal) {
      modal.style.display = "none";
    }
  });

  // Handle Next button
  nextBtn.addEventListener("click", () => {
    const password = signupForm.querySelector(
      'input[placeholder="Password"]'
    ).value;
    const confirmPassword = signupForm.querySelector(
      'input[placeholder="Confirm Password"]'
    ).value;

    if (password !== confirmPassword) {
      alert("Passwords do not match!");
      return;
    }

    step1.style.display = "none";
    step2.style.display = "block";
  });

  if (createPackageBtn && packageSection) {
    createPackageBtn.addEventListener("click", () => {
      // Toggle package section visibility
      if (packageSection.style.display === "none") {
        packageSection.style.display = "block";
        createPackageBtn.textContent = "Remove Package";
      } else {
        packageSection.style.display = "none";
        createPackageBtn.textContent = "Create Package";

        // Optional: clear values
        packageSection
          .querySelectorAll("input")
          .forEach((input) => (input.value = ""));
      }
    });
  }
  // Handle Back button
  backBtn.addEventListener("click", () => {
    step2.style.display = "none";
    step1.style.display = "block";
  });

  // Handle form submission
  signupForm.addEventListener("submit", (event) => {
    event.preventDefault();

    // First this is operator's info (Chum)
    const firstName = signupForm.querySelector('input[name="firstName"]').value;
    const lastName = signupForm.querySelector('input[name="lastName"]').value;
    const email = signupForm.querySelector('input[type="email"]').value;
    const password = signupForm.querySelector('input[type="password"]').value;

    // Extract printer info
    const printerName = signupForm.querySelector(
      'input[name="printer_name"]'
    ).value;
    const location = signupForm.querySelector('input[name="location"]').value;
    const timeBw = parseFloat(
      signupForm.querySelector('input[name="time_bw"]').value
    );
    const timeColor = parseFloat(
      signupForm.querySelector('input[name="time_color"]').value
    );
    const costBw = parseFloat(
      signupForm.querySelector('input[name="cost_bw"]').value
    );
    const costColor = parseFloat(
      signupForm.querySelector('input[name="cost_color"]').value
    );
    const phoneNumber = signupForm.querySelector(
      'input[name="bkash_number"]'
    ).value;

    // Check if package section is shown
    const hasPackage =
      document.getElementById("packageSection").style.display === "block";
    const packagePrice = hasPackage
      ? parseFloat(
          signupForm.querySelector('input[name="package_price"]').value
        )
      : 0;
    const packagePage = hasPackage
      ? parseInt(signupForm.querySelector('input[name="package_pages"]').value)
      : 0;
    // Creating this for backend DTO named OperatorRegistraionRequest
    const payload = {
      firstName,
      lastName,
      email,
      password,
      phoneNumber,
      printer: {
        name: printerName,
        location: location,
        status: "NOT_AVAILABLE",
        timePerPageBw: timeBw,
        timePerPageColor: timeColor,
        costBw: costBw,
        costColor: costColor,
        hasPackage: hasPackage ? "YES" : "NO",
        packagePrice: packagePrice,
        packagePage: packagePage,
      },
    };

    //sending data to the backend
    fetch("http://localhost:8080/api/operators/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(payload),
    })
      .then((response) => {
        if (!response.ok) {
          return response.text().then((text) => {
            console.error("Server Response:", text);
            throw new Error("Registration failed: " + text);
          });
        }

        // Handle plain text or JSON responses
        return response.text().then((text) => {
          try {
            return JSON.parse(text); // backend sent JSON
          } catch {
            return { message: text }; // backend sent plain text
          }
        });
      })
      .then(() => {
      // Step 2: Fetch operator by email
      return fetch(`http://localhost:8080/api/operators/email/${email}`);
    })
    .then((res) => {
      if (!res.ok) {
        throw new Error("Failed to fetch operator data");
      }
      return res.json();
    })
    .then((operatorData) => {
      // Step 3: Save to session storage
      sessionStorage.setItem(
        "user",
        JSON.stringify({
          userId: operatorData.operatorId,
          userName: operatorData.firstName + " " + operatorData.lastName,
          email: operatorData.email,
          phoneNumber: operatorData.phoneNumber,
          role: "OPERATOR",
        })
      );

      // Step 4: Redirect to operator dashboard
      window.location.href = "../Printing_operator/operator.html";
    })
    .catch((error) => {
      console.error("Frontend Error:", error);
      alert("Error during registration. " + error.message);
    });
  });
});