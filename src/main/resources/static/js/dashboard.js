// Get parent ID from Thymeleaf
const parentId = document.getElementById('parentId').value;
const childrenList = document.getElementById('childrenList');

// Load children on page load
document.addEventListener('DOMContentLoaded', function() {
    loadChildren();
    
    // Add child form submission
    document.getElementById('addChildForm').addEventListener('submit', function(e) {
        e.preventDefault();
        
        const childName = document.getElementById('childName').value;
        const birthdate = document.getElementById('birthdate').value;
        
        // Get parent ID from the hidden input
        const parentId = document.getElementById('parentId').value;
        
        const childData = {
            name: childName,
            birthdate: birthdate,
            parent: {
                id: parentId
            }
        };
        
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
            // Clear form
            document.getElementById('childName').value = '';
            document.getElementById('birthdate').value = '';
            
            // Reload children list
            loadChildren();
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error adding child. Please try again.');
        });
    });
    
    // Add activity form submission
    document.getElementById('addActivityForm').addEventListener('submit', function(e) {
        e.preventDefault();
        
        const activityType = document.getElementById('activityType').value;
        const startTime = document.getElementById('startTime').value;
        const endTime = document.getElementById('endTime').value;
        const notes = document.getElementById('notes').value;
        const childId = document.getElementById('selectedChildId').value;
        
        const activityData = {
            activityType: activityType,
            startTime: startTime,
            endTime: endTime,
            notes: notes,
            child: {
                id: childId
            }
        };
        
        fetch('/api/activities', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(activityData)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            // Clear form
            document.getElementById('activityType').value = '';
            document.getElementById('startTime').value = '';
            document.getElementById('endTime').value = '';
            document.getElementById('notes').value = '';
            
            // Hide activity form
            document.getElementById('activityFormContainer').style.display = 'none';
            
            // Reload children list
            loadChildren();
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error adding activity. Please try again.');
        });
    });
});

// Add event delegation for settings buttons
childrenList.addEventListener('click', function(e) {
    if (e.target.classList.contains('settings-btn')) {
        const child = JSON.parse(e.target.getAttribute('data-child'));
        openEditModal(child);
    }
});

function loadChildren() {
    const parentId = document.getElementById('parentId').value;
    
    fetch(`/api/children/parent/${parentId}`)
        .then(response => response.json())
        .then(children => {
            childrenList.innerHTML = '';
            
            children.forEach(child => {
                const childElement = document.createElement('div');
                childElement.className = 'card mb-3';
                childElement.innerHTML = `
                    <div class="card-body">
                        <h5 class="card-title">${child.name}</h5>
                        <p class="card-text">Age: ${child.age}</p>
                        <button class="btn btn-primary" onclick="showActivityForm(${child.id})">Add Activity</button>
                        <a href="/api/children/${child.id}/activities" class="btn btn-info">View Activities</a>
                        <button class="btn btn-secondary settings-btn" data-child='${JSON.stringify(child)}'>Settings</button>
                    </div>
                `;
                childrenList.appendChild(childElement);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error loading children. Please refresh the page.');
        });
}

function openEditModal(child) {
    document.getElementById('editChildId').value = child.id;
    document.getElementById('editChildName').value = child.name;
    document.getElementById('editBirthdate').value = child.birthdate;
    document.getElementById('editGender').value = child.gender;

    const editModal = new bootstrap.Modal(document.getElementById('editChildModal'));
    editModal.show();
}

function updateChild() {
    const childId = document.getElementById('editChildId').value;
    const childData = {
        name: document.getElementById('editChildName').value,
        birthdate: document.getElementById('editBirthdate').value,
        gender: document.getElementById('editGender').value,
        parent: {
            id: document.getElementById('parentId').value
        }
    };

    fetch(`/api/children/${childId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(childData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const editModal = bootstrap.Modal.getInstance(document.getElementById('editChildModal'));
        editModal.hide();
        loadChildren();
    })
    .catch(error => {
        console.error('Error updating child:', error);
        alert('Error updating child. Please try again.');
    });
}

function showActivityForm(childId) {
    document.getElementById('selectedChildId').value = childId;
    document.getElementById('activityFormContainer').style.display = 'block';
}

function viewActivities(childId) {
    window.location.href = `/child/${childId}/activities`;
} 