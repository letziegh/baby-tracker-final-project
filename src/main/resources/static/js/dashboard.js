// Get parent ID from Thymeleaf
const parentId = document.getElementById('parentId').value;

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

function loadChildren() {
    const parentId = document.getElementById('parentId').value;
    
    fetch(`/api/children/parent/${parentId}`)
        .then(response => response.json())
        .then(children => {
            const childrenList = document.getElementById('childrenList');
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

function showActivityForm(childId) {
    document.getElementById('selectedChildId').value = childId;
    document.getElementById('activityFormContainer').style.display = 'block';
}

function viewActivities(childId) {
    window.location.href = `/child/${childId}/activities`;
} 