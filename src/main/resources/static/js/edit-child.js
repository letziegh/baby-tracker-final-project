// Edit Child Page JavaScript

// Get child ID from Thymeleaf
const childId = document.getElementById('childId').value;

// Form submission handler
document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('editChildForm').addEventListener('submit', function(e) {
        e.preventDefault();
        
        const childName = document.getElementById('childName').value.trim();
        const birthdate = document.getElementById('birthdate').value;
        const gender = document.getElementById('gender').value;
        
        // Basic validation
        if (!childName || !birthdate || !gender) {
            alert('Please fill in all required fields.');
            return;
        }
        
        // Validate birthdate is not in the future
        const today = new Date();
        const birthDate = new Date(birthdate);
        if (birthDate > today) {
            alert('Birth date cannot be in the future.');
            return;
        }
        
        const childData = {
            name: childName,
            birthdate: birthdate,
            gender: gender
        };
        
        // Show loading state
        const submitBtn = document.querySelector('button[type="submit"]');
        const originalText = submitBtn.innerHTML;
        submitBtn.innerHTML = '<span>Saving Changes...</span>';
        submitBtn.disabled = true;
        
        fetch(`/api/children/${childId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(childData)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            // Show success message
            alert('Child information updated successfully!');
            
            // Redirect to dashboard
            window.location.href = '/dashboard';
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error updating child information. Please try again.');
            
            // Reset button state
            submitBtn.innerHTML = originalText;
            submitBtn.disabled = false;
        });
    });
    
    // Set max date to today for birthdate input
    const birthdateInput = document.getElementById('birthdate');
    const today = new Date().toISOString().split('T')[0];
    birthdateInput.setAttribute('max', today);
});
