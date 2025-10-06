// Children Page JavaScript

// Get parent ID from Thymeleaf
const parentId = document.getElementById('parentId').value;
const childrenList = document.getElementById('childrenList');
const emptyState = document.getElementById('emptyState');
const childrenCount = document.getElementById('childrenCount');

// Load children on page load
document.addEventListener('DOMContentLoaded', function() {
    loadChildren();
});

// Add event delegation for child actions
childrenList.addEventListener('click', function(e) {
    if (e.target.classList.contains('view-profile-btn')) {
        const childId = e.target.getAttribute('data-child-id');
        viewChildProfile(childId);
    } else if (e.target.classList.contains('edit-child-btn')) {
        const childId = e.target.getAttribute('data-child-id');
        editChild(childId);
    } else if (e.target.classList.contains('delete-child-btn')) {
        const childId = e.target.getAttribute('data-child-id');
        deleteChild(childId);
    }
});

function loadChildren() {
    fetch(`/api/children/parent/${parentId}/with-last-activity`)
        .then(response => response.json())
        .then(children => {
            childrenList.innerHTML = '';
            
            if (children.length === 0) {
                // Show empty state
                emptyState.style.display = 'block';
                childrenList.style.display = 'none';
                childrenCount.textContent = '0 children';
            } else {
                // Hide empty state and show children
                emptyState.style.display = 'none';
                childrenList.style.display = 'block';
                childrenCount.textContent = `${children.length} ${children.length === 1 ? 'child' : 'children'}`;
                
                children.forEach(child => {
                    const childItem = createChildItem(child);
                    childrenList.appendChild(childItem);
                });
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error loading children. Please refresh the page.');
        });
}

function createChildItem(child) {
    const item = document.createElement('div');
    item.className = 'child-item';
    
    // Get first letter of name for avatar
    const firstLetter = child.name.charAt(0).toUpperCase();
    
    // Format birthdate
    const birthdate = new Date(child.birthdate).toLocaleDateString('en-US', {
        month: 'long',
        day: 'numeric',
        year: 'numeric'
    });
    
    // Format gender
    const genderText = child.gender === 'MALE' ? 'Male' : child.gender === 'FEMALE' ? 'Female' : 'Other';
    
    item.innerHTML = `
        <div class="child-item-content">
            <div class="child-avatar">${firstLetter}</div>
            <div class="child-info">
                <h3 class="child-name">${child.name}</h3>
                <div class="child-details">
                    <span class="child-age">${child.age}</span>
                    <span class="child-gender">${genderText}</span>
                    <span class="child-birthdate">Born ${birthdate}</span>
                </div>
                <p class="child-last-activity">${child.lastActivityDescription}</p>
            </div>
        </div>
        <div class="child-actions">
            <button class="view-profile-btn" data-child-id="${child.id}">View Profile</button>
            <button class="edit-child-btn" data-child-id="${child.id}">Edit</button>
            <button class="delete-child-btn" data-child-id="${child.id}">Delete</button>
        </div>
    `;
    
    return item;
}

function viewChildProfile(childId) {
    window.location.href = `/api/children/${childId}/activities`;
}

function editChild(childId) {
    window.location.href = `/api/children/${childId}/edit`;
}

function deleteChild(childId) {
    if (confirm('Are you sure you want to delete this child? This action cannot be undone and will also delete all associated activities.')) {
        fetch(`/api/children/${childId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            // Reload children list
            loadChildren();
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error deleting child. Please try again.');
        });
    }
}
