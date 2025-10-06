// Add Child Page JavaScript

// Get parent ID from Thymeleaf
const parentId = document.getElementById('parentId').value;

// Form submission handler
document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('addChildForm').addEventListener('submit', function(e) {
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
            gender: gender,
            parent: {
                id: parentId
            }
        };
        
        // Show loading state
        const submitBtn = document.querySelector('button[type="submit"]');
        const originalText = submitBtn.innerHTML;
        submitBtn.innerHTML = '<span>Adding Child...</span>';
        submitBtn.disabled = true;
        
        fetch('/api/children', {
            method: 'POST',
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
            alert('Child added successfully!');
            
            // Redirect to dashboard
            window.location.href = '/dashboard';
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error adding child. Please try again.');
            
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
