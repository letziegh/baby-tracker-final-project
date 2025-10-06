// Get parent ID from Thymeleaf
const parentId = document.getElementById('parentId').value;
const childrenGrid = document.getElementById('childrenGrid');
const emptyState = document.getElementById('emptyState');

// Load children on page load
document.addEventListener('DOMContentLoaded', function() {
    loadChildren();
});

// Add event delegation for view profile and edit buttons
childrenGrid.addEventListener('click', function(e) {
    if (e.target.classList.contains('view-profile-btn')) {
        const childId = e.target.getAttribute('data-child-id');
        viewChildProfile(childId);
    } else if (e.target.classList.contains('edit-child-btn')) {
        const childId = e.target.getAttribute('data-child-id');
        editChild(childId);
    }
});

function loadChildren() {
    const parentId = document.getElementById('parentId').value;
    
    fetch(`/api/children/parent/${parentId}/with-last-activity`)
        .then(response => response.json())
        .then(children => {
            childrenGrid.innerHTML = '';
            
            if (children.length === 0) {
                // Show empty state
                emptyState.style.display = 'block';
                childrenGrid.style.display = 'none';
            } else {
                // Hide empty state and show children
                emptyState.style.display = 'none';
                childrenGrid.style.display = 'grid';
                
                children.forEach(child => {
                    const childCard = createChildCard(child);
                    childrenGrid.appendChild(childCard);
                });
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error loading children. Please refresh the page.');
        });
}

function createChildCard(child) {
    const card = document.createElement('div');
    card.className = 'child-card';
    
    // Get first letter of name for avatar
    const firstLetter = child.name.charAt(0).toUpperCase();
    
    // Format birthdate
    const birthdate = new Date(child.birthdate).toLocaleDateString('en-US', {
        month: 'numeric',
        day: 'numeric',
        year: 'numeric'
    });
    
    card.innerHTML = `
        <div class="child-avatar">${firstLetter}</div>
        <h3 class="child-name">${child.name}</h3>
        <p class="child-age">${child.age}</p>
        <p class="child-birthdate">Born: ${birthdate}</p>
        <p class="child-last-activity">${child.lastActivityDescription}</p>
        <div class="child-actions">
            <button class="view-profile-btn" data-child-id="${child.id}">View Profile</button>
            <button class="edit-child-btn" data-child-id="${child.id}">Edit</button>
        </div>
    `;
    
    return card;
}

function viewChildProfile(childId) {
    window.location.href = `/api/children/${childId}/activities`;
}

function editChild(childId) {
    window.location.href = `/api/children/${childId}/edit`;
}
