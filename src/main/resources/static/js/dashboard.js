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
        const gender = document.getElementById('gender').value;
        
        // Get parent ID from the hidden input
        const parentId = document.getElementById('parentId').value;
        
        const childData = {
            name: childName,
            birthdate: birthdate,
            gender: gender,
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
            document.getElementById('gender').value = 'MALE';
            
            // Hide form and show add children button
            hideAddChildForm();
            
            // Reload children list
            loadChildren();
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error adding child. Please try again.');
        });
    });
    

});

// Add event delegation for edit and delete buttons
childrenList.addEventListener('click', function(e) {
    if (e.target.classList.contains('edit-btn')) {
        const child = JSON.parse(e.target.getAttribute('data-child'));
        openEditModal(child);
    } else if (e.target.classList.contains('delete-btn')) {
        const childId = e.target.getAttribute('data-child-id');
        const childName = e.target.getAttribute('data-child-name');
        deleteChild(childId, childName);
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
                        <p class="card-text">Gender: ${child.gender || 'Not specified'}</p>
                        <a href="/add-activity/${child.id}" class="btn btn-primary">Add Activity</a>
                        <a href="/api/children/${child.id}/activities" class="btn btn-info">View Activities</a>
                        <button class="btn btn-warning edit-btn" data-child='${JSON.stringify(child)}'>Edit</button>
                        <button class="btn btn-danger delete-btn" data-child-id="${child.id}" data-child-name="${child.name}">Delete</button>
                    </div>
                `;
                childrenList.appendChild(childElement);
            });

            // Show/hide add child form and add children button
            if (children.length === 0) {
                document.getElementById('addChildFormContainer').style.display = '';
                document.getElementById('addChildrenBtn').style.display = 'none';
            } else {
                document.getElementById('addChildFormContainer').style.display = 'none';
                document.getElementById('addChildrenBtn').style.display = '';
            }
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



function viewActivities(childId) {
    window.location.href = `/child/${childId}/activities`;
}

function showAddChildForm() {
    document.getElementById('addChildFormContainer').style.display = 'block';
    document.getElementById('addChildrenBtn').style.display = 'none';
}

function hideAddChildForm() {
    document.getElementById('addChildFormContainer').style.display = 'none';
    document.getElementById('addChildrenBtn').style.display = 'block';
}

function deleteChild(childId, childName) {
    if (confirm(`Are you sure you want to delete ${childName}? This action cannot be undone and will also delete all associated activities.`)) {
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
            alert(`${childName} has been deleted successfully.`);
            loadChildren();
        })
        .catch(error => {
            console.error('Error deleting child:', error);
            alert('Error deleting child. Please try again.');
        });
    }
} 