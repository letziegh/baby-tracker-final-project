// document.addEventListener("DOMContentLoaded", function () {
//     let nameElement = document.querySelector("#parent-name"); // Corrected selector to #parent-name (ID selector)
//     let emailElement = document.querySelector("#parent-email"); // Corrected selector to #parent-email (ID selector)
//
//     let name = nameElement ? nameElement.innerText.trim() : "";
//     let email = emailElement ? emailElement.innerText.trim() : "";
//
//     // If name is missing, prompt the user for a username
//     if (!name || name === "null") {
//         let username = prompt("GitHub/Google did not return a valid name. Please enter a username:");
//
//         if (username) {
//             fetch('/api/parent/update-username', {
//                 method: 'POST',
//                 headers: {
//                     'Content-Type': 'application/json',
//                     'X-CSRF-TOKEN': document.cookie.match('(^|;)\\s*XSRF-TOKEN\\s*=\\s*([^;]+)')?.pop() || '' // Re-add CSRF token header
//                 },
//                 credentials: 'include', // Keep credentials
//                 body: JSON.stringify({ username, email })
//             })
//                 .then(response => {
//                     if (!response.ok) {
//                         throw new Error(`HTTP error! Status: ${response.status}`);
//                     }
//                     return response.json();
//                 })
//                 .then(data => {
//                     if (nameElement) { // Check if nameElement is not null before setting innerText
//                         nameElement.innerText = data.name; // Update name dynamically
//                     }
//                 })
//                 .catch(error => console.error("Error saving username:", error));
//         }
//     }
// });


// document.addEventListener("DOMContentLoaded", function () {
//     let nameElement = document.getElementById("parent-name");
//     let emailElement = document.getElementById("parent-email");
//
//     if (!nameElement || !emailElement) {
//         console.error("Missing parent-name or parent-email elements.");
//         return; // Stop execution if elements are missing
//     }

    // let name = nameElement.innerText.trim();
//     let email = emailElement.innerText.trim();
//
//     if (!name || name === "null") {
//         let username = prompt("GitHub/Google did not return a valid name. Please enter a username:");
//
//         if (username) {
//             fetch('/api/parent/update-username', {
//                 method: 'POST',
//                 headers: { 'Content-Type': 'application/json', 'X-CSRF-TOKEN': document.cookie.match('(^|;)\\s*XSRF-TOKEN\\s*=\\s*([^;]+)')?.pop() || ''}, // Get CSRF Token },
//                 credentials: 'include', // Include authentication credentials
//                 body: JSON.stringify({ username, email })
//             })
//                 .then(response => {
//                     if (!response.ok) {
//                         throw new Error(`HTTP error! Status: ${response.status}`);
//                     }
//                     return response.json();
//                 })
//                 .then(data => {
//                     if (nameElement) {
//                         nameElement.innerText = data.name; // Update dynamically
//                     }
//                 })
//                 .catch(error => console.error("Error saving username:", error));
//         }
//     }
// });

// document.addEventListener("DOMContentLoaded", function () {
//     let nameElement = document.querySelector("parent-name");
//     let emailElement = document.querySelector("parent-email");
//
//     let name = nameElement ? nameElement.innerText.trim() : "";
//     let email = emailElement ? emailElement.innerText.trim() : "";
//
//     // If name is missing, prompt the user for a username
//     if (!name || name === "null") {
// //         let username = prompt("GitHub/Google did not return a valid name. Please enter a username:");
//
//         if (username) {
//             fetch('/api/parent/update-username', {
//                 method: 'POST',
//                 headers: { 'Content-Type': 'application/json' },
//                 body: JSON.stringify({ username, email })
//             })
//                 .then(response => response.json())
//                 .then(data => {
//                     nameElement.innerText = data.name; // Update name dynamically
//                 })
//                 .catch(error => console.error("Error saving username:", error));
//         }
//     }
// });

